package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.BorrowRecord;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.util.List;

public class BorrowService {

    private static final Gson gson = new Gson();

    public List<BorrowRecord> getAllBorrows() {
        try {
            String json = SupabaseClient.get("/rest/v1/borrows?select=*");
            Type listType = new TypeToken<List<BorrowRecord>>() {}.getType();
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
