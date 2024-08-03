package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.VerificationCode;
import com.Animesh.Kryptrade.Repository.VerificationCodeRepo;
import com.Animesh.Kryptrade.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{
@Autowired
private VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendverify(User user, Verification_Type verificationType) {
        VerificationCode verificationCode1=new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateotp());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);
        return verificationCodeRepo.save(verificationCode1);

    }

    @Override
    public VerificationCode getverifyById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode=verificationCodeRepo.findById(id);
        if(verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("verification code not found");


    }

    @Override
    public VerificationCode getverifyByUser(Long userId) {
        return verificationCodeRepo.findByUserId(userId);
    }

    @Override
    public void deleteVerificationById(VerificationCode verificationCode) {
    verificationCodeRepo.delete(verificationCode);
    }
}
