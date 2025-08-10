package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
public class TestJavaFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Role.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            /* String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css); */
            primaryStage.setTitle("Phần mềm quản lí thư viện");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
