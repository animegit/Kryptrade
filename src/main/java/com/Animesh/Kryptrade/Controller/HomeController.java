package com.Animesh.Kryptrade.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String Home(){
        return "Welcome to Home";
    }
}