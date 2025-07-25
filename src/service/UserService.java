package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.util.List;

public class UserService {

    private static final Gson gson = new Gson();

    public List<User> getAllUsers() {
        try {
            String json = SupabaseClient.get("/rest/v1/users?select=*");
            Type listType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy danh sách người dùng:");
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean addUser(User user) {
        try {
            String jsonBody = gson.toJson(user);
            int status = SupabaseClient.post("/rest/v1/users", jsonBody);
            return status == 201;
        } catch (Exception e) {
            System.out.println(" Lỗi khi thêm người dùng:");
            e.printStackTrace();
            return false;
        }
    }


}
