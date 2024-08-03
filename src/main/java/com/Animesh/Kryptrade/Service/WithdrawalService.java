package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.WithDrawalStatus;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.Withdrawal; // Assuming this is your model class

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUserWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequests();
}
