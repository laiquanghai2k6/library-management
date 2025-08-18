package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import adapter.LocalDateTypeAdapter;
import adapter.UUIDTypeAdapter;
import model.ReturnRecord;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReturnService {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    

    public List<ReturnRecord> getAllReturns() {
        try {
            String json = SupabaseClient.get("/rest/v1/returns?select=*");
            Type listType = new TypeToken<List<ReturnRecord>>() {
            }.getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy danh sách trả:");
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean returnBook(ReturnRecord record) {
        try {
            String jsonBody = gson.toJson(record);
            int status = SupabaseClient.post("/rest/v1/returns", jsonBody);
            return status == 201;
        } catch (Exception e) {
            System.out.println(" Lỗi khi trả sách:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReturn(UUID id) {
        try {
            int status = SupabaseClient.delete("/rest/v1/returns?id=eq." + id);
            return status == 204;
        } catch (Exception e) {
            System.out.println(" Lỗi khi xóa bản ghi trả:");
            e.printStackTrace();
            return false;
        }
    }
}
