package com.example.minio_storage.controller;

import com.example.minio_storage.dto.response.ApiResponse;
import com.example.minio_storage.dto.response.TestResponse;
import com.example.minio_storage.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/redis")
@RequiredArgsConstructor
public class RedisController {
    private final BaseRedisService redisService;

    @PostMapping
    void set(){
        redisService.set("hihi", "haha");
    }
    @GetMapping("/{key}")

    ResponseEntity<TestResponse> getAll(@PathVariable("key") String key){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TestResponse.builder()
                        .value(redisService.get(key).toString())
                        .build());
    }
}
