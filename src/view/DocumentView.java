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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class DocumentView implements Initializable {

    @FXML
    private ListView<String> myListView;

    @FXML
    private TextField title;

    @FXML
    private TextField author;

    @FXML
    private TextField isbn;

    @FXML
    private TextField categoryId;

    @FXML
    private TextField quantity;

    @FXML
    private Button addDocs;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<Document, Integer> idColumn;

    @FXML
    private TableColumn<Document, String> titleColumn;

    @FXML
    private TableColumn<Document, String> authorColumn;

    @FXML
    private TableColumn<Document, String> isbnColumn;

    @FXML
    private TableColumn<Document, String> categoryIDColumn;

    @FXML
    private TableColumn<Document, String> quantityColumn;

    @FXML
    private TableView<Document> docsTable;


    int id = 0;

    private final DocumentController docController = new DocumentController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDocsToListView();
    }

    private void loadDocsToListView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        categoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        List<Document> docs = docController.getAllDocuments();
        for (int i = id; i < docs.size(); i++) {
            docsTable.getItems().add(docs.get(i)); 
        }

        id = docs.size();
    }

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
}