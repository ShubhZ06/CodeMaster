package CodeMaster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class base_controller {

    @FXML
    private Button alreadyhave_button;
    @FXML
    private PasswordField create_Pass;
    @FXML
 private TextField create_answer;
    @FXML
    private Button create_button;
    @FXML
    private TextField create_name;
    @FXML
    private Pane create_page;
    @FXML
    private Button create_page_button;
    @FXML
    private ComboBox<?> create_question;
    @FXML
    private Pane login_page;
    @FXML
    private Hyperlink login_frgt_pass;
    @FXML
    private PasswordField login_pass;
    @FXML
    private TextField login_username;
    @FXML
    private Button loginpage_button;
    @FXML
    private ImageView arrow_down;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    private String[] questionList = { "What is your favorite Color?", "What is your favorite food?",
            "what is your birth date?" };

    public void loginBtn() {

        if (login_username.getText().isEmpty() || login_pass.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/password");
            alert.showAndWait();
        } else {

            String selctData = "SELECT username, password FROM user WHERE username = ? and password = ?";

            connect = base_database.connectDB();

            try {

                prepare = connect.prepareStatement(selctData);
                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_pass.getText());

                result = prepare.executeQuery();
                // IF SUCCESSFULLY LOGIN, THEN PROCEED TO ANOTHER FORM WHICH IS OUR MAIN FORM
                if (result.next()) {
                    // TO GET THE USERNAME THAT USER USED
                    data.username = login_username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    // LINK YOUR MAIN FORM
                    Parent root = FXMLLoader.load(getClass().getResource("classroom.fxml"));
                    Scene scene = new Scene(root, Color.TRANSPARENT);
                    scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.show();

                    loginpage_button.getScene().getWindow().hide();

                } else { // IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void regBtn() {
        if (create_name.getText().isEmpty() || create_answer.getText().isEmpty()
                || create_question.getSelectionModel().getSelectedItem() == null
                || create_Pass.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            String regData = "INSERT INTO user (username, password, question, answer, date) "
                    + "VALUES(?,?,?,?,?)";
            connect = base_database.connectDB();

            try {
                // CHECK IF THE USERNAME IS ALREADY RECORDED
                String checkUsername = "SELECT username FROM user WHERE username = '"
                        + create_name.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(create_name.getText() + " is already taken");
                    alert.showAndWait();
                } else if (create_Pass.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, atleast 8 characters are needed");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, create_name.getText());
                    prepare.setString(2, create_Pass.getText());
                    prepare.setString(3, (String) create_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, create_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();

                    create_name.setText("");
                    create_Pass.setText("");
                    create_question.getSelectionModel().clearSelection();
                    create_answer.setText("");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();
        for (String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        create_question.setItems(listData);
    }

    public void maximizeWindow(MouseEvent event) {
        // Get the current stage (window) and maximize it
        Stage stage = (Stage) arrow_down.getScene().getWindow();
        stage.setMaximized(true); // Maximizes the window
    }

    public void closeform(MouseEvent event) {
        create_page.setVisible(false);
        login_page.setVisible(false);
    }

    public void closewindow(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void switchform(ActionEvent event) {
        if (event.getSource() == create_button) {
            create_page.setVisible(true);
            login_page.setVisible(false);
            regLquestionList();
        } else if (event.getSource() == alreadyhave_button) {
            create_page.setVisible(false);
            login_page.setVisible(true);
        }

    }
}
