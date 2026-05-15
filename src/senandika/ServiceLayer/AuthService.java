package senandika.ServiceLayer;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService {

    private final String BASE_URL = "http://localhost:5000/api/auth";
    
    // Method Login
    public String login(String email, String password) {

        try {

            URL url = new URL(BASE_URL + "/login");

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            conn.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\"}",
                    email,
                    password
            );

            try (OutputStream os = conn.getOutputStream()) {

                byte[] input = jsonInput.getBytes("utf-8");

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

            StringBuilder response = new StringBuilder();

            String responseLine;

            while ((responseLine = br.readLine()) != null) {

                response.append(responseLine.trim());
            }

            return response.toString();

        } catch (Exception e) {

            return e.getMessage();
        }
    }
    
    // Method Register
    public String register(String email, String password) {

    try {

        URL url = new URL(BASE_URL + "/register");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        conn.setDoOutput(true);

        String jsonInput = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                email,
                password
        );

        try (OutputStream os = conn.getOutputStream()) {

            byte[] input = jsonInput.getBytes("utf-8");

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

        StringBuilder response = new StringBuilder();

        String responseLine;

        while ((responseLine = br.readLine()) != null) {

            response.append(responseLine.trim());
        }

        return response.toString();

    } catch (Exception e) {

        return e.getMessage();
    }
    }
    
    // Method Forgot Password
    public String forgotPassword(String email) {

    try {

        URL url = new URL(BASE_URL + "/forgot-password");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        conn.setDoOutput(true);

        String jsonInput = String.format(
                "{\"email\":\"%s\"}",
                email
        );

        try (OutputStream os = conn.getOutputStream()) {

            byte[] input = jsonInput.getBytes("utf-8");

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

        StringBuilder response = new StringBuilder();

        String responseLine;

        while ((responseLine = br.readLine()) != null) {

            response.append(responseLine.trim());
        }

        return response.toString();

    } catch (Exception e) {

        return e.getMessage();
    }
}
    
    // Methode reset password
    public String resetPassword(
        String email,
        String newPassword) {

    try {

        URL url = new URL(
                BASE_URL + "/reset-password"
        );

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");

        conn.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        conn.setDoOutput(true);

        String jsonInput = String.format(
                "{\"email\":\"%s\",\"newPassword\":\"%s\"}",
                email,
                newPassword
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

        while ((responseLine = br.readLine())
                != null) {

            response.append(responseLine.trim());
        }

        return response.toString();

    } catch (Exception e) {

        return e.getMessage();
    }
}
}