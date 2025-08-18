package view;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BorrowRecord;
import model.Document;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import controller.BorrowController;
import controller.DocumentController;
import controller.UserController;

public class BorrowView implements Initializable {
    private static class BorrowData { //class lưu tạm data
        String userName;
        String documentTitle;
        LocalDate borrowDate; 

        BorrowData(String userName, String documentTitle, LocalDate borrowDate) {
            this.userName = userName;
            this.documentTitle = documentTitle;
            this.borrowDate = borrowDate;
        }
    }
    private final BorrowController borrowController = new BorrowController();
    private final UserController userController = new UserController();
    private final DocumentController documentController = new DocumentController();

    @FXML
    private ComboBox<String> bookNameComboBox;

    @FXML
    private ComboBox<String> usersComboBox;

    @FXML
    private VBox borrowingListVBox;

    private PauseTransition bookPause = new PauseTransition(Duration.millis(300));
    private PauseTransition userPause = new PauseTransition(Duration.millis(300));


    private ObservableList<String> items;
    private ObservableList<String> usersItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookNameComboBox.setEditable(true);
        usersComboBox.setEditable(true);

        items = FXCollections.observableArrayList();
        usersItem = FXCollections.observableArrayList();

        bookNameComboBox.setItems(items);
        usersComboBox.setItems(usersItem);

        bookNameComboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            // Nếu đang chọn item trùng với text thì bỏ qua
            if (bookNameComboBox.getSelectionModel().getSelectedItem() != null &&
                    newText.equals(bookNameComboBox.getSelectionModel().getSelectedItem())) {
                return;
            }

            bookPause.setOnFinished(event -> {
                autocompleteComboBox(newText);
            });
            bookPause.playFromStart();
        });

        usersComboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            // Nếu đang chọn item trùng với text thì bỏ qua
            if (usersComboBox.getSelectionModel().getSelectedItem() != null &&
                    newText.equals(usersComboBox.getSelectionModel().getSelectedItem())) {
                return;
            }

            userPause.setOnFinished(event -> {
                autocompleteUserComboBox(newText);
            });
            userPause.playFromStart();
        });

        loadBorrowingList();
    }

    private void autocompleteComboBox(String text) {
        if (text == null || text.isEmpty()) {
            bookNameComboBox.hide();
            return;
        }

        List<Document> matchedDocuments = documentController.searchDocumentsByName(text);

        items.clear();
        for (Document doc : matchedDocuments) {
            items.add(doc.getTitle());
        }

        if (!items.isEmpty()) {
            bookNameComboBox.show();
        } else {
            bookNameComboBox.hide();
        }
    }

    private void autocompleteUserComboBox(String text) {
        if (text == null || text.isEmpty()) {
            usersComboBox.hide();
            return;
        }

        List<User> matchedUser = userController.searchUserByName(text);

        usersItem.clear();
        for (User user : matchedUser) {
            usersItem.add(user.getName() + " - " + user.getEmail());
        }

        if (!usersItem.isEmpty()) {
            usersComboBox.show();
        } else {
            usersComboBox.hide();
        }
    }

    @FXML
    public void loadBorrowingList() {
        Task<List<BorrowData>> task = new Task<>() {
            @Override
            protected List<BorrowData> call() throws Exception {
                List<BorrowData> list = new ArrayList<>();
                List<BorrowRecord> borrowings = borrowController.getAllBorrowRecords();

                for (BorrowRecord borrowRecord : borrowings) {
                    User user = userController.getUserById(borrowRecord.getUser_id());
                    Document document = documentController.getDocumentById(borrowRecord.getDocument_id());

                    list.add(new BorrowData(
                            user.getName(),
                            document.getTitle(),
                            borrowRecord.getBorrow_date()));
                }
                return list;
            }
        };

        task.setOnSucceeded(event -> {
            borrowingListVBox.getChildren().clear();
            for (BorrowData bd : task.getValue()) {
                addBorrowView(bd.userName, bd.documentTitle, bd.borrowDate);
            }
        });

        task.setOnFailed(event -> {
            Throwable ex = task.getException();
            showAlert(Alert.AlertType.ERROR, "Lỗi tải danh sách mượn", ex.getMessage());
        });

        new Thread(task).start();
    }




    private void addBorrowView(String username, String bookName, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        Label label = new Label(username + " đã mượn " + bookName + " vào ngày " + formattedDate);
        label.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 3 0 3 0; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 3px;");

        borrowingListVBox.getChildren().add(0, label);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void submit() {
        String bookText = bookNameComboBox.getEditor().getText().trim();
        String userText = usersComboBox.getEditor().getText().trim();

        if (bookText.isEmpty() || userText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Chưa điền đủ thông tin",
                    "Vui lòng chọn sách và người dùng trước khi trả.");
            return;
        }
        String[] parts = userText.split(" - ");
        if (parts.length < 2) {
            showAlert(Alert.AlertType.ERROR, "Sai định dạng người dùng",
                    "Vui lòng chọn người dùng từ danh sách.");
            return;
        }
        String email = parts[1].trim();
        String username = parts[0].trim();
        UUID userId = userController.getUserIdByEmail(email);
        if (userId == null) {
            showAlert(Alert.AlertType.ERROR, "Không tìm thấy người dùng",
                    "Không tìm thấy người dùng với email: " + email);
            return;
        }

        UUID documentId = documentController.getDocumentIdByName(bookText);
        if (documentId == null) {
            showAlert(Alert.AlertType.ERROR, "Không tìm thấy sách",
                    "Không tìm thấy sách với tên: " + bookText);
            return;
        }

        BorrowRecord borrowRecord = borrowController.getBorrowRecord(documentId, userId);
        if (borrowRecord != null) {
            showAlert(Alert.AlertType.ERROR, "Đã có người mượn sách",
                    username + " đã mượn sách " + bookText);
            return;
        }

        // Tạo ReturnRecord mới
        LocalDate currentDate = LocalDate.now();
        BorrowRecord newBorrowRecord = new BorrowRecord(
                UUID.randomUUID(),
                userId,
                documentId,
                currentDate);
        boolean isSuccess = borrowController.addBorrowRecord(newBorrowRecord);
        if (isSuccess) {

            showAlert(Alert.AlertType.INFORMATION, "Thành công",
                    "Mượn sách thành công: " + bookText +
                            " bởi " + username);
            addBorrowView(username, bookText, currentDate);
            // Xoá field sau khi submit
            bookNameComboBox.getEditor().clear();
            usersComboBox.getEditor().clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thất bại",
                    "Mượn sách thất bại!");
        }

    }

    public void Back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UI.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
