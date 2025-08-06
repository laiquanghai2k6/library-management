package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Document;
import controller.DocumentController;
import java.net.URL;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class DocumentView implements Initializable {

    @FXML
    private ListView<String> myListView;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private Button addUser;

    @FXML
    private Button updateButton;

    int id = 0;

    private final DocumentController docController = new DocumentController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDocsToListView();
    }

    private void loadDocsToListView() {
        List<Document> docs = docController.getAllDocuments();
        
        for (int i = id; i < docs.size(); i++) {
            myListView.getItems().add(docs.get(i).toString()); 
        }
        id = docs.size();
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