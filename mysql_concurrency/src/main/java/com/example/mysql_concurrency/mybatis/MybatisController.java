package com.example.mysql_concurrency.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final Service service;
    @GetMapping("/my")
    public String test() {
        service.create();
        return "ok";
    }
}
