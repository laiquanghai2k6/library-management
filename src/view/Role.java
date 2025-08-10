package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Role {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loadAdmin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/UI.fxml"));
        switchScene(event, root);
    }

    private void switchScene(ActionEvent event, Parent view) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }

    public void loadUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/UI2.fxml"));
        switchScene(event, root);
    }
}
