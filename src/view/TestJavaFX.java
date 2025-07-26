package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import service.DocumentService;

public class TestJavaFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("Click to load documents");

        btn.setOnAction(e -> {
            System.out.println("Đang kiểm tra kết nối và truy vấn tài liệu...\n");
            DocumentService service = new DocumentService();
            service.getAllDocuments().forEach(System.out::println);
        });

        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
