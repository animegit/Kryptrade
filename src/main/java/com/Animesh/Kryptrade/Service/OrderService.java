package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.OrderType;
import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Model.Order;
import com.Animesh.Kryptrade.Model.OrderItem;
import com.Animesh.Kryptrade.Model.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getallorderofuser(Long userId, OrderType orderType, String assetSymbol);
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
