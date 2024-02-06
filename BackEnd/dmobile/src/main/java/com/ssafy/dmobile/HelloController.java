package com.ssafy.dmobile;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello2")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }
}

