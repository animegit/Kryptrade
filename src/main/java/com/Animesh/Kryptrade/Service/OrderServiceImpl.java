package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.OrderStatus;
import com.Animesh.Kryptrade.Domain.OrderType;
import com.Animesh.Kryptrade.Model.*;
import com.Animesh.Kryptrade.Repository.OrderItemRepo;
import com.Animesh.Kryptrade.Repository.OrderRepo;
import com.Animesh.Kryptrade.Repository.UserRepo;
import com.Animesh.Kryptrade.Repository.CoinREpo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private UserRepo userRepository;
@Autowired
private WalletService walletService;
    @Autowired
    private CoinREpo coinRepository;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private AssetService assetService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price=orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.Pending);

        // Save order to the database
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElseThrow(()->new Exception("Order Not Found"));
    }



    @Override
    public List<Order> getallorderofuser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);}

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType.equals(OrderType.BUY)){
            return buyAsset(coin,quantity,user);
        }
        if(orderType.equals(OrderType.SELL)){
            return buyAsset(coin,quantity,user);
        }
        throw new Exception("Invalid Order Type");
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepo.save(orderItem);
    }
    @Transactional
    private Order buyAsset(Coin coin, double quantity, User user) throws Exception {

        if(quantity<=0){
            throw new Exception("quantity should be greater than 0");
        }
        double totalCost = coin.getCurrentPrice();





        // Create a new order
        OrderItem orderItem = createOrderItem(coin, quantity, totalCost, 0.0);
        Order order = createOrder(user,orderItem,OrderType.BUY);
       orderItem.setOrder(order);


        // Optionally, update user's wallet to persist changes
        walletService.payOrderPayment(order,user);
        order.setStatus(OrderStatus.Sucess);
        order.setOrderType(OrderType.BUY);
        Order savedOrder    =orderRepository.save(order);

        //create asset

        Asset oldAsset=assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),order.getOrderItem().getId());
        if(oldAsset==null){
            assetService.createAsset(user,orderItem.getCoin(),orderItem.getQuantity());
        }
        else{
            assetService.updateAsset(oldAsset.getId(),quantity);
        }
        return savedOrder;
    }

    @Transactional
    private Order sellAsset(Coin coin, double quantity, User user) throws Exception {

        if(quantity<=0){
            throw new Exception("quantity should be greater than 0");
        }

        Asset assetToSell=assetService.findAssetByUserIdAndCoinId(user.getId(), Long.valueOf(coin.getId()));
        double buyPrice = assetToSell.getBuyPrice();
        double sellPrice=coin.getCurrentPrice();
        if(assetToSell!=null){
            OrderItem orderItem=createOrderItem(coin,quantity,buyPrice,sellPrice);
        }



        // Create a new order
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
        Order order = createOrder(user,orderItem,OrderType.SELL);
        orderItem.setOrder(order);

        if(assetToSell.getQuantity()>=quantity){
            order.setStatus(OrderStatus.Sucess);
            order.setOrderType(OrderType.BUY);
            Order savedOrder    =orderRepository.save(order);
            walletService.payOrderPayment(order,user);

            Asset updatedAsset=assetService.updateAsset(assetToSell.getId(),-quantity);
            if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1){
                assetService.deleteAsset(updatedAsset.getId());
            }
            return savedOrder;
        }

    throw new Exception("Insufficient Funds");
    }



}
