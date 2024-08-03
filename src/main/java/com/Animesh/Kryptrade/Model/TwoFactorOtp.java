package com.Animesh.Kryptrade.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class TwoFactorOtp {
    @Id
    private String Id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String otp;
    @OneToOne
    private User user;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jwt;

}
