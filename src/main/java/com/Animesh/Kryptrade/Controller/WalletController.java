package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Model.*;
import com.Animesh.Kryptrade.Service.OrderService;
import com.Animesh.Kryptrade.Service.PaymentService;
import com.Animesh.Kryptrade.Service.UserService;
import com.Animesh.Kryptrade.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController

public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String token) {
        try {
            User user=userService.findUserProfileByJwt(token);
            Wallet wallet = walletService.getUserWallet(user);
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String token,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req) throws Exception {
       User senderuser=userService.findUserProfileByJwt(token);
       Wallet recevier=walletService.getWalletById(walletId);
       Wallet wallet=walletService.walletTowallet(senderuser,recevier,req.getAmount());
       return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);



    }
    @PutMapping ("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String token,
            @PathVariable Long orderId,
            @RequestBody WalletTransaction req) throws Exception {
        User user=userService.findUserProfileByJwt(token);
        Order order=orderService.getOrderById(orderId);
        Wallet wallet=walletService.payOrderPayment(order,user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);



    }

    @PutMapping ("/api/wallet/deposit")
    public ResponseEntity<Wallet> addMoneyToWallet(
            @RequestHeader("Authorization") String token,
            @RequestParam(name="order_id") Long orderId,
            @RequestParam(name="payment_id") String paymentId,

            @RequestBody WalletTransaction req) throws Exception {
        User user=userService.findUserProfileByJwt(token);
       Wallet wallet=walletService.getUserWallet(user);

        PaymentOrder paymentOrder=paymentService.getPaymentOrderById(orderId);
        Boolean status =paymentService.proceedPaymentOrder(paymentOrder,paymentId);
        if (wallet.getBalance()==null){
            wallet.setBalance(BigDecimal.valueOf(0));
        }
        if(status){
            wallet=walletService.addMoney(wallet,paymentOrder.getAmount());
        }
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);



    }


}
