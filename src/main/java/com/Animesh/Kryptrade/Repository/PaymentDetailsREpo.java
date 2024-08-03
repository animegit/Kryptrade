package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsREpo extends JpaRepository<PaymentDetails,Long> {
    PaymentDetails findByUserId(Long userId);
}
