package com.Animesh.Kryptrade.Model;

import com.Animesh.Kryptrade.Domain.WithDrawalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private WithDrawalStatus status;

    private long amount;

    @ManyToOne

    private User user;

    private LocalDateTime dateTime = LocalDateTime.now();

}
