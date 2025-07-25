package app;

import model.Document;
import service.DocumentService;

public class Main {
    public static void main(String[] args) {
        System.out.println("🔍 Đang kiểm tra kết nối và truy vấn tài liệu...\n");
        //Testing
        DocumentService service = new DocumentService();

        
        service.getAllDocuments().forEach(System.out::println);

    
        Document newDoc = new Document();
        newDoc.setTitle("Java 21 Mastery");
        newDoc.setAuthor("Trần Văn B");
        newDoc.setIsbn("9876543210");
        newDoc.setQuantity(3);
        newDoc.setCategory("Kỹ thuật");
        boolean added = service.addDocument(newDoc);
        System.out.println(added ? " Thêm thành công" : " Thêm thất bại");

   
        Document updateDoc = new Document();
        updateDoc.setId(1); 
        updateDoc.setTitle("Clean Code (Update)");
        updateDoc.setAuthor("Robert C. Martin");
        updateDoc.setIsbn("9780132350884");
        updateDoc.setQuantity(6);
        updateDoc.setCategory("Lập trình");
        boolean updated = service.updateDocument(updateDoc);
        System.out.println(updated ? " Cập nhật thành công" : " Cập nhật thất bại");

        
        boolean deleted = service.deleteDocument(3); 
        System.out.println(deleted ? " Xóa thành công" : " Xóa thất bại");
    }
}
