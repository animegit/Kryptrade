package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.WithDrawalStatus;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.Withdrawal;
import com.Animesh.Kryptrade.Repository.WithdrawalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private WithdrawalRepo withdrawalRepo;

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        // Implement withdrawal request logic here
        Withdrawal withdrawal = new Withdrawal(); // Example instantiation
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithDrawalStatus.PENDING);
        // Save withdrawal request to repository
        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        // Implement withdrawal approval/rejection logic here
        Optional<Withdrawal> withdrawal = withdrawalRepo.findById(withdrawalId);
        if(withdrawal.isEmpty()){
            throw new Exception("withdrawal not found");
        }
        Withdrawal withdrawal1=withdrawal.get();
        withdrawal1.setDateTime(LocalDateTime.now());

            if (accept) {

                withdrawal1.setStatus(WithDrawalStatus.SUCCESS); // Example status update
            } else {
                // Reject withdrawal
                withdrawal1.setStatus(WithDrawalStatus.PENDING); // Example status update
            }


        return withdrawalRepo.save(withdrawal1);

    }

    @Override
    public List<Withdrawal> getUserWithdrawalHistory(User user) {
        // Implement logic to retrieve withdrawal history for a user
        return withdrawalRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequests() {
        // Implement logic to retrieve all withdrawal requests
        return withdrawalRepo.findAll();
    }
}
