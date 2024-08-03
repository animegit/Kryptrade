package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.PaymentDetails;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Repository.PaymentDetailsREpo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl  implements PaymentDetailsService{
    @Autowired
    private PaymentDetailsREpo paymentDetailsREpo;
    @Override
    public PaymentDetails addPaymentDetails(String accountNum, String HolderName, String ifsc, String bankName, User user) {
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setAccountNumber(accountNum);
        paymentDetails.setAccountHolderName(HolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);
        return paymentDetailsREpo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUserPaymentDetails(User user) {
       return paymentDetailsREpo.findByUserId(user.getId());}
}
