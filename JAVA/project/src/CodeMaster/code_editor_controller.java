package CodeMaster;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.*;

public class code_editor_controller {
    @FXML
    private Label max_window;
    @FXML
    private Label close_window;
    @FXML
    private TextArea code_area; // For inputting the program
    @FXML
    private TextArea input_area; // For user input to the program
    @FXML
    private TextArea output_area; // For output of the program
    @FXML
    private Button run_button; // For running the code
    @FXML
    private ComboBox<String> language_selector; // For selecting the programming language

    private Process runProcess;
    private BufferedWriter processInputWriter;

    @FXML
    public void initialize() {
        // Initialize the language selector with available languages
        language_selector.getItems().addAll("C", "C++", "Java", "Python");
        language_selector.setValue("C"); // Default to C
    }

    @FXML
    public void compileAndRunCode() {
        String code = code_area.getText(); // Retrieve code from code_area
        String selectedLanguage = language_selector.getValue(); // Get selected language
        File tempCodeFile = null;
        File tempExeFile = null;

        try {
            // Step 1: Save the code to a temporary file based on the selected language
            File outputDir = new File(System.getProperty("user.home"), "Documents\\CodeExec");
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // Create directory if it doesn't exist
            }

            String extension = getExtension(selectedLanguage);
            tempCodeFile = new File(outputDir, "temp_code." + extension);
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempCodeFile));
            writer.write(code);
            writer.close();

            // Step 2: Compile or interpret the code
            Process compileProcess = compileOrRun(tempCodeFile, selectedLanguage, outputDir);
            compileProcess.waitFor();

            // Step 3: Capture any compilation or runtime errors
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String errorLine;
            StringBuilder errorOutput = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }

            if (errorOutput.length() > 0) {
                // If there are errors, show them in output_area
                output_area.setText("Error:\n" + errorOutput.toString());
            } else {
                // Step 4: Run the program if compilation is successful (for compiled languages)
                if (selectedLanguage.equals("C") || selectedLanguage.equals("C++") || selectedLanguage.equals("Java")) {
                    ProcessBuilder runProcessBuilder = getRunCommand(selectedLanguage, outputDir);
                    runProcess = runProcessBuilder.start();
                    processInputWriter = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()));
                    captureProgramOutput(); // Capture output
                }
            }

        } catch (Exception e) {
            output_area.setText("An error occurred: " + e.getMessage());
        } finally {
            // Clean up temporary files
            if (tempCodeFile != null && tempCodeFile.exists()) {
                tempCodeFile.delete();
            }
        }
    }

    // Method to determine the file extension based on the selected language
    private String getExtension(String language) {
        switch (language) {
            case "C":
                return "c";
            case "C++":
                return "cpp";
            case "Java":
                return "java";
            case "Python":
                return "py";
            default:
                return "txt";
        }
    }

    // Method to compile or interpret the code based on the language
    private Process compileOrRun(File codeFile, String language, File outputDir) throws IOException {
        switch (language) {
            case "C":
                return Runtime.getRuntime().exec(new String[] { "gcc", codeFile.getAbsolutePath(), "-o",
                        new File(outputDir, "temp_code.exe").getAbsolutePath() });
            case "C++":
                return Runtime.getRuntime().exec(new String[] { "g++", codeFile.getAbsolutePath(), "-o",
                        new File(outputDir, "temp_code.exe").getAbsolutePath() });
            case "Java":
                return Runtime.getRuntime().exec(new String[] { "javac", codeFile.getAbsolutePath() });
            case "Python":
                return Runtime.getRuntime().exec(new String[] { "python", codeFile.getAbsolutePath() });
            default:
                return null;
        }
    }

    // Method to get the command for running the compiled code
    private ProcessBuilder getRunCommand(String language, File outputDir) {
        switch (language) {
            case "C":
            case "C++":
                return new ProcessBuilder(new File(outputDir, "temp_code.exe").getAbsolutePath());
            case "Java":
                return new ProcessBuilder("java", "-cp", outputDir.getAbsolutePath(), "temp_code");
            case "Python":
                return new ProcessBuilder("python", new File(outputDir, "temp_code.py").getAbsolutePath());
            default:
                return null;
        }
    }

    // Capture output method (same as before)
    private void captureProgramOutput() {
        new Thread(() -> {
            try {
                BufferedReader outputReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));

                String outputLine;
                StringBuilder outputText = new StringBuilder();
                boolean inputRequired = false;

                while ((outputLine = outputReader.readLine()) != null
                        || (outputLine = errorReader.readLine()) != null) {
                    if (outputLine != null) {
                        outputText.append(outputLine).append("\n");

                        if (outputLine.toLowerCase().contains("enter input")) {
                            inputRequired = true;
                        }

                        String finalOutput = outputText.toString();
                        Platform.runLater(() -> output_area.setText(finalOutput));

                        if (inputRequired) {
                            Platform.runLater(() -> {
                                input_area.setPromptText("Please enter input for the program...");
                                input_area.setDisable(false);
                                run_button.setDisable(true);
                            });
                            break;
                        }
                    }
                }

            } catch (IOException e) {
                Platform.runLater(() -> output_area.setText("Error capturing program output: " + e.getMessage()));
            }
        }).start();
    }

    public void sendInputToProgram() {
        String userInput = input_area.getText();

        try {
            processInputWriter.write(userInput + "\n");
            processInputWriter.flush();

            input_area.clear();
            input_area.setDisable(true);
            input_area.setPromptText("Enter your input here...");
            captureProgramOutput();

            run_button.setDisable(false);

        } catch (IOException e) {
            output_area.setText("Error sending input to program: " + e.getMessage());
        }
    }

    public void maximizeWindow(MouseEvent event) {
        Stage stage = (Stage) max_window.getScene().getWindow();
        stage.setMaximized(true);
    }

    public void closewindow(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}
