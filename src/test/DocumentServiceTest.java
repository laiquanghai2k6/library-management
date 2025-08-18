package test;

import model.Document;
import org.junit.jupiter.api.*;
import service.DocumentService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DocumentServiceTest {

    private static DocumentService documentService;
    private static Document testDocument;

    @BeforeAll
    static void setup() {
        documentService = new DocumentService();

        // --- Tạo document tạm ---
        testDocument = new Document();
        testDocument.setTitle("Test Document");
        testDocument.setAuthor("Test Author"); // Trường bắt buộc
        testDocument.setIsbn("1234567890");    // Nếu bảng có cột isbn not null

        boolean added = documentService.addDocument(testDocument);
        assertTrue(added, "Thêm document thất bại");

        // Lấy ID vừa tạo
        UUID docId = documentService.getDocumentIdByName(testDocument.getTitle());
        assertNotNull(docId, "Không tìm thấy document vừa thêm");
        testDocument.setId(docId);
    }

    @Test
    @Order(1)
    void testGetDocumentById() {
        Document fetched = documentService.getDocumentById(testDocument.getId());
        assertNotNull(fetched, "Không lấy được document theo ID");
        assertEquals(testDocument.getTitle(), fetched.getTitle());
        assertEquals(testDocument.getAuthor(), fetched.getAuthor());
        assertEquals(testDocument.getIsbn(), fetched.getIsbn());
    }

    @Test
    @Order(2)
    void testSearchDocumentsByName() {
        List<Document> results = documentService.searchDocumentsByName("Test Document");
        assertFalse(results.isEmpty(), "Search không trả về kết quả");
        boolean found = results.stream().anyMatch(doc -> doc.getId().equals(testDocument.getId()));
        assertTrue(found, "Không tìm thấy document test trong kết quả search");
    }

    @Test
    @Order(3)
    void testUpdateDocument() {
        testDocument.setTitle("Updated Document");
        boolean updated = documentService.updateDocument(testDocument);
        assertTrue(updated, "Cập nhật document thất bại");

        Document fetched = documentService.getDocumentById(testDocument.getId());
        assertEquals("Updated Document", fetched.getTitle());
    }

    @AfterAll
    static void cleanup() {
        if (testDocument != null && testDocument.getId() != null) {
            boolean deleted = documentService.deleteDocument(testDocument.getId());
            assertTrue(deleted, "Xóa document thất bại");
        }
    }
}
