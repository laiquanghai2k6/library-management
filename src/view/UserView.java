package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import model.User;

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
    private TableColumn<User, UUID> idColumn;

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
        scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submit(ActionEvent event) {
        boolean success = userController.addUser(new User(name.getText(), email.getText()));

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Thêm người dùng thành công!");
            alert.showAndWait();
            loadUsersToListView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể thêm người dùng");
            alert.setContentText("Vui lòng kiểm tra lại dữ liệu và thử lại.");
            alert.showAndWait();
        }
    }

    @FXML
    void update(ActionEvent event) {
        boolean allSuccess = true;
        StringBuilder failedUsers = new StringBuilder();

        for (User user : userTable.getItems()) {
            boolean success = userController.updateUser(user);
            if (!success) {
                allSuccess = false;
                failedUsers.append(user.getName()).append(", ");
            }
        }

        if (allSuccess) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật thành công tất cả người dùng!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi cập nhật");
            alert.setHeaderText("Có lỗi khi cập nhật người dùng");
            alert.setContentText("Cập nhật thất bại với người dùng: " + failedUsers);
            alert.showAndWait();
        }

        loadUsersToListView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTable.setEditable(true);

        // Cell factory cho cột name với TextField có wrap text
        nameColumn.setCellFactory(column -> new TextFieldTableCell<User, String>(new DefaultStringConverter()) {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(column.widthProperty().subtract(10));
                setGraphic(text);
                setPrefHeight(Control.USE_COMPUTED_SIZE);
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    text.setText(null);
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        });

        nameColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setName(event.getNewValue());
            // Không gọi userController.updateUser(user) ở đây
        });

        emailColumn.setCellFactory(column -> new TextFieldTableCell<User, String>(new DefaultStringConverter()) {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(column.widthProperty().subtract(10));
                setGraphic(text);
                setPrefHeight(Control.USE_COMPUTED_SIZE);
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    text.setText(null);
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        });

        emailColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setEmail(event.getNewValue());
            // Không gọi userController.updateUser(user) ở đây
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUsersToListView();
    }

    @FXML
    public void loadUsersToListView() {
        userTable.getItems().clear();
        List<User> users = userController.getAllUsers();
        userTable.getItems().addAll(users);
    }
}
