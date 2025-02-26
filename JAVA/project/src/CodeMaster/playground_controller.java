package CodeMaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class playground_controller {

    @FXML
    private Button CPP_button;

    @FXML
    private Button C_button;

    @FXML
    private Button JAVA_button;
    @FXML
    private ImageView image_cpp;

    @FXML
    private Button PYTHON_button;

    @FXML
    private Button classroom_button;
 @FXML
    private Label head_text;
    @FXML
    private Label close_window;

    @FXML
    private Button dash_button;

    @FXML
    private Label max_window;

    @FXML
    private Button playground_button;

    @FXML
    private Button user_button;

    public void initialize() {
        // Set images to buttons using the full paths
        setButtonImage(C_button, "file:/C:/Users/shubh/Downloads/pngwing.com (2).png");
        setButtonImage(CPP_button, "file:/C:/Users/shubh/Downloads/pngwing.com (4).png");
        setButtonImage(JAVA_button, "file:/C:/Users/shubh/Downloads/pngwing.com (3).png");
        setButtonImage(PYTHON_button, "file:/C:/Users/shubh/Downloads/pngwing.com.png");

        // Display the username on the label when FXML loads
        displayUsername();
    }

    // Method to display the username on the label
    public void displayUsername() {
        // Get the username from the data class and set it to the label
        String username = data.username;  // Fetching the username from the data class
        if (username != null && !username.isEmpty()) {
            head_text.setText(username);  // Set the username to the label
        } else {
            head_text.setText("Guest");  // Fallback text if username is null or empty
        }
    }
    // Helper method to set image on button
    private void setButtonImage(Button button, String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(244);  // Set the height
        imageView.setFitWidth(407);   // Set the width
        imageView.setPreserveRatio(false);  // Maintain aspect ratio while scaling

        button.setGraphic(imageView);  // Set the image on the button
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

    public void switchcontent(ActionEvent event) {
        try {
            Parent root = null;

            if (event.getSource() == C_button) {
                root = FXMLLoader.load(getClass().getResource("C.fxml"));
            } else if (event.getSource() == CPP_button) {
                root = FXMLLoader.load(getClass().getResource("CPP.fxml"));
            } else if (event.getSource() == JAVA_button) {
                root = FXMLLoader.load(getClass().getResource("JAVA.fxml"));
            } else if (event.getSource() == PYTHON_button) {
                root = FXMLLoader.load(getClass().getResource("PYTHON.fxml"));
            }

            if (root != null) {
                Scene scene = new Scene(root, Color.TRANSPARENT);
                scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());

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
