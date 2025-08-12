package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import adapter.LocalDateTypeAdapter;
import adapter.UUIDTypeAdapter;
import model.Rating;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RatingService {

    private static final Gson gson = new GsonBuilder()
    .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
    .create();

    public List<Rating> getAllRatings() {
        try {
            String json = SupabaseClient.get("/rest/v1/ratings?select=*");
            Type listType = new TypeToken<List<Rating>>() {
            }.getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy đánh giá tài liệu:");
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Rating> getRatingsByDocumentId(UUID documentId) {
        try {
            String json = SupabaseClient.get("/rest/v1/ratings?document_id=eq." + documentId);
            Type listType = new TypeToken<List<Rating>>() {
            }.getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy đánh giá:");
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean addRating(Rating rating) {
        try {
            String jsonBody = gson.toJson(rating);
            int status = SupabaseClient.post("/rest/v1/ratings", jsonBody);
            return status == 201;
        } catch (Exception e) {
            System.out.println(" Lỗi khi thêm đánh giá:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRating(int id) {
        try {
            int status = SupabaseClient.delete("/rest/v1/ratings?id=eq." + id);
            return status == 204;
        } catch (Exception e) {
            System.out.println(" Lỗi khi xóa đánh giá:");
            e.printStackTrace();
            return false;
        }
    }
}
