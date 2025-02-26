package CodeMaster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class c_notes_controller {

    @FXML
    private Button chapter0;
    @FXML
    private Button chapter1;
    @FXML
    private Button chapter2;
    @FXML
    private Button chapter3;
    @FXML
    private Button chapter4;
    @FXML
    private Button chapter5;
    @FXML
    private Button chapter6;
    @FXML
    private Button chapter7;
    @FXML
    private Button chapter8;
    @FXML
    private Button chapter9;
    @FXML
    private Button chapter10;
    @FXML
    private Button chapter11;
    @FXML
    private Button project1;
    @FXML
    private Button project2;
    @FXML
    private Text title_holder;
    @FXML
    private Text content_holder;
    @FXML
    private Label close_window;
    @FXML
    private Label head_text;
    @FXML
    private Label max_window;

    public void backwindow(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("classroom.fxml"));
        Scene scene = new Scene(root, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.show();
    }

    // Database connection details
    private final String url = "jdbc:mysql://localhost:3306/codemaster"; // Update with your DB details
    private final String user = "root"; // Your DB username
    private final String password = ""; // Your DB password

    // Maximize the window
    public void maximizeWindow(MouseEvent event) {
        Stage stage = (Stage) max_window.getScene().getWindow();
        stage.setMaximized(true);
    }

    // Close the window
    public void closewindow(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
        // Load content for Chapter 0 on window load
        loadChapter(1); // Chapter 0 content (assuming chapter number 1 in the database)

        // Add click listeners for buttons
        chapter0.setOnAction(event -> loadChapter(1));
        chapter1.setOnAction(event -> loadChapter(2));
        chapter2.setOnAction(event -> loadChapter(3));
        chapter3.setOnAction(event -> loadChapter(4));
        chapter4.setOnAction(event -> loadChapter(5));
        project1.setOnAction(event -> loadChapter(6));
        chapter5.setOnAction(event -> loadChapter(7));
        chapter6.setOnAction(event -> loadChapter(8));
        chapter7.setOnAction(event -> loadChapter(9));
        chapter8.setOnAction(event -> loadChapter(10));
        chapter9.setOnAction(event -> loadChapter(11));
        chapter10.setOnAction(event -> loadChapter(12));
        project2.setOnAction(event -> loadChapter(13));
        chapter11.setOnAction(event -> loadChapter(14));
        displayUsername();
    }

    // Method to load chapter data from the database based on chapter number
    private void loadChapter(int chapterNumber) {
        String query = "SELECT title, content FROM c_notes WHERE no = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chapterNumber); // Set chapter number in the query
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");

                // Set data to Text
                title_holder.setText(title);
                content_holder.setText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
