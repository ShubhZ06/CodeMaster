
package CodeMaster;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class MultiLangCodeExecutor extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI Elements
        Label codeLabel = new Label("Enter your code:");
        TextArea codeArea = new TextArea();
        Button executeButton = new Button("Execute");
        Label outputLabel = new Label("Output:");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        // Language dropdownP
        ComboBox<String> languageBox = new ComboBox<>();
        languageBox.getItems().addAll("Python", "C", "C++", "Java");
        languageBox.setValue("Python"); // Default language

        // Add action for the Execute button
        executeButton.setOnAction(e -> {
            String code = codeArea.getText();
            String language = languageBox.getValue();
            executeCode(code, language, outputArea);
        });

        // Layout
        VBox vbox = new VBox(10, codeLabel, languageBox, codeArea, executeButton, outputLabel, outputArea);
        Scene scene = new Scene(vbox, 500, 500);

        // Stage Setup
        primaryStage.setTitle("Multi-Language Code Executor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to execute code based on selected language
    private void executeCode(String code, String language, TextArea outputArea) {
        try {
            File codeFile = createTempFile(code, language);
            ProcessBuilder processBuilder = null;

            switch (language) {
                case "Python":
                    processBuilder = new ProcessBuilder("python", codeFile.getAbsolutePath());
                    break;
                case "C":
                    String cExecutable = compileCCode(codeFile);
                    processBuilder = new ProcessBuilder(cExecutable);
                    break;
                case "C++":
                    String cppExecutable = compileCppCode(codeFile);
                    processBuilder = new ProcessBuilder(cppExecutable);
                    break;
                case "Java":
                    compileJavaCode(codeFile);
                    processBuilder = new ProcessBuilder("java", codeFile.getName().replace(".java", ""));
                    break;
            }

            if (processBuilder != null) {
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                process.waitFor();
                outputArea.setText(output.toString());
            }
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    // Helper to create a temporary file with the user's code
    private File createTempFile(String code, String language) throws IOException {
        String extension = switch (language) {
            case "Python" -> ".py";
            case "C" -> ".c";
            case "C++" -> ".cpp";
            case "Java" -> ".java";
            default -> ".txt";
        };
        File tempFile = File.createTempFile("usercode", extension);
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(code);
        }
        return tempFile;
    }

    // Helper to compile C code
    private String compileCCode(File codeFile) throws IOException, InterruptedException {
        String executablePath = codeFile.getAbsolutePath().replace(".c", "");
        ProcessBuilder processBuilder = new ProcessBuilder("gcc", codeFile.getAbsolutePath(), "-o", executablePath);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        process.waitFor();
        return executablePath;
    }

    // Helper to compile C++ code
    private String compileCppCode(File codeFile) throws IOException, InterruptedException {
        String executablePath = codeFile.getAbsolutePath().replace(".cpp", "");
        ProcessBuilder processBuilder = new ProcessBuilder("g++", codeFile.getAbsolutePath(), "-o", executablePath);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        process.waitFor();
        return executablePath;
    }

    // Helper to compile Java code
    private void compileJavaCode(File codeFile) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("javac", codeFile.getAbsolutePath());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
