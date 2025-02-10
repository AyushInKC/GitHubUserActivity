package com.JavaJunkie.JavaJunkie.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class CodeforcesService {
    public String fetchCodeforcesProfile(String handle) {
        String apiUrl = "https://codeforces.com/api/user.info?handles=" + handle;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonResponse = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonResponse.append(line);
                }
                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse.toString());

                if (rootNode.get("status").asText().equals("OK")) {
                    JsonNode userInfo = rootNode.get("result").get(0);

                    String username = userInfo.get("handle").asText();
                    int rating = userInfo.has("rating") ? userInfo.get("rating").asInt() : 0;
                    int maxRating = userInfo.has("maxRating") ? userInfo.get("maxRating").asInt() : 0;
                    String rank = userInfo.has("rank") ? userInfo.get("rank").asText() : "Unrated";
                    String maxRank = userInfo.has("maxRank") ? userInfo.get("maxRank").asText() : "Unrated";

                    result.append("Codeforces Profile Details:\n")
                            .append("- Handle: ").append(username).append("\n")
                            .append("- Current Rating: ").append(rating).append("\n")
                            .append("- Max Rating: ").append(maxRating).append("\n")
                            .append("- Rank: ").append(rank).append("\n")
                            .append("- Max Rank: ").append(maxRank).append("\n");
                } else {
                    return "Invalid handle or user not found.";
                }

            } else {
                return "Failed to fetch data. HTTP Error Code: " + connection.getResponseCode();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

        return result.toString().isEmpty() ? "No data found." : result.toString();
    }
}
