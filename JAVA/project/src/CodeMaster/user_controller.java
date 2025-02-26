package CodeMaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class user_controller {

    @FXML
    private Button classroom_button;

    @FXML
    private Label name_user;
    @FXML
    private Label close_window;
    @FXML
    private Label head_text;
    @FXML
    private Button dash_button;

    @FXML
    private Label max_window;
    @FXML
    private Button log_out;
    @FXML
    private Button playground_button;

    @FXML
    private Button user_button;

    public void initialize() {
        displayUsername();
    }

    // Method to display the username on the label
    public void displayUsername() {
        // Get the username from the data class and set it to the labels
        String username = data.username;// Fetching the username from the data class

        if (username != null && !username.isEmpty()) {
            head_text.setText(username); // Set the username to the head_text label
            name_user.setText(username); // Set the username to the name_user label
            // Set the date of joining to the doj_name label
        } else {
            head_text.setText("Guest"); // Fallback text if username is null or empty
            name_user.setText("Guest"); // Fallback text if username is null or empty
            // Fallback text if username is null or empty
        }
    }

    public void maximizeWindow(MouseEvent event) {
        // Get the current stage (window) and maximize it
        Stage stage = (Stage) max_window.getScene().getWindow();
        stage.setMaximized(true); // Maximizes the window
    }

    public void closewindow(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void switchpage(ActionEvent event) {
        try {
            Parent root = null;

            if (event.getSource() == classroom_button) {
                root = FXMLLoader.load(getClass().getResource("classroom.fxml"));
            } else if (event.getSource() == playground_button) {
                root = FXMLLoader.load(getClass().getResource("playground.fxml"));
            } else if (event.getSource() == user_button) {
                root = FXMLLoader.load(getClass().getResource("user.fxml"));
            } else if (event.getSource() == log_out) {
                root = FXMLLoader.load(getClass().getResource("intropage.fxml"));
            }

            if (root != null) {
                Scene scene = new Scene(root, Color.TRANSPARENT);
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                // Get the current stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the existing stage without changing the style
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
