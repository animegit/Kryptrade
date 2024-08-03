package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import com.Animesh.Kryptrade.Requests.ForgotPasswordTokenReq;
import com.Animesh.Kryptrade.Model.ForgotPasswordToken;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.VerificationCode;
import com.Animesh.Kryptrade.Requests.ResetPasswordReq;
import com.Animesh.Kryptrade.Response.ApiResponse;
import com.Animesh.Kryptrade.Response.Authresponse;
import com.Animesh.Kryptrade.Service.ForgotPassword;
import com.Animesh.Kryptrade.Service.JavaMailService;
import com.Animesh.Kryptrade.Service.UserService;
import com.Animesh.Kryptrade.Service.VerificationCodeService;
import com.Animesh.Kryptrade.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    public JavaMailService javaMailService;
    @Autowired
    public VerificationCodeService verificationCodeService;
    @Autowired
    public ForgotPassword forgotPassword;

    @GetMapping("/api/user/profile")
    public ResponseEntity<User> getUserProfile  (@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerifcationCode (@RequestHeader("Authorization") String jwt, @PathVariable Verification_Type verificationType) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode=verificationCodeService.getverifyById(user.getId());
        if(verificationCode==null){
            verificationCode=verificationCodeService.sendverify(user,verificationType);
        }
        if(verificationType.equals(Verification_Type.EMAIL)){
            javaMailService.sendVerification(user.getEmail(),verificationCode.getOtp());
        }
        return new ResponseEntity<>("Otp sent successfully", HttpStatus.OK);

    }
    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User>    enableTwoFactor (@PathVariable String otp,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode=verificationCodeService.getverifyByUser(user.getId());
        String  sendTo=verificationCode.getVerificationType().equals(Verification_Type.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();
        boolean isVerified=verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updateduser=userService.enableTwofactor(verificationCode.getVerificationType(),sendTo,user);
            verificationCodeService.deleteVerificationById(verificationCode);
            return new ResponseEntity<>(updateduser,HttpStatus.OK);
        }
  throw new Exception("Wrong otp!!!");

    }
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<Authresponse> sendForgotPassword ( @RequestBody ForgotPasswordTokenReq req) throws Exception {
        User user=userService.findUserByEmail(req.getSendTo());
      String otp= OtpUtils.generateotp();
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();
        ForgotPasswordToken token=forgotPassword.findByUserId(user.getId());
        if(token==null){
            token=forgotPassword.createToken(user,id,otp,req.getVerificationType(),req.getSendTo());
        }
        if(req.getVerificationType().equals(Verification_Type.EMAIL)){
            javaMailService.sendVerification(user.getEmail(), token.getOtp());
        }
        Authresponse authresponse=new Authresponse();
        authresponse.setSession(token.getId());
        authresponse.setMessage("Password reset");

        return new ResponseEntity<>(authresponse, HttpStatus.OK);

    }
    @PostMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id, @RequestBody ResetPasswordReq resetPasswordReq, @RequestHeader("Authorization") String jwt) throws Exception {
        ForgotPasswordToken forgotPasswordToken=forgotPassword.findById(id);
        boolean isVerified=forgotPasswordToken.getOtp().equals(resetPasswordReq.getOtp());
        if(isVerified){
            userService.updatepswd(forgotPasswordToken.getUser(),resetPasswordReq.getPassword());
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage("Password Updated!!");
            return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
        }
        throw new Exception("Otp Verification Failed!!");
    }



    }
