package com.example.mysql_concurrency.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final Service service;
    @GetMapping("/jpa/{id}")
    public String test(@PathVariable Long id) {
        service.modify(id);
        return "ok";
    }
}
