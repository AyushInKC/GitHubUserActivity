package com.JavaJunkie.JavaJunkie.Controller;

import com.JavaJunkie.JavaJunkie.Service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/github/activity")
    public String fetchActivity(@RequestParam String username) {
        return gitHubService.fetchGitHubActivity(username);
    }
}
