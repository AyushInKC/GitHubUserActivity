package com.JavaJunkie.JavaJunkie.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class LeetcodeService {

    public String fetchLeetCodeStats(String username) {
        String API_URL= "https://leetcode-stats-api.herokuapp.com/" + username;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(API_URL);
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
                JsonNode stats = objectMapper.readTree(jsonResponse.toString());

                if (stats.has("status") && stats.get("status").asText().equals("error")) {
                    return "Invalid username or data not found.";
                }

                int totalSolved = stats.get("totalSolved").asInt();
                int totalQuestions = stats.get("totalQuestions").asInt();
                int easySolved = stats.get("easySolved").asInt();
                int mediumSolved = stats.get("mediumSolved").asInt();
                int hardSolved = stats.get("hardSolved").asInt();
                int ranking = stats.get("ranking").asInt();
                double acceptanceRate = stats.get("acceptanceRate").asDouble();

                result.append("LeetCode Statistics for ").append(username).append(":\n")
                        .append("- Total Solved: ").append(totalSolved).append("/").append(totalQuestions).append("\n")
                        .append("- Easy: ").append(easySolved).append("\n")
                        .append("- Medium: ").append(mediumSolved).append("\n")
                        .append("- Hard: ").append(hardSolved).append("\n")
                        .append("- Acceptance Rate: ").append(String.format("%.2f", acceptanceRate)).append("%\n")
                        .append("- Global Ranking: #").append(ranking);

            } else {
                return "Failed to fetch data. HTTP Error Code: " + connection.getResponseCode();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

        return result.toString().isEmpty() ? "No data found." : result.toString();
    }
    }
