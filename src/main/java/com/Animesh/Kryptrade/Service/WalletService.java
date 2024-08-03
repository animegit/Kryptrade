package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.Order;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.Wallet;

import java.math.BigDecimal;

public interface WalletService {


    Wallet getUserWallet(User user) throws Exception;

Wallet addMoney(Wallet wallet,Long money);
    Wallet getWalletById(Long userId) throws Exception;
    Wallet walletTowallet(User sender,Wallet reciverWallet,Long amt) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;


}
