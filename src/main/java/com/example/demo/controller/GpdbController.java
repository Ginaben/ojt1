package com.example.demo.controller;

import com.example.demo.service.GpdbService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpdbController {

    private final GpdbService gpdbService;

    @PostMapping("/gp")
    public ResponseEntity<String> gpPractice(HttpServletRequest request) throws IOException {

        //response 헤더
        //header는 컨트롤러에서 써도 되는지..?
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("Content-Type", request.getContentType());
        responseHeader.set("X-Amz-Date", String.valueOf(System.currentTimeMillis()));
        responseHeader.set("x-amzn-RequestId", request.getHeader("amz-sdk-invocation-id"));

        String responseBody = gpdbService.selectCatalogPage(request);
        log.info("responseBody={}", responseBody);

        return ResponseEntity.ok()
                .headers(responseHeader)
                .body(responseBody);
    }
}
