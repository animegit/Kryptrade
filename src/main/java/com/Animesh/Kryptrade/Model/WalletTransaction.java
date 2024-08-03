package com.Animesh.Kryptrade.Model;

import com.Animesh.Kryptrade.Domain.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne

    private Wallet wallet;

    private TransactionType transactionType;



    private LocalDateTime timestamp;

    private String transferId;
    private String purpose;
    private Long amount;

    // Add additional fields as needed
}
