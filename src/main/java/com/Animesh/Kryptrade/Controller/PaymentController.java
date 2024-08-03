package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Domain.PaymentType;
import com.Animesh.Kryptrade.Model.PaymentOrder;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Response.PaymentResponse;
import com.Animesh.Kryptrade.Service.PaymentService;
import com.Animesh.Kryptrade.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController

public class PaymentController {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/api/payment/{paymentType}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentType paymentType, @PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserProfileByJwt(jwt);
        if(user!=null){
            System.out.println(jwt);
        }
        PaymentResponse paymentResponse;
        PaymentOrder order=paymentService.createOrder(user,amount,paymentType);
        if(paymentType.equals(PaymentType.RAZOR)){
            paymentResponse=paymentService.createRazorPaymentLink(user,amount,order.getId());
        }
        else{
            paymentResponse=paymentService.createStriperPaymentLink(user,amount,order.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
