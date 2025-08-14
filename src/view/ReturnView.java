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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.DocumentController;
import controller.RatingController;
import controller.UserController;
import model.Document;
import model.Rating;
import model.User;

import java.io.IOException;
import java.util.List;

public class ReturnView {

    private final RatingController ratingController = new RatingController();
    private final UserController userController = new UserController();
    private final DocumentController documentController = new DocumentController();

    @FXML
    private ComboBox<String> bookNameComboBox;

    @FXML
    private ComboBox<String> usersComboBox;

    @FXML
    private VBox reviewListVBox;

    private PauseTransition pause = new PauseTransition(Duration.millis(300));

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

            pause.setOnFinished(event -> {
                autocompleteComboBox(newText);
            });
            pause.playFromStart();
        });

        usersComboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            // Nếu đang chọn item trùng với text thì bỏ qua
            if (usersComboBox.getSelectionModel().getSelectedItem() != null &&
                    newText.equals(usersComboBox.getSelectionModel().getSelectedItem())) {
                return;
            }

            pause.setOnFinished(event -> {
                autocompleteUserComboBox(newText);
            });
            pause.playFromStart();
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
            usersItem.add(user.getName()+" - "+user.getEmail());
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

    private void addReview(String username, String review, String bookName) {
        Label label = new Label(username + " : " + review + " (" + bookName + ")");
        label.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 3 0 3 0; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 3px;");
        
        reviewListVBox.getChildren().add(label);
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
