package app;
import view.TestJavaFX;
import service.DocumentService;
public class Main {
     public static void main(String[] args) {
        //TestJavaFX.main(args);
        System.out.println("Đang kiểm tra kết nối và truy vấn tài liệu...\n");
        DocumentService.displayAllDocuments();
    }
}
