package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import controller.UserController;
import java.net.URL;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class UserView implements Initializable {

    @FXML
    private ListView<String> myListView;

    private final UserController userController = new UserController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUsersToListView();
    }

    private void loadUsersToListView() {
        List<User> users = userController.getAllUsers();
        // Hiển thị tên người dùng (hoặc sửa theo dữ liệu bạn muốn)
        for (User user : users) {
            myListView.getItems().add(user.toString()); // hoặc user.toString()
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/UIController.fxml"));
        switchScene(event, root);
    }

    

    private void switchScene(ActionEvent event, Parent view) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }
}