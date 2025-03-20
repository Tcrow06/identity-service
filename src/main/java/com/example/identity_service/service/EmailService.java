package com.example.minio_storage.service;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body);
    boolean validateOtp(String email, String otp);
    void saveOtp(String email, String otp);
    String getRedisKey(String email);
}
