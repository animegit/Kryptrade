package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import com.Animesh.Kryptrade.Model.ForgotPasswordToken;
import com.Animesh.Kryptrade.Model.User;

public interface ForgotPassword {

    ForgotPasswordToken createToken(User user, String id, String otp, Verification_Type verificationType,String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUserId(Long userId);
    void deleteToken(ForgotPasswordToken token);
}
