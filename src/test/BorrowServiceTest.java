package test;

import model.BorrowRecord;
import model.Document;
import model.User;
import org.junit.jupiter.api.*;
import service.BorrowService;
import service.DocumentService;
import service.UserService;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BorrowServiceTest {

    private static BorrowService borrowService;
    private static DocumentService documentService;
    private static UserService userService;

    private static User testUser;
    private static Document testDocument;
    private static BorrowRecord testBorrow;

    @BeforeAll
    static void setup() {
        borrowService = new BorrowService();
        documentService = new DocumentService();
        userService = new UserService();

        // user tạm
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
        userService.addUser(testUser);

        // ID vừa tạo
        UUID userId = userService.getUserIdByEmail(testUser.getEmail());
        testUser.setId(userId);

        // document tạm
        testDocument = new Document();
        testDocument.setTitle("Test Document");
        testDocument.setAuthor("Test Author"); // Bắt buộc
        documentService.addDocument(testDocument);

        // ID vừa tạo
        UUID docId = documentService.getDocumentIdByName(testDocument.getTitle());
        testDocument.setId(docId);

    }

    @Test
    @Order(1)
    void testBorrowAndDelete() {
        // BorrowRecord
        testBorrow = new BorrowRecord();
        testBorrow.setUser_id(testUser.getId());
        testBorrow.setDocument_id(testDocument.getId());
        testBorrow.setBorrow_date(LocalDate.now());

        boolean added = borrowService.borrowBook(testBorrow);
        assertTrue(added, "Thêm bản ghi mượn thất bại");

        BorrowRecord fetched = borrowService.getBorrowRecord(testDocument.getId(), testUser.getId());
        assertNotNull(fetched, "Không tìm thấy bản ghi mượn vừa thêm");

        // Xóa
        boolean deleted = borrowService.deleteBorrow(fetched.getId());
        assertTrue(deleted, "Xóa bản ghi mượn thất bại");
    }

    @AfterAll
    static void cleanup() {
        // Xóa bản ghi mượn (nếu còn)
        if (testBorrow != null && testBorrow.getId() != null) {
            borrowService.deleteBorrow(testBorrow.getId());
        }

        // Xóa document test
        if (testDocument != null && testDocument.getId() != null) {
            documentService.deleteDocument(testDocument.getId());
        }

        // Xóa user test
        if (testUser != null && testUser.getId() != null) {
            userService.deleteUser(testUser.getId());
        }
    }

}
