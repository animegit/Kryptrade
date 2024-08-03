package com.Animesh.Kryptrade.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double quantity;

    @ManyToOne

    private Coin coin;

    private double buyPrice; // Renamed to follow camelCase convention

    private double sellPrice; // Renamed to follow camelCase convention

    @JsonIgnore
    @OneToOne
    private Order order;
}
