package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendverify(User user, Verification_Type verificationType);
    VerificationCode getverifyById(Long id) throws Exception;
    VerificationCode getverifyByUser(Long userId);

    void deleteVerificationById(VerificationCode verificationCode);
}
