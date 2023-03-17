package com.example.mysql_concurrency.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class Service {

    private final Dao dao;
    @Transactional
    public void create() {
        Map<String, Object> byId = dao.findById(1L);
        if (byId.isEmpty()) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", 1);
            params.put("username", "jisu");
            dao.create(params);
        }
    }
}
