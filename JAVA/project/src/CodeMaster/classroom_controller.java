package CodeMaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class classroom_controller {
    @FXML
    private Button python_button;
    @FXML
    private Button java_button;
    @FXML
    private Button cpp_button;
    @FXML
    private Button c_button;
    @FXML
    private Button classroom_button;
    @FXML
    private Label close_window;
    @FXML
    private Label prev_window;
    @FXML
    private Label max_window;
    @FXML
    private Label head_text;
    @FXML
    private Button playground_button;
    @FXML
    private Button user_button;
    @FXML
    private Stage previousStage; // To store the previous stage
    public void initialize() {
        // Set images to buttons using the full paths
        setButtonImage(c_button, "file:/C:/Users/shubh/Downloads/c1.png");
        setButtonImage(cpp_button, "file:/C:/Users/shubh/Downloads/cpp1.jpg");
        setButtonImage(java_button, "file:/C:/Users/shubh/Downloads/java1.jpg");
        setButtonImage(python_button, "file:/C:/Users/shubh/Downloads/1704352101828.jpg");
        displayUsername();
    }

    // Method to display the username on the label
    public void displayUsername() {
        // Get the username from the data class and set it to the label
        String username = data.username; // Fetching the username from the data class
        if (username != null && !username.isEmpty()) {
            head_text.setText(username); // Set the username to the label
        } else {
            head_text.setText("Guest"); // Fallback text if username is null or empty
        }
    }

    // Helper method to set image on button
    private void setButtonImage(Button button, String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(244); // Set the height
        imageView.setFitWidth(407); // Set the width
        imageView.setPreserveRatio(false); // Maintain aspect ratio while scaling

        button.setGraphic(imageView); // Set the image on the button
    }

    // Maximize window
    public void maximizeWindow(MouseEvent event) {
        Stage stage = (Stage) max_window.getScene().getWindow();
        stage.setMaximized(true); // Maximizes the window
    }

    // Close the current window
    public void closewindow(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    // Switch between pages
    public void switchpage(ActionEvent event) {
        try {
            Parent root = null;

            if (event.getSource() == classroom_button) {
                root = FXMLLoader.load(getClass().getResource("classroom.fxml"));
            } else if (event.getSource() == playground_button) {
                root = FXMLLoader.load(getClass().getResource("playground.fxml"));
            } else if (event.getSource() == user_button) {
                root = FXMLLoader.load(getClass().getResource("user.fxml"));
            }

            if (root != null) {
                Scene scene = new Scene(root, Color.TRANSPARENT);
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                previousStage = currentStage; // Store the current stage

                currentStage.setScene(scene);
                currentStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Switch between content (like notes)
    public void switchcontent(ActionEvent event) {
        try {
            Parent root = null;

            if (event.getSource() == c_button) {
                root = FXMLLoader.load(getClass().getResource("c_notes.fxml"));
            } else if (event.getSource() == cpp_button) {
                root = FXMLLoader.load(getClass().getResource("cpp_notes.fxml"));
            } else if (event.getSource() == java_button) {
                root = FXMLLoader.load(getClass().getResource("java_notes.fxml"));
            } else if (event.getSource() == python_button) {
                root = FXMLLoader.load(getClass().getResource("python_note.fxml"));
            }

            if (root != null) {
                Scene scene = new Scene(root, Color.TRANSPARENT);
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
