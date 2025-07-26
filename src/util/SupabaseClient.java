package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class SupabaseClient {
    private static final String SUPABASE_URL = System.getenv("SUPABASE_URL");
    private static final String SUPABASE_API_KEY = System.getenv("SUPABASE_API_KEY");

    private static HttpURLConnection setupConnection(String path, String method) throws IOException {
        URL url = new URL(SUPABASE_URL + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod(method);
        conn.setRequestProperty("apikey", SUPABASE_API_KEY);
        conn.setRequestProperty("Authorization", "Bearer " + SUPABASE_API_KEY);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        return conn;
    }

    public static String get(String path) throws IOException {
        HttpURLConnection conn = setupConnection(path, "GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("GET failed with HTTP " + responseCode);
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return in.lines().collect(Collectors.joining());
        }
    }

    public static int post(String path, String jsonBody) throws IOException {
        HttpURLConnection conn = setupConnection(path, "POST");

        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode();

        if (responseCode != 201) {
            System.out.println(" POST thất bại với mã: " + responseCode);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                System.out.println(" Phản hồi lỗi từ Supabase: " + response);
            }
        }

        return responseCode;
    }

    public static int patch(String path, String jsonBody) throws Exception {
        URL url = new URL(SUPABASE_URL + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST"); // Trick: POST + override PATCH
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");

        conn.setRequestProperty("apikey", SUPABASE_API_KEY);
        conn.setRequestProperty("Authorization", "Bearer " + SUPABASE_API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode();

        if (responseCode != 204) {
            System.out.println(" PATCH thất bại với mã: " + responseCode);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                System.out.println(" Phản hồi lỗi từ Supabase (PATCH): " + response);
            }
        }

        return responseCode;
    }

    public static int delete(String path) throws IOException {
        HttpURLConnection conn = setupConnection(path, "DELETE");

        int responseCode = conn.getResponseCode();

        if (responseCode != 204) {
            System.out.println(" DELETE thất bại với mã: " + responseCode);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                System.out.println(" Phản hồi lỗi từ Supabase (DELETE): " + response);
            }
        }

        return responseCode;
    }

}
