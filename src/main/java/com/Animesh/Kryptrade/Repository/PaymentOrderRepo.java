package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepo  extends JpaRepository<PaymentOrder,Long> {

}
