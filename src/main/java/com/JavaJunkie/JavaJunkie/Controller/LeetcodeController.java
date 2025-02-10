package com.JavaJunkie.JavaJunkie.Controller;
import com.JavaJunkie.JavaJunkie.Service.LeetcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeetcodeController {

    @Autowired
    private LeetcodeService leetcodeService;
    @GetMapping("/leetcode/stats")
    public String fetchLeetCodeStats(@RequestParam String username) {
        return leetcodeService.fetchLeetCodeStats(username);
    }

}
