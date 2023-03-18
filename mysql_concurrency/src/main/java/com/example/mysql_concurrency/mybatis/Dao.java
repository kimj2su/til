package com.example.mysql_concurrency.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
public interface Dao {

    Map<String, Object> findById(Long Id);

    void create(Map<String, Object> map);
}
