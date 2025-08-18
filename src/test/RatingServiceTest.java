package test;

import model.Document;
import model.Rating;
import model.User;
import org.junit.jupiter.api.*;
import service.DocumentService;
import service.RatingService;
import service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingServiceTest {

    private static RatingService ratingService;
    private static DocumentService documentService;
    private static UserService userService;

    private static User testUser;
    private static Document testDocument;
    private static Rating testRating;

    @BeforeAll
    static void setup() {
        ratingService = new RatingService();
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
        testDocument.setAuthor("Test Author"); // Bắt buộc
        documentService.addDocument(testDocument);
        UUID docId = documentService.getDocumentIdByName(testDocument.getTitle());
        testDocument.setId(docId);
    }

    @Test
    @Order(1)
    void testAddRating() {
        testRating = new Rating();
        testRating.setUser_id(testUser.getId());
        testRating.setDocument_id(testDocument.getId());
        testRating.setComment("Test comment");
        testRating.setCreated_at(LocalDate.now());

        boolean added = ratingService.addRating(testRating);
        assertTrue(added, "Thêm rating thất bại");
    }

    @Test
    @Order(2)
    void testGetRatingsByDocumentId() {
        List<Rating> ratings = ratingService.getRatingsByDocumentId(testDocument.getId());
        assertFalse(ratings.isEmpty(), "Không tìm thấy rating cho documentId này");

        // Lưu lại ID để xóa
        testRating.setId(ratings.get(0).getId());
    }

    @Test
    @Order(3)
    void testGetAllRatings() {
        List<Rating> allRatings = ratingService.getAllRatings();
        assertTrue(allRatings.size() > 0, "Danh sách rating rỗng");
    }

    @AfterAll
    static void cleanup() {
        // --- Xóa rating ---
        if (testRating.getId() != null) {
            ratingService.deleteRating(testRating.getId());
        }

        // --- Xóa document ---
        if (testDocument.getId() != null) {
            documentService.deleteDocument(testDocument.getId());
        }

        // --- Xóa user ---
        if (testUser.getId() != null) {
            userService.deleteUser(testUser.getId());
        }
    }
}
