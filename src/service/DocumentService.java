package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import adapter.LocalDateTypeAdapter;
import adapter.UUIDTypeAdapter;
import model.Document;
import util.SupabaseClient;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DocumentService {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    public Document getDocumentById(UUID id) {
        try {
            String json = SupabaseClient.get("/rest/v1/documents?id=eq." + id);
            Type listType = new TypeToken<List<Document>>() {
            }.getType();
            List<Document> documents = gson.fromJson(json, listType);
            if (documents != null && !documents.isEmpty()) {
                return documents.get(0); 
            }
            return null; 
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy document theo id:");
            e.printStackTrace();
            return null;
        }
    }

    public List<Document> getAllDocuments() {
        try {
            String json = SupabaseClient.get("/rest/v1/documents?select=*");
            Type listType = new TypeToken<List<Document>>() {
            }.getType();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(" Lỗi khi lấy danh sách tài liệu:");
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean addDocument(Document doc) {
        try {
            String jsonBody = gson.toJson(doc);
            int status = SupabaseClient.post("/rest/v1/documents", jsonBody);
            return status == 201;
        } catch (Exception e) {
            System.out.println(" Lỗi khi thêm tài liệu:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDocument(Document doc) {
        try {
            String jsonBody = gson.toJson(doc);
            int status = SupabaseClient.patch("/rest/v1/documents?id=eq." + doc.getId(), jsonBody);
            return status == 204;
        } catch (Exception e) {
            System.out.println(" Lỗi khi cập nhật tài liệu:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDocument(int id) {
        try {
            int status = SupabaseClient.delete("/rest/v1/documents?id=eq." + id);
            return status == 204;
        } catch (Exception e) {
            System.out.println(" Lỗi khi xóa tài liệu:");
            e.printStackTrace();
            return false;
        }
    }
}
