package com.JavaJunkie.JavaJunkie.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GitHubService {

    public String fetchGitHubActivity(String username) {
        String apiUrl = "https://api.github.com/users/" + username + "/events";
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
                JsonNode events = objectMapper.readTree(jsonResponse.toString());

                for (JsonNode event : events) {
                    String type = event.get("type").asText();
                    String repoName = event.get("repo").get("name").asText();

                    switch (type) {
                        case "PushEvent":
                            int commitCount = event.get("payload").get("size").asInt();
                            String branch = event.get("payload").get("ref").asText().replace("refs/heads/", "");
                            result.append("- Pushed ").append(commitCount).append(" commit(s) to ").append(repoName)
                                    .append(" on branch ").append(branch).append("\n");

                            for (JsonNode commit : event.get("payload").get("commits")) {
                                String commitMessage = commit.get("message").asText();
                                String commitSha = commit.get("sha").asText().substring(0, 7); // Short SHA
                                result.append("  ➝ Commit ").append(commitSha).append(": ").append(commitMessage).append("\n");
                            }
                            break;

                        case "CreateEvent":
                            String refType = event.get("payload").get("ref_type").asText();
                            String refName = event.get("payload").has("ref") ? event.get("payload").get("ref").asText() : "N/A";
                            String repositoryName = event.has("repo") && event.get("repo").has("name") ? event.get("repo").get("name").asText() : "Unknown Repository";

                            result.append("- Created a new ").append(refType).append(" (").append(refName).append(") in ")
                                    .append(repositoryName).append("\n");
                            break;

                        case "WatchEvent":
                            result.append("- Starred ").append(repoName).append("\n");
                            break;

                        case "ForkEvent":
                            result.append("- Forked ").append(repoName).append("\n");
                            break;

                        case "IssuesEvent":
                            String issueTitle = event.get("payload").get("issue").get("title").asText();
                            result.append("- Opened an issue in ").append(repoName)
                                    .append(" ➝ \"").append(issueTitle).append("\"\n");
                            break;

                        case "PullRequestEvent":
                            String prTitle = event.get("payload").get("pull_request").get("title").asText();
                            result.append("- Opened a pull request in ").append(repoName)
                                    .append(" ➝ \"").append(prTitle).append("\"\n");
                            break;

                        case "DeleteEvent":
                            String delRefType = event.get("payload").get("ref_type").asText();
                            String delRefName = event.get("payload").get("ref").asText();
                            result.append("- Deleted ").append(delRefType).append(" (").append(delRefName).append(") from ")
                                    .append(repoName).append("\n");
                            break;

                        default:
                            result.append("- Performed ").append(type).append(" in ").append(repoName).append("\n");
                    }
                }
            } else {
                return "Failed to fetch data. HTTP Error Code: " + connection.getResponseCode();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

        return result.toString().isEmpty() ? "No recent activity found." : result.toString();
    }
}
