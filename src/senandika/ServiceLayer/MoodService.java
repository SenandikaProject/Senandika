/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senandika.ServiceLayer;

// MoodService.java
import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import senandika.Model.Mood;

public class MoodService {
    private final String BASE_URL = "http://localhost:5000/api/moods";

    // Method POST: Untuk mengirim dan menyimpan data mood ke database API
    public String addMood(Mood mood) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Menyesuaikan format JSON payload sesuai penamaan kolom backend Supabase Anda
        String jsonInput = String.format(
                "{\"tanggal\":\"%s\",\"tingkat_mood\":%d,\"catatan\":\"%s\"}",
                mood.getTanggal(), mood.getTingkatMood(), mood.getCatatan()
        );

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() >= 200 && conn.getResponseCode() < 300 
                ? conn.getInputStream() : conn.getErrorStream()
        ));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        conn.disconnect();
        return response.toString();
    }

    // Method GET: Mengambil riwayat mood real-time dari database API
    public List<Mood> getMoodHistory() {
        List<Mood> moodList = new ArrayList<>();
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                // Parsing Array JSON Response dari database menggunakan Gson
                JsonArray array = JsonParser.parseString(response.toString()).getAsJsonArray();
                for (JsonElement element : array) {
                    JsonObject obj = element.getAsJsonObject();
                    Mood m = new Mood();
                    m.setId(obj.get("id").getAsInt());
                    m.setTanggal(obj.get("tanggal").getAsString());
                    
                    // SINKRONISASI BARU: Memetakan nilai created_at dari database Supabase Anda
                    if (obj.has("created_at") && !obj.get("created_at").isJsonNull()) {
                        m.setCreatedAt(obj.get("created_at").getAsString());
                    } else {
                        m.setCreatedAt(obj.get("tanggal").getAsString()); // Fallback jika null
                    }
                    
                    // Antisipasi jika backend merespon dengan key tingkat_mood sesuai Supabase
                    if (obj.has("tingkat_mood")) {
                        m.setTingkatMood(obj.get("tingkat_mood").getAsInt());
                    } else if (obj.has("tingkatMood")) {
                        m.setTingkatMood(obj.get("tingkatMood").getAsInt());
                    }
                    
                    m.setCatatan(obj.has("catatan") && !obj.get("catatan").isJsonNull() 
                            ? obj.get("catatan").getAsString() : "");
                    
                    moodList.add(m);
                }
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error getMoodHistory: " + e.getMessage());
        }
        return moodList;
    }

    // Method Kalkulasi Rata-rata Otomatis dari Data Terbaru Database
    public double getAverageMood() {
        List<Mood> history = getMoodHistory();
        if (history.isEmpty()) return 0;

        int total = 0;
        for (Mood m : history) {
            total += m.getTingkatMood();
        }
        return (double) total / history.size();
    }
}