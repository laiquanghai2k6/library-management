package test;

import model.BorrowRecord;
import model.Document;
import model.ReturnRecord;
import model.User;
import org.junit.jupiter.api.*;
import service.BorrowService;
import service.DocumentService;
import service.ReturnService;
import service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReturnServiceTest {

    private static ReturnService returnService;
    private static BorrowService borrowService;
    private static DocumentService documentService;
    private static UserService userService;

    private static User testUser;
    private static Document testDocument;
    private static BorrowRecord testBorrow;
    private static ReturnRecord testReturn;

    @BeforeAll
    static void setup() {
        returnService = new ReturnService();
        borrowService = new BorrowService();
        documentService = new DocumentService();
        userService = new UserService();

        // --- Tạo user tạm ---
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
        userService.addUser(testUser);
        UUID userId = userService.getUserIdByEmail(testUser.getEmail());
        testUser.setId(userId);

        // --- Tạo document tạm ---
        testDocument = new Document();
        testDocument.setTitle("Test Document");
        testDocument.setAuthor("Test Author");
        documentService.addDocument(testDocument);
        UUID docId = documentService.getDocumentIdByName(testDocument.getTitle());
        testDocument.setId(docId);

        // --- Tạo borrow tạm ---
        testBorrow = new BorrowRecord();
        testBorrow.setUser_id(testUser.getId());
        testBorrow.setDocument_id(testDocument.getId());
        testBorrow.setBorrow_date(LocalDate.now());
        boolean borrowed = borrowService.borrowBook(testBorrow);
        assertTrue(borrowed, "Thêm bản ghi mượn thất bại");

        BorrowRecord fetchedBorrow = borrowService.getBorrowRecord(testDocument.getId(), testUser.getId());
        assertNotNull(fetchedBorrow, "Không tìm thấy borrow vừa tạo");
        testBorrow.setId(fetchedBorrow.getId());
    }

    @Test
    @Order(1)
    void testReturnBook() {
        // --- Tạo ReturnRecord ---
        testReturn = new ReturnRecord();
        testReturn.setUser_id(testUser.getId());
        testReturn.setBorrow_id(testBorrow.getId());
        testReturn.setReturn_date(LocalDate.now());

        boolean returned = returnService.returnBook(testReturn);
        assertTrue(returned, "Thêm bản ghi trả thất bại");

        // Lấy ID mới từ DB
        List<ReturnRecord> returns = returnService.getAllReturns();
        ReturnRecord fetched = returns.stream()
                .filter(r -> r.getBorrow_id().equals(testBorrow.getId()))
                .findFirst()
                .orElse(null);
        assertNotNull(fetched, "Không tìm thấy bản ghi trả vừa thêm");
        testReturn.setId(fetched.getId());
    }

    @Test
    @Order(2)
    void testGetAllReturns() {
        List<ReturnRecord> allReturns = returnService.getAllReturns();
        assertTrue(allReturns.size() > 0, "Danh sách trả rỗng");
    }

    @AfterAll
    static void cleanup() {
        // --- Xóa return ---
        if (testReturn != null && testReturn.getId() != null) {
            returnService.deleteReturn(testReturn.getId());
        }

        // --- Xóa borrow ---
        if (testBorrow != null && testBorrow.getId() != null) {
            borrowService.deleteBorrow(testBorrow.getId());
        }

        // --- Xóa document ---
        if (testDocument != null && testDocument.getId() != null) {
            documentService.deleteDocument(testDocument.getId());
        }

        // --- Xóa user ---
        if (testUser != null && testUser.getId() != null) {
            userService.deleteUser(testUser.getId());
        }
    }
}
