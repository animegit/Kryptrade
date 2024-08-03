package com.Animesh.Kryptrade.Model;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Embeddable
@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;

    @Enumerated(EnumType.STRING)
    private Verification_Type sentTo;
}
