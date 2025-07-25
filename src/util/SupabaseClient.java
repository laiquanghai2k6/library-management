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

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
            os.flush();
        }

        return conn.getResponseCode();
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

        return conn.getResponseCode();
    }

    public static int delete(String path) throws IOException {
        HttpURLConnection conn = setupConnection(path, "DELETE");
        return conn.getResponseCode();
    }
}
