package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.PaymentDetails;
import com.Animesh.Kryptrade.Model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNum, String HolderName, String ifsc, String bankName, User user);
    public PaymentDetails getUserPaymentDetails(User user);
}
