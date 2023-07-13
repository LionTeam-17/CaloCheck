package calocheck.boundedContext.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String showMain() throws IOException {

        return "usr/home/main2";
    }

    @GetMapping("/main2")
    public String showMain2() {

        return "usr/home/main2";
    }
}