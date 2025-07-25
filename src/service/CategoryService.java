package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Category;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.util.List;

public class CategoryService {

    private static final Gson gson = new Gson();

    public List<Category> getAllCategories() {
        try {
            String json = SupabaseClient.get("/rest/v1/categories?select=*");
            Type listType = new TypeToken<List<Category>>() {}.getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách danh mục:");
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean addCategory(Category category) {
        try {
            String jsonBody = gson.toJson(category);
            int status = SupabaseClient.post("/rest/v1/categories", jsonBody);
            return status == 201;
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm danh mục:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCategory(int id) {
        try {
            int status = SupabaseClient.delete("/rest/v1/categories?id=eq." + id);
            return status == 204;
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa danh mục:");
            e.printStackTrace();
            return false;
        }
    }
}
