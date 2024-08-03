package com.Animesh.Kryptrade.Model;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
@Entity
@Data

public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otp;
    @OneToOne
    private User user;
    private String email;
    private String mobile;
    private Verification_Type verificationType;


}
