package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.TwoFactorOtp;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Repository.TwoFactorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service

public class TwoFactorOtpSeriveImpl implements TwoFactorOtpSevice{

    @Autowired
    public TwoFactorRepo twoFactorRepo;
    @Override
    public TwoFactorOtp createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();
        TwoFactorOtp twoFactorOtp=new TwoFactorOtp();
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setJwt(jwt);
        twoFactorOtp.setId(id);
        twoFactorOtp.setUser(user);

        return twoFactorRepo.save(twoFactorOtp);
    }

    @Override
    public TwoFactorOtp findByUser(Long userId) {
        return twoFactorRepo.findByUserId(userId);
    }

    @Override
    public TwoFactorOtp findById(String id) {
        Optional<TwoFactorOtp> opt=twoFactorRepo.findById(id);

        return opt.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp, String otp) {
        return twoFactorOtp.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp) {
    twoFactorRepo.delete(twoFactorOtp);
    }
}
