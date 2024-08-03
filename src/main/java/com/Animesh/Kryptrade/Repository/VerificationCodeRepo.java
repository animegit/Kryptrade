package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepo extends JpaRepository<VerificationCode,Long> {
    public VerificationCode findByUserId(Long userId);

}
