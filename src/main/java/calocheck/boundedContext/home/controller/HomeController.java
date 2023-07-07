package calocheck.boundedContext.home.controller;


import calocheck.boundedContext.photo.config.GCPConfigProperties;
import calocheck.boundedContext.photo.config.S3ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GCPConfigProperties gcpConfigProperties;

    @GetMapping("/")
    public String showMain() {

        String filePath = gcpConfigProperties.getFilePath();

        System.out.println(filePath);

        return "usr/home/main";
    }
}