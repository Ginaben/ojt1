package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JavaCharStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Slf4j
@RestController
public class ApiController {

    @PostMapping({"/",""})
    public ResponseEntity<String> posting (HttpServletRequest request) throws IOException {

        // request 헤더
        Enumeration header = request.getHeaderNames();
        while (header.hasMoreElements()) {
            String headerName = (String) header.nextElement();
            String headerValue = request.getHeader(headerName);
        }

        //request 바디
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        //reponse 헤더
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("Content-Type", request.getContentType());
        responseHeader.set("X-Amz-Date", String.valueOf(System.currentTimeMillis()));
        responseHeader.set("x-amzn-RequestId", request.getHeader("amz-sdk-invocation-id"));

        //response 바디
        String responseBody = "{\n" +
                "\"Item\": {\n" +
                "\"cc_call_center_sk\": { \"S\": \"4\"\n" +
                "}, \"cc_rec_start_date\": {\n" +
                "\"S\": \"1998-01-01\" }\n" +
                "}, \"ConsumedCapacity\": {\n" +
                "\"CapacityUnits\": 0.5,\n" +
                "\"TableName\": \"dvcp.tpcds.call_center\" }\n" +
                "}";

//        return new ResponseEntity<>(responseHeader, HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(responseHeader)
                .body(responseBody);
    }

}
