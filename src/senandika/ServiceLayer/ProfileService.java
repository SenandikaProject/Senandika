package senandika.ServiceLayer;

import java.io.OutputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import senandika.ServiceLayer.Session;

public class ProfileService {

    private final String BASE_URL =
            "http://localhost:5000/api/profile";

    public String setupProfile(
            String username,
            String fullName,
            String gender,
            int stressLevel,
            String favoriteActivity
    ) throws IOException {

        URL url = new URL(BASE_URL + "/setup");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        conn.setRequestProperty(
                "Authorization",
                "Bearer " + Session.TOKEN
        );

        conn.setDoOutput(true);

        String jsonInput = String.format(
                "{"
                        + "\"username\":\"%s\","
                        + "\"fullName\":\"%s\","
                        + "\"gender\":\"%s\","
                        + "\"stressLevel\":%d,"
                        + "\"favoriteActivity\":\"%s\""
                        + "}",
                username,
                fullName,
                gender,
                stressLevel,
                favoriteActivity
        );

        try (OutputStream os = conn.getOutputStream()) {

            byte[] input =
                    jsonInput.getBytes("utf-8");

            os.write(input, 0, input.length);
        }

        BufferedReader br;

        if (conn.getResponseCode() >= 200
                && conn.getResponseCode() < 300) {

            br = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream(),
                            "utf-8"
                    )
            );

        } else {

            br = new BufferedReader(
                    new InputStreamReader(
                            conn.getErrorStream(),
                            "utf-8"
                    )
            );
        }

        StringBuilder response =
                new StringBuilder();

        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        br.close();
        conn.disconnect();

        return response.toString();
    }
}