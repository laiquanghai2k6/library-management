package service;

import util.SupabaseClient;

public class DocumentService {
    public static void displayAllDocuments() {
        try {
            String json = SupabaseClient.getDocumentsJson();
            System.out.println("📚 Danh sách tài liệu (JSON):");
            System.out.println(json);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi truy vấn Supabase API:");
            e.printStackTrace();
        }
    }
}
