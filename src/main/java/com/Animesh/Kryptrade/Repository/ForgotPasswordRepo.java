package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordToken, String> {

    // Corrected method name and query
    @Query("SELECT fpt FROM ForgotPasswordToken fpt WHERE fpt.user.id = :userId")
    ForgotPasswordToken findByUserId(@Param("userId") Long userId);
}
