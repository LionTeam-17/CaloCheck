package calocheck.boundedContext.home.controller;


import calocheck.boundedContext.photo.config.GCPConfigProperties;
import calocheck.boundedContext.photo.config.ObjectStorageConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String showMain() {

        return "usr/home/main";
    }
}