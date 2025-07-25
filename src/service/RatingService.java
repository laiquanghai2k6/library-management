package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Rating;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.util.List;

public class RatingService {

    private static final Gson gson = new Gson();

    public List<Rating> getRatingsByDocumentId(int documentId) {
        try {
            String json = SupabaseClient.get("/rest/v1/ratings?document_id=eq." + documentId);
            Type listType = new TypeToken<List<Rating>>() {}.getType();
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
