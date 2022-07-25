package com.example.demo.service;


import com.example.demo.mapper.GpdbMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GpdbService {

    private final GpdbMapper gpdbMapper;

    public String selectCatalogPage(HttpServletRequest request) throws IOException {

        // request 헤더
        //얘도 왠지 필요없을것 같음..
        Enumeration header = request.getHeaderNames();
        while (header.hasMoreElements()) {
            String headerName = (String) header.nextElement();
            String headerValue = request.getHeader(headerName);
        }

        //request 바디
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        //Json to Object
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonToObj = objectMapper.readTree(messageBody);
        JsonNode jsonToObj2 = objectMapper.readTree(messageBody).get("Key");
        String tableName = jsonToObj.get("TableName").asText();
        log.info("tableName={}", tableName);

        //jsonNode -> map
        Map<String, JsonNode> map = objectMapper.convertValue(jsonToObj2,
                new TypeReference<Map<String, JsonNode>>() {
                });

        //get values
        for (String key : map.keySet()) {
            JsonNode jsonNode = map.get(key);
            JsonNode node = jsonNode.get("S");
            map.put(key,node);
        }
        log.info("map={}", map);

        //mapper
        Map<String, String> stringMap = new HashMap<>();
        log.info("stringMap={}", stringMap);

        //result
        Map<String, Object> map1 = new HashMap<>();
        map1.put("item", map);
        log.info("map1={}", map1);

        //String id = "15";

        //response 바디
        String responseBody = gpdbMapper.selectCatalogPage(tableName, map);
        log.info("responseBody={}", responseBody);

        String json = null;
        try {
            json = objectMapper.writeValueAsString(map1);
            log.info("json={}", json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

}
