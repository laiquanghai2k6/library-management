package app;

import service.DocumentService;

public class Main {
    public static void main(String[] args) {
        System.out.println("🔍 Đang kiểm tra kết nối và truy vấn tài liệu...\n");
        //Testing
        DocumentService service = new DocumentService();

        
        service.getAllDocuments().forEach(System.out::println);

    
    }
}
