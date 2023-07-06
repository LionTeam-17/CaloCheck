package calocheck.boundedContext.home.controller;


import calocheck.boundedContext.photo.config.GCPConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GCPConfigProperties gcpConfigProperties;

    @GetMapping("/")
    public String showMain() {

        String example1 = gcpConfigProperties.getExample();

        String example2 = gcpConfigProperties.getExample2();

        System.out.println("example1 = " + example1);
        System.out.println("example2 = " + example2);

        return "usr/home/main";
    }
}