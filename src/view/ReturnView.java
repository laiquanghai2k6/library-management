package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import controller.DocumentController;
import controller.RatingController;
import controller.UserController;
import model.Document;
import model.Rating; 
import model.User;

public class ReturnView {
    private final RatingController ratingController = new RatingController();
    private final UserController userController = new UserController();
    private final DocumentController documentController = new DocumentController();

    @FXML
    private VBox reviewListVBox;

    @FXML
    public void initialize() {
        List<Rating> ratings = ratingController.getAllRatings();
        for (Rating rating : ratings) {
            User user = userController.getUserById(rating.getUser_id());
            Document document = documentController.getDocumentById(rating.getDocument_id());
            String reviewText = rating.getComment(); 

            String reviewDisplay =   reviewText;
            addReview(user.getName(), reviewDisplay,document.getTitle());
        }
    }

    private void addReview(String username, String review, String bookName) {
        Label label = new Label(username + " : " + review + " (" +bookName +")");
        label.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-padding: 3 0 3 0; " +
                "-fx-text-fill: black; " + 
                "-fx-border-color: white; " + 
                "-fx-border-width: 1px; " + 
                "-fx-border-radius: 3px;" 
        );
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
