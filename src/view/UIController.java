package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loadUserView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
        switchScene(event, root);
    }

    public void loadDocumentView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/DocumentView.fxml"));
        switchScene(event, root);
    }

    public void loadBorrowView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/BorrowView.fxml"));
        switchScene(event, root);
    }

    public void loadReturnView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ReturnView.fxml"));
        switchScene(event, root);
    }

 

    private void switchScene(ActionEvent event, Parent view) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(view);
        scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
