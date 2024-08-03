package com.Animesh.Kryptrade.Controller;


import com.Animesh.Kryptrade.Domain.OrderType;
import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Model.Order;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Requests.CreateOrderRequest;
import com.Animesh.Kryptrade.Service.CoinService;
import com.Animesh.Kryptrade.Service.OrderService;
import com.Animesh.Kryptrade.Service.UserService;
import com.Animesh.Kryptrade.Service.WalletService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;
    @Autowired
    private CoinService coinService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrder (
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest request) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(request.getCoindId());
        Order order=orderService.processOrder(coin, request.getQuantity(), request.getOrderType(),user);
        return ResponseEntity.ok(order);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersForUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) OrderType orderType,
            @RequestParam(required = false) String assetSymbol,
            @AuthenticationPrincipal User user) throws Exception {
        if(jwt==null){
            throw new Exception("token missing...");
        }
        Long userId=userService.findUserProfileByJwt(jwt).getId();
        if (!user.getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        List<Order> orders = orderService.getallorderofuser(userId, orderType, assetSymbol);
        return ResponseEntity.ok(orders);
    }
}
