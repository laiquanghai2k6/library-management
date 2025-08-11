package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

public class ReturnView {
    @FXML
    private VBox reviewListVBox;

    @FXML
    public void initialize() {
        // Thêm thử danh sách đánh giá demo
        addReview("Nguyễn Văn A", "Đánh giá: 5 sao");
        addReview("Trần Thị B", "Đánh giá: 4 sao");
        addReview("Lê Văn C", "Đánh giá: 3 sao");
        addReview("Phạm Thị D", "Đánh giá: 4 sao");
    }

    private void addReview(String username, String review) {
        Label label = new Label(username + " : " + review);
        label.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 3 0 3 0; " +
                        "-fx-text-fill: black; " + // màu chữ đen
                        "-fx-border-color: white; " + // viền trắng
                        "-fx-border-width: 1px; " + // độ dày viền
                        "-fx-border-radius: 3px;" // bo góc viền (tuỳ chọn)
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
