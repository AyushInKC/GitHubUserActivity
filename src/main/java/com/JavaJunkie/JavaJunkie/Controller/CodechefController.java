package com.JavaJunkie.JavaJunkie.Controller;

import com.JavaJunkie.JavaJunkie.Service.CodechefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodechefController {

    @Autowired
    private CodechefService codeChefService;

    @GetMapping("/codechef/profile")
    public String fetchProfile(@RequestParam String handle) {
        return codeChefService.fetchCodeChefProfile(handle);
    }
}
