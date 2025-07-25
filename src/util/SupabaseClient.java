package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class SupabaseClient {
    private static final String SUPABASE_URL = System.getenv("SUPABASE_URL");
    private static final String SUPABASE_KEY = System.getenv("SUPABASE_API_KEY");

    public static String getDocumentsJson() throws Exception {
        URL url = new URL(SUPABASE_URL + "/rest/v1/documents?select=*");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("apikey", SUPABASE_KEY);
        conn.setRequestProperty("Authorization", "Bearer " + SUPABASE_KEY);
        conn.setRequestProperty("Accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String result = in.lines().collect(Collectors.joining());
        in.close();

        return result;
    }
}
