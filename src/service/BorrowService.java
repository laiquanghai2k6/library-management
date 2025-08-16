package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import adapter.LocalDateTypeAdapter;
import adapter.UUIDTypeAdapter;
import model.BorrowRecord;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BorrowService {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    public BorrowRecord getBorrowRecord(UUID documentId, UUID userId) {
        try {
            String json = SupabaseClient.get("/rest/v1/borrows?user_id=eq." + userId.toString()
                    + "&document_id=eq." + documentId.toString());

            Type listType = new TypeToken<List<BorrowRecord>>() {
            }.getType();
            List<BorrowRecord> records = gson.fromJson(json, listType);
            System.out.println("records"+records);
            if (records != null && !records.isEmpty()) {
                return records.get(0); 
            }
            return null; 
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy BorrowRecord:");
            e.printStackTrace();
            return null;
        }
    }

    public List<BorrowRecord> getBorrowByUserId(UUID userId, String bookId) {
        try {

            String json = SupabaseClient.get("/rest/v1/borrows?user_id=eq." + userId.toString() + "&select=*");

            Type listType = new TypeToken<List<BorrowRecord>>() {
            }.getType();

            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy danh sách mượn:");
            e.printStackTrace();
            return List.of();
        }
    }

    public List<BorrowRecord> getAllBorrows() {
        try {
            String json = SupabaseClient.get("/rest/v1/borrows?select=*");
            Type listType = new TypeToken<List<BorrowRecord>>() {
            }.getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy danh sách mượn:");
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean borrowBook(BorrowRecord record) {
        try {
            String jsonBody = gson.toJson(record);
            int status = SupabaseClient.post("/rest/v1/borrows", jsonBody);
            return status == 201;
        } catch (Exception e) {
            System.out.println(" Lỗi khi mượn sách:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBorrow(int id) {
        try {
            int status = SupabaseClient.delete("/rest/v1/borrows?id=eq." + id);
            return status == 204;
        } catch (Exception e) {
            System.out.println(" Lỗi khi xóa bản ghi mượn:");
            e.printStackTrace();
            return false;
        }
    }
}
