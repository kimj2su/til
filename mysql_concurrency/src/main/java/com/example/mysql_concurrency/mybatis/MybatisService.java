package com.example.mysql_concurrency.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MybatisService {

    private final Dao dao;

    @Transactional
    public void create(Long id) {
        Map<String, Object> byId = dao.findById(id);
        if (byId == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("username", "jisu");
            params.put("lastAt", "Y");
            dao.create(params);
        }
    }
}
