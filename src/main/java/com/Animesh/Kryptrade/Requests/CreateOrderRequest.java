package com.Animesh.Kryptrade.Requests;

import com.Animesh.Kryptrade.Domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coindId;
    private double quantity;
    private OrderType orderType;
}
