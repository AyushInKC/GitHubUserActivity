package com.JavaJunkie.JavaJunkie.Controller;

import com.JavaJunkie.JavaJunkie.Service.CodeforcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeforcesController {
    @Autowired
    private CodeforcesService codeforcesService;

    @GetMapping("/codeforces/profile")
    public String fetchCodeforcesProfile(@RequestParam String handle) {
        return codeforcesService.fetchCodeforcesProfile(handle);
    }
}
