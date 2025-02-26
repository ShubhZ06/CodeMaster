package CodeMaster;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class python_controller {
    @FXML
    private StackPane stackpane;
    @FXML
    private Label close_window;
    @FXML
    private Label max_window;
    @FXML
    private Label prev_window;
    @FXML
    private Pane solutionpane;
    @FXML
    private Text textplace;
    @FXML
    private TableView<Question> questionsTable; // TableView for displaying questions
    @FXML
    private TableColumn<Question, Integer> srNoColumn; // Sr No column
    @FXML
    private TableColumn<Question, String> questionColumn; // Question column
    @FXML
    private TableColumn<Question, String> solutionColumn; // Solution column

    public void initialize() {
        // Set up the columns to display the properties of the Question class
        srNoColumn.setCellValueFactory(new PropertyValueFactory<>("srNo"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        solutionColumn.setCellValueFactory(new PropertyValueFactory<>("solution"));

        // Add a cell factory to the solution column to display "View" instead of the
        // actual solution
        solutionColumn.setCellFactory(column -> {
            return new TableCell<Question, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText("View");
                }
            };
        });

        // Add a row factory to handle row selection event
        questionsTable.setRowFactory(tv -> {
            TableRow<Question> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Question selectedQuestion = row.getItem();
                    TableColumn<Question, ?> column = row.getTableView().getColumns()
                            .get(row.getTableView().getSelectionModel().getSelectedCells().get(0).getColumn());
                    if (column == questionColumn) {
                        navigateToQuestionPage(selectedQuestion);
                    } else if (column == solutionColumn) {
                        navigateToSolutionPage(selectedQuestion);
                    }
                }
            });
            return row;
        });

        loadQuestions();
    }

    private void loadQuestions() {
        ObservableList<Question> questionList = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3306/codemaster"; // Change as necessary
        String user = "root"; // Change as necessary
        String password = ""; // Change as necessary

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM python"); // Change table name as necessary

            while (resultSet.next()) {
                int srNo = resultSet.getInt("Sr_no"); // Change to your column name
                String question = resultSet.getString("question"); // Change to your column name
                String solution = resultSet.getString("solution"); // Change to your column name
                questionList.add(new Question(srNo, question, solution));
            }

            questionsTable.setItems(questionList);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void maximizeWindow(MouseEvent event) {
        Stage stage = (Stage) max_window.getScene().getWindow();
        stage.setMaximized(true);
    }

    public void closewindow(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void navigateToQuestionPage(Question selectedQuestion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("terminal.fxml"));
            Parent root = loader.load();

            // Get the controller of the new page
            terminal_controller controller = loader.getController();
            controller.initData(selectedQuestion.getQuestion(), getDescriptionFromDatabase(selectedQuestion.getSrNo())); // Pass
                                                                                                                         // the
                                                                                                                         // selected
                                                                                                                         // question
                                                                                                                         // and
                                                                                                                         // its
                                                                                                                         // description

            // Create a new stage for the terminal
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT); // Set the style before showing
            Scene scene = new Scene(root, Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToSolutionPage(Question selectedQuestion) {
        try {
            // Get the solution from the database using the question's Sr No
            String solutionText = getSolutionFromDatabase(selectedQuestion.getSrNo());

            // Set the retrieved solution to the textplace Text node
            textplace.setText(solutionText);

            // Make the solution pane and stackpane visible
            stackpane.setVisible(true);
            solutionpane.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSolutionFromDatabase(int srNo) {
        String solution = "";
        String url = "jdbc:mysql://localhost:3306/codemaster"; // Change as necessary
        String user = "root"; // Change as necessary
        String password = ""; // Change as necessary

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT solution FROM python WHERE Sr_no = " + srNo);
            if (resultSet.next()) {
                solution = resultSet.getString("solution");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return solution;
    }

    private String getDescriptionFromDatabase(int srNo) {
        String description = "";
        String url = "jdbc:mysql://localhost:3306/codemaster"; // Change as necessary
        String user = "root"; // Change as necessary
        String password = ""; // Change as necessary

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT description FROM python WHERE Sr_no = " + srNo); // Change
                                                                                                                  // to
                                                                                                                  // your
                                                                                                                  // column
                                                                                                                  // name
            if (resultSet.next()) {
                description = resultSet.getString("description"); // Change to your column name
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return description;
    }

    public void backwindow(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playground.fxml"));
        Scene scene = new Scene(root, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.show();
    }

    public void closeform(MouseEvent event) {
        stackpane.setVisible(false);
        solutionpane.setVisible(false);
    }
}
