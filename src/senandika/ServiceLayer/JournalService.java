package senandika.ServiceLayer;

import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import senandika.Model.JournalData;

public class JournalService {

    private final String BASE_URL =
            "http://localhost:5000/api/journals";

    public String createJournal(
            String judul,
            String isi,
            File imageFile
    ) throws IOException {
      String boundary =
        Long.toHexString(
                System.currentTimeMillis()
        );

        URL url =
                new URL(BASE_URL);

        HttpURLConnection conn =
                (HttpURLConnection)
                        url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty(
                "Authorization",
                "Bearer " + Session.TOKEN
        );

        conn.setRequestProperty(
                "Content-Type",
                "multipart/form-data; boundary="
                        + boundary
        );

        conn.setDoOutput(true);
        
        //Output Stream
        OutputStream output =
        conn.getOutputStream();

        PrintWriter writer =
                new PrintWriter(
                        new OutputStreamWriter(
                                output,
                                "UTF-8"
                        ),
                        true
        );
        
        //Field Judul
        writer.append("--")
            .append(boundary)
            .append("\r\n");

        writer.append(
                "Content-Disposition: form-data; name=\"judul\""
        );

        writer.append("\r\n\r\n");

        writer.append(judul);

        writer.append("\r\n");

        writer.flush();
        
        //Field Isi
        writer.append("--")
            .append(boundary)
            .append("\r\n");

        writer.append(
                "Content-Disposition: form-data; name=\"isi\""
        );

        writer.append("\r\n\r\n");

        writer.append(isi);

        writer.append("\r\n");

        writer.flush();
        
        // Upload File
        if (imageFile != null) {

            writer.append("--")
                  .append(boundary)
                  .append("\r\n");

            writer.append(
                "Content-Disposition: form-data; name=\"image\"; filename=\""
                + imageFile.getName()
                + "\""
            );

            writer.append("\r\n");

            writer.append(
                    "Content-Type: image/jpeg"
            );
            
           

            writer.append("\r\n\r\n");

            writer.flush();

            FileInputStream input =
                    new FileInputStream(
                            imageFile
                    );

            byte[] buffer =
                    new byte[4096];

            int bytesRead;

            while (
                    (bytesRead =
                            input.read(buffer))
                            != -1
            ) {

                output.write(
                        buffer,
                        0,
                        bytesRead
                );
            }

            output.flush();

            input.close();

            writer.append("\r\n");

            writer.flush();
        }
        
            writer.append("--")
                .append(boundary)
                .append("--")
                .append("\r\n");
            writer.close();
        
        // Ambil Response
        BufferedReader br =
        new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()
                )
        );

        StringBuilder response =
                new StringBuilder();

        String line;

        while ((line = br.readLine())
                != null) {

            response.append(line);
        }

        return response.toString();
    }

    
    
    
    public List<JournalData> getJournals()
            throws Exception {

        URL url = new URL(BASE_URL);

        HttpURLConnection conn =
                (HttpURLConnection)
                        url.openConnection();

        conn.setRequestMethod("GET");

        conn.setRequestProperty(
                "Authorization",
                "Bearer " + Session.TOKEN
        );

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()
                        )
                );

        StringBuilder response =
                new StringBuilder();

        String line;

        while ((line = br.readLine())
                != null) {

            response.append(line);
        }

        JsonObject json =
                JsonParser.parseString(
                        response.toString()
                ).getAsJsonObject();

        JsonArray data =
                json.getAsJsonArray(
                        "data"
                );

        List<JournalData> journals =
                new ArrayList<>();

        for (JsonElement element : data) {

            JsonObject obj =
                    element.getAsJsonObject();

            JournalData journal =
                    new JournalData();

            journal.setId(
                    obj.get("id").getAsInt()
            );

            journal.setJudul(
                    obj.get("judul")
                            .getAsString()
            );

            journal.setIsi(
                    obj.get("isi")
                            .getAsString()
            );

            journal.setTanggal(
                    obj.get("tanggal")
                            .getAsString()
            );

            journal.setStreak(
                    obj.get("streak")
                            .getAsInt()
            );

            journals.add(journal);
        }

        return journals;
    }

    public int getStreak()
            throws Exception {

        URL url =
                new URL(
                        BASE_URL + "/streak"
                );

        HttpURLConnection conn =
                (HttpURLConnection)
                        url.openConnection();

        conn.setRequestMethod("GET");

        conn.setRequestProperty(
                "Authorization",
                "Bearer " + Session.TOKEN
        );

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()
                        )
                );

        StringBuilder response =
                new StringBuilder();

        String line;

        while ((line = br.readLine())
                != null) {

            response.append(line);
        }

        JsonObject json =
                JsonParser.parseString(
                        response.toString()
                ).getAsJsonObject();

        return json.get("streak")
                .getAsInt();
    }
}