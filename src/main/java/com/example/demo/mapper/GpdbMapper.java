package com.example.demo.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface GpdbMapper {
    String selectCatalogPage(@Param("tableName") String tableName, Map<String, JsonNode> map);
}
