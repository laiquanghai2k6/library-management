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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import java.util.UUID;
import javafx.util.StringConverter;

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
    private UUID category;

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
    private TableColumn<Document, UUID> categoryColumn;

    @FXML
    private TableColumn<Document, Integer> quantityColumn;

    @FXML
    private TableView<Document> docsTable;

    int id = 0;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private final DocumentController docController = new DocumentController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        docsTable.setEditable(true);
        idColumn.setEditable(false); // k cho chỉnh id

        // Cho phép chỉnh sửa từng cột và xử lý update khi commit
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(event -> {
            Document doc = event.getRowValue();
            doc.setTitle(event.getNewValue());
            docController.updateDocument(doc);
            reloadTable();
        });

        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setOnEditCommit(event -> {
            Document doc = event.getRowValue();
            doc.setAuthor(event.getNewValue());
            docController.updateDocument(doc);
            reloadTable();
        });

        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isbnColumn.setOnEditCommit(event -> {
            Document doc = event.getRowValue();
            doc.setIsbn(event.getNewValue());
            docController.updateDocument(doc);
            reloadTable();
        });

        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<UUID>() {
            @Override
            public String toString(UUID uuid) {
                return uuid != null ? uuid.toString() : "";
            }
            @Override
            public UUID fromString(String string) {
                try {
                    return UUID.fromString(string.trim());
                } catch (Exception e) {
                    return null;
                }
            }
        }));

        categoryColumn.setOnEditCommit(event -> {
            Document doc = event.getRowValue();
            if (event.getNewValue() != null) {
                doc.setCategoryId(event.getNewValue());
                docController.updateDocument(doc);
                reloadTable();
            }
        });

        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setOnEditCommit(event -> {
            Document doc = event.getRowValue();
            // doc.setQuantity(event.getNewValue());
            docController.updateDocument(doc);
            reloadTable();
        });

        // Load dữ liệu lần đầu
        loadDocsToListView();
    }

    private void loadDocsToListView() {
        docsTable.getItems().clear();
        List<Document> docs = docController.getAllDocuments();
        docsTable.getItems().addAll(docs);
    }

    private void reloadTable() {
        loadDocsToListView();
    }

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