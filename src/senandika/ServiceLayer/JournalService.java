package senandika.ServiceLayer;

import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import senandika.Model.JournalData;

public class JournalService {

    public final String BASE_URL = "http://localhost:5000/api/journals";

    public String createJournal(String judul, String isi, File imageFile) throws IOException {
        String boundary = Long.toHexString(System.currentTimeMillis());
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setDoOutput(true);
        
        OutputStream output = conn.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
        
        // Field Judul
        writer.append("--").append(boundary).append("\r\n");
        writer.append("Content-Disposition: form-data; name=\"judul\"");
        writer.append("\r\n\r\n").append(judul).append("\r\n");
        writer.flush();
        
        // Field Isi
        writer.append("--").append(boundary).append("\r\n");
        writer.append("Content-Disposition: form-data; name=\"isi\"");
        writer.append("\r\n\r\n").append(isi).append("\r\n");
        writer.flush();
        
        // Upload File
        if (imageFile != null) {
            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"").append(imageFile.getName()).append("\"");
            writer.append("\r\n");
            writer.append("Content-Type: image/jpeg");
            writer.append("\r\n\r\n");
            writer.flush();

            try (FileInputStream input = new FileInputStream(imageFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.flush();
            }
            writer.append("\r\n");
            writer.flush();
        }
        
        writer.append("--").append(boundary).append("--").append("\r\n");
        writer.close();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }

    public List<JournalData> getJournals() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        System.out.println(response.toString());

        JsonArray data = JsonParser.parseString(response.toString()).getAsJsonArray();
        List<JournalData> journals = new ArrayList<>();

        for (JsonElement element : data) {
            JsonObject obj = element.getAsJsonObject();
            JournalData journal = new JournalData();

            journal.setId(obj.get("id").getAsInt());
            journal.setJudul(obj.get("judul").getAsString());
            journal.setIsi(obj.get("isi").getAsString());
            journal.setTanggal(obj.get("tanggal").getAsString());
            journal.setStreak(obj.get("streak").getAsInt());

            if(obj.has("image_path") && !obj.get("image_path").isJsonNull()) {
                journal.setImagePath(obj.get("image_path").getAsString());
            }

            // Memastikan penarikan data created_at aman
            if(obj.has("created_at") && !obj.get("created_at").isJsonNull()) {
                journal.setCreatedAt(obj.get("created_at").getAsString());
            }

            journals.add(journal);
        }

        return journals;
    }

    public List<JournalData> getFilteredJournals(String filter, String searchQuery) throws Exception {
        String encodedFilter = URLEncoder.encode(filter, "UTF-8");
        String urlString = BASE_URL + "?filter=" + encodedFilter;

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        JsonArray data = JsonParser.parseString(response.toString()).getAsJsonArray();
        List<JournalData> journals = new ArrayList<>();

        for (JsonElement element : data) {
            JsonObject obj = element.getAsJsonObject();
            JournalData journal = new JournalData();

            journal.setId(obj.get("id").getAsInt()); 
            journal.setJudul(obj.get("judul").getAsString());
            journal.setIsi(obj.get("isi").getAsString());
            journal.setTanggal(obj.get("tanggal").getAsString());
            journal.setStreak(obj.get("streak").getAsInt());

            if (obj.has("image_path") && !obj.get("image_path").isJsonNull()) {
                journal.setImagePath(obj.get("image_path").getAsString());
            }
            
            // DISESUAIKAN: Menambahkan ekstraksi created_at pada filter pencarian/waktu
            if (obj.has("created_at") && !obj.get("created_at").isJsonNull()) {
                journal.setCreatedAt(obj.get("created_at").getAsString());
            }
            
            journals.add(journal);
        }
        return journals;
    }

    public int getStreak() throws Exception {
        URL url = new URL(BASE_URL + "/streak");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
        return json.get("streak").getAsInt();
    }
    
    public JournalData getJournalById(int id) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while((line = br.readLine()) != null){
            response.append(line);
        }

        JsonObject obj = JsonParser.parseString(response.toString()).getAsJsonObject();
        JournalData journal = new JournalData();

        journal.setId(obj.get("id").getAsInt());
        journal.setJudul(obj.get("judul").getAsString());
        journal.setIsi(obj.get("isi").getAsString());
        journal.setTanggal(obj.get("tanggal").getAsString());

        // DISESUAIKAN: Pengecekan null-safety path gambar agar tidak memicu crash NullPointerException
        if (obj.has("image_path") && !obj.get("image_path").isJsonNull()) {
            journal.setImagePath(obj.get("image_path").getAsString());
        } else {
            journal.setImagePath(null);
        }
        
        if (obj.has("created_at") && !obj.get("created_at").isJsonNull()) {
            journal.setCreatedAt(obj.get("created_at").getAsString());
        }

        return journal;
    }
    
    public void deleteJournal(int id) throws Exception {
 
           URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);

        int code = conn.getResponseCode();
        if(code != 200){
            // Ambil pesan error dari server jika ada
            InputStream errorStream = conn.getErrorStream();
            if (errorStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
                StringBuilder response = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    response.append(line);
                }
                System.out.println("Error dari server: " + response.toString()); // Cek output di console NetBeans
            }
            // Tampilkan kode statusnya di pesan error
            throw new RuntimeException("Gagal menghapus jurnal. HTTP Status: " + code);
        }
    }
    
    public boolean updateJournal(int id, String judul, String isi, String fotoPath) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Authorization", "Bearer " + Session.TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JsonObject body = new JsonObject();
        body.addProperty("judul", judul);
        body.addProperty("isi", isi);
        body.addProperty("foto", fotoPath); // Menambahkan parameter string foto/gambar

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes("UTF-8"));
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200 || responseCode == 204) {
            return true; // Mengembalikan true jika update sukses
        } else {
            throw new RuntimeException("Gagal update jurnal, server merespon dengan kode: " + responseCode);
        }
    }
    
}