package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.PaymentStatus;
import com.Animesh.Kryptrade.Domain.PaymentType;
import com.Animesh.Kryptrade.Model.PaymentOrder;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Repository.PaymentOrderRepo;
import com.Animesh.Kryptrade.Response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorApiKey;

    @Value("${razorpay.api.secret}")
    private String razorApiSecretKey;

    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentType paymentType) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentType(paymentType);
        paymentOrder.setPaymentStatus(PaymentStatus.PENDING); // Set initial status as PENDING
        return paymentOrderRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepo.findById(id)
                .orElseThrow(() -> new Exception("Payment order not found"));
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if(paymentOrder.getPaymentStatus()==null){
            paymentOrder.setPaymentStatus(PaymentStatus.PENDING);
        }

        if (paymentOrder.getPaymentStatus().equals(PaymentStatus.PENDING)) {

            if (paymentOrder.getPaymentType().equals(PaymentType.RAZOR)) {
                RazorpayClient client = new RazorpayClient(razorApiKey, razorApiSecretKey);

                Payment payment = client.payments.fetch(paymentId);
                String status = payment.get("status");
                if (status.equals("captured")) {
                    paymentOrder.setPaymentStatus(PaymentStatus.SUCCESS);
                    paymentOrderRepo.save(paymentOrder);
                    return true;
                }
                paymentOrder.setPaymentStatus(PaymentStatus.FAILED);
                paymentOrderRepo.save(paymentOrder);
                return false;
            }
            // Handle other payment types if needed
            paymentOrder.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentOrderRepo.save(paymentOrder);
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorPaymentLink(User user, Long amount,Long ordeId) throws RazorpayException {
        Long amt = 100 * amount;

        RazorpayClient client = new RazorpayClient(razorApiKey, razorApiSecretKey);


        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amt);
        paymentLinkRequest.put("currency", "INR");
        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());
        paymentLinkRequest.put("customer", customer);
        JSONObject notify = new JSONObject();
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);
        paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet?order_id="+ordeId);
        paymentLinkRequest.put("callback_method", "get");

        PaymentLink paymentLink = client.paymentLink.create(paymentLinkRequest);
        String paymentLinkUrl = paymentLink.get("short_url");
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentUrl(paymentLinkUrl);

        return paymentResponse;
    }

    @Override
    public PaymentResponse createStriperPaymentLink(User user, Long amount, Long orderId) {
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount * 100) // amount in cents
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Top up wallet")
                                        .build())
                                .build())
                        .build())
                .build();


        try {
            Session session = Session.create(params);
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentUrl(session.getUrl());

            return paymentResponse;
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }


}
