package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.PaymentType;
import com.Animesh.Kryptrade.Model.PaymentOrder;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Response.PaymentResponse;
import com.razorpay.RazorpayException;


public interface PaymentService {


    PaymentOrder createOrder(User user, Long amount, PaymentType paymentType);


    PaymentOrder getPaymentOrderById(Long id) throws Exception;


    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;


    PaymentResponse createRazorPaymentLink(User user, Long amount,Long orderId) throws RazorpayException;
    PaymentResponse createStriperPaymentLink(User user, Long amount,Long orderId);
}
