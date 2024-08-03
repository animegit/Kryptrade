package com.Animesh.Kryptrade.Model;

import com.Animesh.Kryptrade.Domain.PaymentStatus;
import com.Animesh.Kryptrade.Domain.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentStatus paymentStatus;

    private PaymentType paymentType;

    @ManyToOne
    private User user;


}
