package view;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import controller.UserController;
import java.net.URL;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;
import javafx.scene.control.Control;

public class UserView implements Initializable {

    private final UserController userController = new UserController();

    @FXML
    private Button addUser;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private Button updateButton;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/UI.fxml"));
        switchScene(event, root);
    }

    private void switchScene(ActionEvent event, Parent view) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(view);
        // Thêm background
        scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submit(ActionEvent event) {
        userController.addUser(new User(name.getText(), email.getText()));
    }

    @FXML
    void update(ActionEvent event) {
        loadUsersToListView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadUsersToListView();
    }

    @FXML
    public void loadUsersToListView() {
        nameColumn.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell<User, String> call(TableColumn<User, String> param) {
                return new TableCell<User, String>() {
                    private final Text text = new Text();

                    {
                        text.wrappingWidthProperty().bind(param.widthProperty().subtract(10));
                        setPrefHeight(Control.USE_COMPUTED_SIZE);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            text.setText(item);
                            setGraphic(text);
                        }
                    }
                };
            }
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<User> users = userController.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            userTable.getItems().add(users.get(i));
        }

    }
}
