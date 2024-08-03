package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.Wallet;
import com.Animesh.Kryptrade.Model.Withdrawal;
import com.Animesh.Kryptrade.Service.UserService;
import com.Animesh.Kryptrade.Service.WalletService;
import com.Animesh.Kryptrade.Service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;
    @Autowired
    private UserService userService;
    @Autowired
    private  WalletService walletService;


    // Request a withdrawal
    @PostMapping("api/withdrawal/{amount}")
    public ResponseEntity<Withdrawal> requestWithdrawal(@PathVariable Long amount, @RequestHeader("Authorization") String jwtToken) throws Exception {
        // Extract user from JWT token. You will need a method to parse and validate the JWT token.
        User user=userService.findUserProfileByJwt(jwtToken);
        Wallet wallet=walletService.getUserWallet(user);
        Withdrawal withdrawal=withdrawalService.requestWithdrawal(amount,user);
        walletService.addMoney(wallet,-withdrawal.getAmount());

       //create Transaction service




        return ResponseEntity.ok(withdrawal);
    }

    // Proceed with a withdrawal request (approve or reject)
    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithWithdrawal(@PathVariable Long withdrawalId, @RequestParam boolean accept, @RequestHeader("Authorization") String jwtToken) throws Exception {
        User user=userService.findUserProfileByJwt(jwtToken);
        Withdrawal withdrawal=withdrawalService.proceedWithWithdrawal(withdrawalId,accept);
        Wallet wallet=walletService.getUserWallet(user);

    if(!accept){
    walletService.addMoney(wallet,withdrawal.getAmount());
    }



        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    // Get withdrawal history for a specific user
    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getUserWithdrawalHistory(@PathVariable Long userId, @RequestHeader("Authorization") String jwtToken) throws Exception {
        User user=userService.findUserProfileByJwt(jwtToken);
        List<Withdrawal> withdrawalHistory = withdrawalService.getUserWithdrawalHistory(user);
        return ResponseEntity.ok(withdrawalHistory);
    }

    // Get all withdrawal requests
    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequests(@RequestHeader("Authorization") String jwtToken) throws Exception {
        // Ensure the JWT token is valid

        User user=userService.findUserProfileByJwt(jwtToken);
        List<Withdrawal> allWithdrawals = withdrawalService.getAllWithdrawalRequests();
        return ResponseEntity.ok(allWithdrawals);
    }

}
