package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private static final Gson gson = new Gson();

    public List<User> getAllUsers() {
        try {
            String json = SupabaseClient.get("/rest/v1/users?select=*");
            System.out.println(json);

            Type listType = new TypeToken<List<User>>() {
            }.getType();
            return gson.fromJson(json, listType);
            
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy danh sách người dùng:");
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean addUser(User user) {
        try {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", user.getName());
            jsonMap.put("email", user.getEmail());

            String jsonBody = gson.toJson(jsonMap);
            int status = SupabaseClient.post("/rest/v1/users", jsonBody);
            return status == 201;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("name", user.getName());
            updateMap.put("email", user.getEmail());
            System.out.println(user.getName());
            // Không đưa id vào payload
            System.out.println(user.getId());
            String jsonBody = gson.toJson(updateMap);

            int status = SupabaseClient.patch("/rest/v1/users?id=eq." + user.getId(), jsonBody);

            return status == 204;
        } catch (Exception e) {
            System.err.println("Lỗi update user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
