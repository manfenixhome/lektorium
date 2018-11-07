package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PingController {

    @GetMapping("/api/anonymous/ping")
    public Map<String, Object> anonymousPing() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Anonymous access granted");
        return response;
    }

    @GetMapping("/api/user/ping")
    public Map<String, Object> userPing() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User role access granted");
        return response;
    }

    @GetMapping("/api/admin/ping")
    public Map<String, Object> adminPing() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin role access granted");
        return response;
    }

}
