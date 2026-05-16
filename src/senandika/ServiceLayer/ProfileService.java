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
    
    public String getProfile() {

        try {

            URL url = new URL(BASE_URL);

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setRequestMethod("GET");

            conn.setRequestProperty(
                    "Authorization",
                    "Bearer " + Session.TOKEN
            );

            BufferedReader br;

            if (conn.getResponseCode() >= 200
                    && conn.getResponseCode() < 300) {

                br = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()
                        )
                );

            } else {

                br = new BufferedReader(
                        new InputStreamReader(
                                conn.getErrorStream()
                        )
                );
            }

            StringBuilder response =
                    new StringBuilder();

            String line;

            while ((line = br.readLine()) != null) {

                response.append(line);
            }

            return response.toString();

        } catch (Exception e) {

            return e.getMessage();
        }
    }
    
    public String updateProfile(
        String username,
        String fullName,
        String gender,
        int stressLevel,
        String favoriteActivity
    ) {

    try {

            URL url = new URL(BASE_URL);
            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setRequestMethod("PUT");

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
                    "{" +
                    "\"username\":\"%s\"," +
                    "\"fullName\":\"%s\"," +
                    "\"gender\":\"%s\"," +
                    "\"stressLevel\":%d," +
                    "\"favoriteActivity\":\"%s\"" +
                    "}",
                    username,
                    fullName,
                    gender,
                    stressLevel,
                    favoriteActivity
            );

            try (OutputStream os =
                         conn.getOutputStream()) {

                byte[] input =
                        jsonInput.getBytes("utf-8");

                os.write(input, 0, input.length);
            }

            BufferedReader br;

            if (conn.getResponseCode() >= 200
                    && conn.getResponseCode() < 300) {

                br = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()
                        )
                );

            } else {

                br = new BufferedReader(
                        new InputStreamReader(
                                conn.getErrorStream()
                        )
                );
            }

            StringBuilder response =
                    new StringBuilder();

            String line;

            while ((line = br.readLine()) != null) {

                response.append(line);
            }

            return response.toString();

        } catch (Exception e) {

            return e.getMessage();
        }
    }
    
    public String uploadProfilePhoto(
        File imageFile
    ) {

        try {

            String boundary =
                    Long.toHexString(
                            System.currentTimeMillis()
                    );

            URL url = new URL(
                    BASE_URL + "/upload-photo"
            );

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setDoOutput(true);

            conn.setRequestMethod("PUT");

            conn.setRequestProperty(
                    "Authorization",
                    "Bearer " + Session.TOKEN
            );

            conn.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary="
                    + boundary
            );

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

            // START FILE
            writer.append("--" + boundary)
                    .append("\r\n");

            writer.append(
                    "Content-Disposition: form-data; name=\"photo\"; filename=\""
                    + imageFile.getName()
                    + "\""
            ).append("\r\n");

            writer.append(
                    "Content-Type: image/jpeg"
            ).append("\r\n");

            writer.append("\r\n").flush();

            FileInputStream inputStream =
                    new FileInputStream(imageFile);

            byte[] buffer =
                    new byte[4096];

            int bytesRead;

            while ((bytesRead =
                    inputStream.read(buffer))
                    != -1) {

                output.write(
                        buffer,
                        0,
                        bytesRead
                );
            }

            output.flush();

            inputStream.close();

            writer.append("\r\n")
                    .flush();

            writer.append("--" + boundary + "--")
                    .append("\r\n");

            writer.close();

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

        } catch (Exception e) {

            return e.getMessage();
        }
    }
}  