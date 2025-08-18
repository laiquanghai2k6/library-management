package view;

import javafx.concurrent.Task;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;


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
    private TableView<Document> docsTable;

    int id = 0;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private final DocumentController docController = new DocumentController();

    @FXML
    void add(ActionEvent event) {

        String t = title.getText().trim();
        String a = author.getText().trim();
        String i = isbn.getText().trim();

        if (t.isEmpty() || a.isEmpty() || i.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", "Vui lòng điền đủ thông tin!");
            return;
        }

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return docController.addDocument(new Document(t, a, i));
            }
        };
        task.setOnSucceeded(e -> {
            if (task.getValue()) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm tài liệu thành công!");
                loadDocsToListView();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm tài liệu.");
            }
        });

        task.setOnFailed(e -> {
            showAlert(Alert.AlertType.ERROR, "Lỗi", task.getException().getMessage());
        });

        new Thread(task).start();

    }

    @FXML
    void update(ActionEvent event) {
        boolean allSuccess = true;
        StringBuilder failedDocs = new StringBuilder();

        for (Document doc : docsTable.getItems()) {
            if (doc.getTitle().trim().isEmpty() ||
                    doc.getAuthor().trim().isEmpty() ||
                    doc.getIsbn().trim().isEmpty()) {
                allSuccess = false;
                failedDocs.append(doc.getTitle()).append(" (thiếu dữ liệu), ");
                continue; // bỏ qua doc này
            }

            boolean success = docController.updateDocument(doc);
            if (!success) {
                allSuccess = false;
                failedDocs.append(doc.getTitle()).append(", ");
            }
        }

        if (allSuccess) {
            showAlert(AlertType.INFORMATION, "Thông báo", "Cập nhật thành công tất cả tài liệu!");
        } else {
            showAlert(AlertType.ERROR, "Lỗi cập nhật",
                    "Có lỗi khi cập nhật một số tài liệu: " + failedDocs);
        }

        loadDocsToListView();
    }

    @FXML
    void deleteDocument(ActionEvent event) {
        Document selectedDoc = docsTable.getSelectionModel().getSelectedItem();

        if (selectedDoc == null) {
            // Nếu chưa chọn document nào
            showAlert(Alert.AlertType.WARNING, "Chưa chọn tài liệu", "Vui lòng chọn tài liệu muốn xóa!");
            return;
        }

        // Xác nhận trước khi xóa
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận xóa");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc chắn muốn xóa tài liệu: " + selectedDoc.getTitle() + "?");

        confirmAlert.showAndWait().ifPresent(response -> {
            switch (response.getButtonData()) {
                case OK_DONE -> {
                    boolean success = docController.deleteDocument(selectedDoc.getId());
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa tài liệu thành công!");
                        loadDocsToListView(); // refresh table
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa tài liệu. Vui lòng thử lại.");
                    }
                }
                default -> {
                    // Người dùng hủy xóa, không làm gì
                }
            }
        });
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        docsTable.setEditable(true);
        idColumn.setEditable(false); // k cho chỉnh id

        // Cho phép chỉnh sửa từng cột và xử lý update khi commit
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(event -> {
            String newValue = event.getNewValue().trim();
            if (newValue.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", "Tiêu đề không được để trống!");
                docsTable.refresh();
                return;
            }
            Document doc = event.getRowValue();
            doc.setTitle(newValue);
        });

        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setOnEditCommit(event -> {
            String newValue = event.getNewValue().trim();
            if (newValue.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", "Tác giả không được để trống!");
                docsTable.refresh();
                return;
            }
            Document doc = event.getRowValue();
            doc.setAuthor(newValue);
        });

        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isbnColumn.setOnEditCommit(event -> {
            String newValue = event.getNewValue().trim();
            if (newValue.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", "ISBN không được để trống!");
                docsTable.refresh();
                return;
            }
            Document doc = event.getRowValue();
            doc.setIsbn(newValue);
        });

        // Load dữ liệu lần đầu
        loadDocsToListView();
    }

    private void loadDocsToListView() {
        Task<List<Document>> task = new Task<>() {
            @Override
            protected List<Document> call() throws Exception {
                return docController.getAllDocuments(); // api
            }
        };

        task.setOnSucceeded(event -> {
            docsTable.getItems().clear();
            docsTable.getItems().addAll(task.getValue());
        });

        task.setOnFailed(event -> {
            showAlert(Alert.AlertType.ERROR, "Lỗi tải dữ liệu", task.getException().getMessage());
        });

        new Thread(task).start();
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