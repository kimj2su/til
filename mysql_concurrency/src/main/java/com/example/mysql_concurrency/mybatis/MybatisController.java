package com.example.mysql_concurrency.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MybatisController {

    private final MybatisService service;
    @GetMapping("/my/{id}")
    public String test(@PathVariable Long id) {
        service.create(id);
        return "ok";
    }
}
