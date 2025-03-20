package com.example.minio_storage.common.utils;

import java.util.Random;

public class OtpGenerator {
    private static final Random RAMRANDOM = new Random();
    public static String generateOtp(){
        return String.format("%06d", RAMRANDOM.nextInt(999999));
    }
}
