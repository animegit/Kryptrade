package com.Animesh.Kryptrade.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double quantity;

    private double buyPrice;

    @ManyToOne

    private Coin coin;

    @ManyToOne

    private User user;
}
