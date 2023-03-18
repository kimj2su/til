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
    private final SqlSession sqlSession;
    @Transactional
    public void create() {
        Map<String, Object> byId = dao.findById(1L);
        if (byId == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", 1);
            params.put("username", "jisu");
            params.put("lastAt", "Y");
            dao.create(params);
        }
    }
}
