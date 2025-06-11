package com.example.barointern.domain.User.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {
    @GetMapping("/docs")
    public String docs() {
        return "redirect:/swagger-ui/index.html";
    }
}
