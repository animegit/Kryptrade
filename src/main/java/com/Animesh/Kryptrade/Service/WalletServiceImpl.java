package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.OrderType;
import com.Animesh.Kryptrade.Model.Order;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.Wallet;
import com.Animesh.Kryptrade.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserService userService; // Assuming a UserService to get user details

    @Override
    public Wallet getUserWallet(User user) throws Exception {


        Wallet wallet = walletRepository.findByUserId(user.getId());
        if(wallet==null){
            wallet=new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        walletRepository.save(wallet);

        }
        return wallet;
    }

    @Override
    public Wallet addMoney(Wallet wallet, Long money) {
        BigDecimal amountToAdd = BigDecimal.valueOf(money);
        wallet.setBalance(wallet.getBalance().add(amountToAdd));
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet getWalletById(Long userId) throws Exception {
        Optional<Wallet> wallet=walletRepository.findById(userId);
        if (wallet.isPresent() ) {
            return wallet.get();

        }
        throw new Exception("Wallet not found for user id: " + userId);

    }

    @Override
    public Wallet walletTowallet(User sender, Wallet receiverWallet, Long amt) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amt)) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        BigDecimal amount = BigDecimal.valueOf(amt);
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBal=wallet.getBalance().subtract(order.getPrice());
            if(newBal.compareTo(order.getPrice())<0){
                throw new Exception("Insuff balance");
            }
           wallet.setBalance(newBal);


        }
        else{
            BigDecimal newBal=wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBal);

        }
        walletRepository.save(wallet);
        return wallet;
    }
}
