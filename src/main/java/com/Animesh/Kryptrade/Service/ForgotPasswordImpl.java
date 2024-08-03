package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import com.Animesh.Kryptrade.Model.ForgotPasswordToken;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Repository.ForgotPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ForgotPasswordImpl implements ForgotPassword{
    @Autowired
    public ForgotPasswordRepo forgotPasswordRepo;

    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, Verification_Type verificationType, String sendTo) {
        ForgotPasswordToken token=new ForgotPasswordToken();
        token.setUser(user);
        token.setId(id);
        token.setOtp(otp);
        token.setVerificationType(verificationType);
        token.setSendTo(sendTo);
        return  forgotPasswordRepo.save(token);


    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> opt=forgotPasswordRepo.findById(id);
        return opt.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUserId(Long userId) {
        return forgotPasswordRepo.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
    forgotPasswordRepo.delete(token);
    }
}
