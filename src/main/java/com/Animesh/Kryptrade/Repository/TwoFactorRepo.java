package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.TwoFactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TwoFactorRepo extends JpaRepository<TwoFactorOtp, String> {
    TwoFactorOtp findByUserId(Long userId);
}
