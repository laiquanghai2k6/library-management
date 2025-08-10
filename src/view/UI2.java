package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI2 {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loadBorrowView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/BorrowViewForUser.fxml"));
        switchScene(event, root);
    }

    public void loadReturnView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ReturnViewForUser.fxml"));
        switchScene(event, root);
    }

    public void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/Role.fxml"));
        switchScene(event, root);
    }

    private void switchScene(ActionEvent event, Parent view) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }
}
