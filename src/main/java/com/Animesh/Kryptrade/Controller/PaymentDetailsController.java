package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Model.PaymentDetails;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Service.PaymentDetailsService;
import com.Animesh.Kryptrade.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    // Add payment details for a user
    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPayment(
            @RequestBody PaymentDetails paymentDetails,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        if (user == null) {
            return ResponseEntity.status(401).build(); // Return 401 Unauthorized if user not found
        }

        // Associate payment details with the user if needed
        PaymentDetails savedPaymentDetails = paymentDetailsService.addPaymentDetails(paymentDetails.getAccountNumber(),paymentDetails.getAccountHolderName(), paymentDetails.getIfsc(), paymentDetails.getBankName(), paymentDetails.getUser());

        return ResponseEntity.ok(savedPaymentDetails);
    }

    // Get payment details for the currently authenticated user
    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getUserPaymentDetails(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        if (user == null) {
            return ResponseEntity.status(401).build(); // Return 401 Unauthorized if user not found
        }

        PaymentDetails paymentDetails = paymentDetailsService.getUserPaymentDetails(user);

        if (paymentDetails == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if payment details not found
        }

        return ResponseEntity.ok(paymentDetails);
    }
}
