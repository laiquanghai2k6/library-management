package test;

import model.User;
import org.junit.jupiter.api.*;
import service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    private static UserService userService;
    private static User testUser;

    @BeforeAll
    static void setup() {
        userService = new UserService();
    }

    @Test
    @Order(1)
    void testAddUser() {
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");

        boolean added = userService.addUser(testUser);
        assertTrue(added, "Thêm user thất bại");

        // Lấy ID từ email
        UUID userId = userService.getUserIdByEmail(testUser.getEmail());
        assertNotNull(userId, "Không lấy được ID user vừa tạo");
        testUser.setId(userId);
    }

    @Test
    @Order(2)
    void testGetUserById() {
        User fetched = userService.getUserById(testUser.getId());
        assertNotNull(fetched, "Không tìm thấy user theo ID");
        assertEquals(testUser.getEmail(), fetched.getEmail());
    }

    @Test
    @Order(3)
    void testSearchUserByName() {
        List<User> results = userService.searchUserByName("Test User");
        assertTrue(results.stream().anyMatch(u -> u.getId().equals(testUser.getId())),
                "Không tìm thấy user trong search theo tên");
    }

    @Test
    @Order(4)
    void testGetAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        assertTrue(allUsers.size() > 0, "Danh sách user rỗng");
    }

    @Test
    @Order(5)
    void testUpdateUser() {
        testUser.setName("Updated Test User");
        boolean updated = userService.updateUser(testUser);
        assertTrue(updated, "Cập nhật user thất bại");

        User fetched = userService.getUserById(testUser.getId());
        assertEquals("Updated Test User", fetched.getName(), "Tên user không được cập nhật");
    }

    @AfterAll
    static void cleanup() {
        if (testUser != null && testUser.getId() != null) {
            boolean deleted = userService.deleteUser(testUser.getId());
            assertTrue(deleted, "Xóa user thất bại");
        }
    }
}
