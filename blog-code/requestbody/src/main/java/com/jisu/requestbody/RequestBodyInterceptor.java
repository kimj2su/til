package com.jisu.requestbody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class RequestBodyInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    public RequestBodyInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request instanceof CacheBodyHttpServletRequestWrapper) {
            CacheBodyHttpServletRequestWrapper wrappedRequest = (CacheBodyHttpServletRequestWrapper) request;

            // 요청 본문 읽기
            // ServletInputStream inputStream = wrappedRequest.getInputStream();
            // String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            String body = wrappedRequest.getRequestBody();
            System.out.println("Original body = " + body);

            // JSON 파싱
            RequestBodyDto requestBodyDto = objectMapper.readValue(body, RequestBodyDto.class);

            // Unix Timestamp를 ZonedDateTime으로 변환
            if (requestBodyDto.getFiled() instanceof Number) {
                long timestamp = ((Number) requestBodyDto.getFiled()).longValue();
                ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp * 1000), ZoneId.of("UTC"));
                requestBodyDto.setFiled(zonedDateTime);
            }

            // 변환된 ZonedDateTime을 JSON 형식으로 직렬화
            String formattedBody = objectMapper.writeValueAsString(requestBodyDto);
            System.out.println("Formatted Request Body: " + formattedBody);

            // 변환된 본문으로 CacheBodyHttpServletRequestWrapper 설정
            wrappedRequest.setRequestBody(formattedBody);
        } else {
            System.out.println("Request is not an instance of CacheBodyHttpServletRequestWrapper");
        }
        return true;
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            byte[] requestBodyBytes = wrapper.getContentAsByteArray();
            String requestBody = new String(requestBodyBytes, StandardCharsets.UTF_8);
            System.out.println("afterCompletion: " + requestBody);
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = request.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}