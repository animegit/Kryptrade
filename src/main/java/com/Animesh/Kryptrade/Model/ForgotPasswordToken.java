package com.Animesh.Kryptrade.Model;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private User user;
    private String otp;
    private Verification_Type verificationType;
    private String sendTo;
}
