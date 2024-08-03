package com.Animesh.Kryptrade.utils;

import java.util.Random;

public class OtpUtils {

    public static String generateotp(){
        int len=6;
        Random random=new Random();
        StringBuilder otp=new StringBuilder();
        for(int i=0;i<len;i++){
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
