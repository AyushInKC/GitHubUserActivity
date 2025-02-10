package com.JavaJunkie.JavaJunkie.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class CodechefService {

    public String fetchCodeChefProfile(String handle) {
        String profileUrl = "https://www.codechef.com/users/" + handle;
        StringBuilder result = new StringBuilder();

        try {
            Document doc = Jsoup.connect(profileUrl).get();

            Element nameElement = doc.selectFirst(".user-details-container h1");
            String name = (nameElement != null) ? nameElement.text() : "N/A";

            Element ratingElement = doc.selectFirst(".rating-number");
            String rating = (ratingElement != null) ? ratingElement.text() : "N/A";

            Element globalRankElement = doc.selectFirst(".rating-ranks .inline-list li:first-child strong");
            String globalRank = (globalRankElement != null) ? globalRankElement.text() : "N/A";

            Element countryRankElement = doc.selectFirst(".rating-ranks .inline-list li:last-child strong");
            String countryRank = (countryRankElement != null) ? countryRankElement.text() : "N/A";

            result.append("👤 **Name:** ").append(name).append("\n")
                    .append("⭐ **Rating:** ").append(rating).append("\n")
                    .append("🌎 **Global Rank:** ").append(globalRank).append("\n")
                    .append("🏆 **Country Rank:** ").append(countryRank).append("\n");

        } catch (Exception e) {
            return "Error: Unable to fetch CodeChef data for handle: " + handle;
        }

        return result.toString();
    }
}
