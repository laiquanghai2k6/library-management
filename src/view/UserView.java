package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import controller.UserController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        String userName = name.getText().trim();
        String userEmail = email.getText().trim();

        // Validate dữ liệu
        if (userName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", "Tên người dùng không được để trống!");
            return;
        }

        if (userEmail.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", "Email người dùng không được để trống!");
            return;
        }

        boolean success = userController.addUser(new User(userName, userEmail));

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm người dùng thành công!");
            loadUsersToListView();
            // Có thể xóa dữ liệu trong TextField sau khi thêm
            name.clear();
            email.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi",
                    "Không thể thêm người dùng. Vui lòng kiểm tra lại dữ liệu và thử lại.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        boolean allSuccess = true;
        StringBuilder failedUsers = new StringBuilder();

        for (User user : userTable.getItems()) {
            String userName = user.getName() != null ? user.getName().trim() : "";
            String userEmail = user.getEmail() != null ? user.getEmail().trim() : "";

            // Validate dữ liệu
            if (userName.isEmpty() || userEmail.isEmpty()) {
                allSuccess = false;
                failedUsers.append(userName.isEmpty() ? "(Tên trống)" : userName)
                        .append(userEmail.isEmpty() ? " (Email trống)" : "")
                        .append(", ");
                continue; // Bỏ qua update user này
            }

            boolean success = userController.updateUser(user);
            if (!success) {
                allSuccess = false;
                failedUsers.append(userName).append(", ");
            }
        }

        if (allSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Cập nhật thành công tất cả người dùng!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi cập nhật",
                    "Có lỗi khi cập nhật một số người dùng: " + failedUsers.toString());
        }

        loadUsersToListView();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
        Task<List<User>> task = new Task<>() {
            @Override
            protected List<User> call() throws Exception {
                return userController.getAllUsers();
            }
        };

        task.setOnSucceeded(event -> {
            userTable.getItems().clear();
            userTable.getItems().addAll(task.getValue());
        });

        task.setOnFailed(event -> {
            Throwable ex = task.getException();
            showAlert(Alert.AlertType.ERROR, "Lỗi tải danh sách người dùng", ex.getMessage());
        });

        new Thread(task).start();
    }

}
