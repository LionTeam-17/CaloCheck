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

    private final ObjectStorageConfigProperties objectStorageConfigProperties;
    private final GCPConfigProperties gcpConfigProperties;

    @GetMapping("/")
    public String showMain() {

        System.out.println("credentials = " + objectStorageConfigProperties.getCredentials());

        System.out.println(objectStorageConfigProperties.getCdn().get("url"));

        System.out.println(objectStorageConfigProperties.getStack().get("auto"));

        System.out.println(objectStorageConfigProperties.getS3().get("url"));

        System.out.println(objectStorageConfigProperties.getRegion().get("static"));

        System.out.println("gcpConfigProperties = " + gcpConfigProperties.getFilePath());

        return "usr/home/main";
    }
}