package com.Animesh.Kryptrade.Requests;

import lombok.Data;

@Data
public class ResetPasswordReq {
    private String otp;
    private String password;
}
