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

public class cpp_notes_controller {

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Button b9;
    @FXML
    private Button b10;
    @FXML
    private Button b11;
    @FXML
    private Button b12;
    @FXML
    private Button b13;
    @FXML
    private Button b14;
    @FXML
    private Button b15;
    @FXML
    private Button b16;
    @FXML
    private Button b17;
    @FXML
    private Button b18;
    @FXML
    private Button b19;
    @FXML
    private Button b20;
    @FXML
    private Button b21;
    @FXML
    private Button b22;
    @FXML
    private Button b23;
    @FXML
    private Button b24;
    @FXML
    private Button b25;
    @FXML
    private Button b26;
    @FXML
    private Button b27;
    @FXML
    private Button b28;
    @FXML
    private Button b29;
    @FXML
    private Button b30;
    @FXML
    private Button b31;
    @FXML
    private Button b32;
    @FXML
    private Button b33;
    @FXML
    private Button b34;
    @FXML
    private Button b35;
    @FXML
    private Button b36;
    @FXML
    private Button b37;
    @FXML
    private Button b38;
    @FXML
    private Button b39;
    @FXML
    private Button b40;
    @FXML
    private Button b41;
    @FXML
    private Button b42;
    @FXML
    private Button b43;
    @FXML
    private Button b44;
    @FXML
    private Button b45;
    @FXML
    private Button b46;
    @FXML
    private Button b47;
    @FXML
    private Button b48;
    @FXML
    private Button b49;
    @FXML
    private Button b50;
    @FXML
    private Button b51;





    @FXML
    private Text title_holder;
    @FXML
    private Text content_holder;
    @FXML
    private Label close_window;
    @FXML
    private Label max_window;

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
        b1.setOnAction(event -> loadChapter(1));
        b2.setOnAction(event -> loadChapter(2));
        b3.setOnAction(event -> loadChapter(3));
        b4.setOnAction(event -> loadChapter(4));
        b5.setOnAction(event -> loadChapter(5));
        b6.setOnAction(event -> loadChapter(6));
        b7.setOnAction(event -> loadChapter(7));
        b8.setOnAction(event -> loadChapter(8));
        b9.setOnAction(event -> loadChapter(9));
        b10.setOnAction(event -> loadChapter(10));
        b11.setOnAction(event -> loadChapter(11));
        b12.setOnAction(event -> loadChapter(12));
        b13.setOnAction(event -> loadChapter(13));
        b14.setOnAction(event -> loadChapter(14));
        b15.setOnAction(event -> loadChapter(15));
        b16.setOnAction(event -> loadChapter(16));
        b17.setOnAction(event -> loadChapter(17));
        b18.setOnAction(event -> loadChapter(18));
        b19.setOnAction(event -> loadChapter(19));
        b20.setOnAction(event -> loadChapter(20));
        b21.setOnAction(event -> loadChapter(21));
        b22.setOnAction(event -> loadChapter(22));
        b23.setOnAction(event -> loadChapter(23));
        b24.setOnAction(event -> loadChapter(24));
        b25.setOnAction(event -> loadChapter(25));
        b26.setOnAction(event -> loadChapter(26));
        b27.setOnAction(event -> loadChapter(27));
        b28.setOnAction(event -> loadChapter(28));
        b29.setOnAction(event -> loadChapter(29));
        b30.setOnAction(event -> loadChapter(30));
        b31.setOnAction(event -> loadChapter(31));
        b32.setOnAction(event -> loadChapter(32));
        b33.setOnAction(event -> loadChapter(33));
        b34.setOnAction(event -> loadChapter(34));
        b35.setOnAction(event -> loadChapter(35));
        b36.setOnAction(event -> loadChapter(36));
        b37.setOnAction(event -> loadChapter(37));
        b38.setOnAction(event -> loadChapter(38));
        b39.setOnAction(event -> loadChapter(39));
        b40.setOnAction(event -> loadChapter(40));
        b41.setOnAction(event -> loadChapter(41));
        b42.setOnAction(event -> loadChapter(42));
        b43.setOnAction(event -> loadChapter(43));
        b44.setOnAction(event -> loadChapter(44));
        b45.setOnAction(event -> loadChapter(45));
        b46.setOnAction(event -> loadChapter(46));
        b47.setOnAction(event -> loadChapter(47));
        b48.setOnAction(event -> loadChapter(48));
        b49.setOnAction(event -> loadChapter(49));
        b50.setOnAction(event -> loadChapter(50));
        b51.setOnAction(event -> loadChapter(51)); // Assuming chapter number 51 in the database


        
        

    }
    public void backwindow(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("classroom.fxml"));
        Scene scene = new Scene(root, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.show();
    }
    // Method to load chapter data from the database based on chapter number
    private void loadChapter(int chapterNumber) {
        String query = "SELECT title, content FROM cpp_notes WHERE no = ?";
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

}
