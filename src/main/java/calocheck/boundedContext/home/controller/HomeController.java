package calocheck.boundedContext.home.controller;

import calocheck.boundedContext.photo.config.S3ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final S3ConfigProperties s3;

    @GetMapping("/")
    public String showMain() {

        System.out.println("s3.getCdnUrl() = " + s3.getCdnUrl());

        return "usr/home/main";
    }
}