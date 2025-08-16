package view;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.BorrowController;
import controller.DocumentController;
import controller.RatingController;
import controller.ReturnController;
import controller.UserController;
import model.BorrowRecord;
import model.Document;
import model.Rating;
import model.ReturnRecord;
import model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReturnView {

    private final RatingController ratingController = new RatingController();
    private final UserController userController = new UserController();
    private final DocumentController documentController = new DocumentController();
    private final ReturnController returnController = new ReturnController();
    private final BorrowController borrowController = new BorrowController();

    @FXML
    private javafx.scene.control.TextField ratingField;

    @FXML
    private ComboBox<String> bookNameComboBox;

    @FXML
    private ComboBox<String> usersComboBox;

    @FXML
    private VBox reviewListVBox;

    private PauseTransition bookPause = new PauseTransition(Duration.millis(300));
    private PauseTransition userPause = new PauseTransition(Duration.millis(300));

    private ObservableList<String> items;
    private ObservableList<String> usersItem;

    @FXML
    public void initialize() {
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

        // Hiển thị tất cả đánh giá ban đầu
        loadAllReviews();
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

    private void loadAllReviews() {
        reviewListVBox.getChildren().clear();
        List<Rating> ratings = ratingController.getAllRatings();
        for (Rating rating : ratings) {
            User user = userController.getUserById(rating.getUser_id());
            Document document = documentController.getDocumentById(rating.getDocument_id());
            addReview(user.getName(), rating.getComment(), document.getTitle());
        }
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
        if (borrowRecord == null) {
            showAlert(Alert.AlertType.ERROR, "Người dùng chưa mượn sách",
                    "Người dùng này chưa mượn quyển sách: " + bookText);
            return;
        }

        // Tạo ReturnRecord mới
        ReturnRecord returnRecord = new ReturnRecord(
                UUID.randomUUID(),
                borrowRecord.getId(),
                LocalDate.now(),
                userId);
        boolean isSuccess = returnController.addReturnRecord(returnRecord);
        // returnRecordController.addReturnRecord(returnRecord);
        if (isSuccess) {
            String comment = ratingField.getText().trim();

            showAlert(Alert.AlertType.INFORMATION, "Thành công",
                    "Trả sách thành công: " + bookText +
                            " bởi " + username);
            if (!comment.isEmpty()) {
                Rating rating = new Rating(
                        UUID.randomUUID(),
                        documentId,
                        userId,
                        LocalDate.now(),
                        comment);
                ratingController.addRating(rating);

                addReview(username, comment, bookText);
            }
            // Xoá field sau khi submit
            ratingField.clear();
            bookNameComboBox.getEditor().clear();
            usersComboBox.getEditor().clear();
        } else {

            showAlert(Alert.AlertType.ERROR, "Thất bại",
                    "Trả sách thất bại!");
        }

    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addReview(String username, String review, String bookName) {
        Label label = new Label(username + " : " + review + " (" + bookName + ")");
        label.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 3 0 3 0; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 3px;");

        reviewListVBox.getChildren().add(0, label);
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
