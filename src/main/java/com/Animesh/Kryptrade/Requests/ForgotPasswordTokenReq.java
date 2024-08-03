package com.Animesh.Kryptrade.Requests;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import lombok.Data;

@Data
public class ForgotPasswordTokenReq {
    private String sendTo;
    private Verification_Type verificationType;
}
