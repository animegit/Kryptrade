package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.TwoFactorOtp;
import com.Animesh.Kryptrade.Model.User;

public interface TwoFactorOtpSevice {
    TwoFactorOtp createTwoFactorOtp(User user,String otp,String jwt);
    TwoFactorOtp findByUser(Long userId);
    TwoFactorOtp findById(String id);
    boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp,String otp);
    void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp);
}
