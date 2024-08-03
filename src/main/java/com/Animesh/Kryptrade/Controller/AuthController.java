package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Model.TwoFactorOtp;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Repository.TwoFactorRepo;
import com.Animesh.Kryptrade.Repository.UserRepo;
import com.Animesh.Kryptrade.Response.Authresponse;
import com.Animesh.Kryptrade.Service.*;
import com.Animesh.Kryptrade.config.JwtProvider;
import com.Animesh.Kryptrade.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public UserRepo userRepo;
    @Autowired
    public CustomeUserServiceDetails serviceDetails;
    @Autowired
    public TwoFactorOtpSevice twoFactorOtpSevice;
    @Autowired
    public JavaMailService email;
    @Autowired
    public WatchListService watchListService;

    @PostMapping("/signup")
    public ResponseEntity<Authresponse> signup(@RequestBody User user) throws Exception {
        User newUser=new User();

        User isEmail=userRepo.findByEmail(user.getEmail());
        if(isEmail!=null){
            throw  new Exception("Email is already used");
        }
        newUser.setFullName(user.getFullName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        User saveduser=userRepo.save(newUser);
        watchListService.createWatchList(saveduser);

        Authentication auth=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication((auth));
        String jwt= JwtProvider.generateToken(auth);

        Authresponse res=new Authresponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register sucess");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<Authresponse> login(@RequestBody User user) throws Exception {

    String username=user.getEmail();
    String password=user.getPassword();
        Authentication auth=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication((auth));
        String jwt= JwtProvider.generateToken(auth);

        User authUser=userRepo.findByEmail(username);

        if(user.getTwoFactorAuth().isEnabled()){
            Authresponse res=new Authresponse();
            res.setMessage("Two Factor auth is Enabled");
            res.setTwoFactorAuth(true);
            String otp= OtpUtils.generateotp();
            TwoFactorOtp oldtwofactor=twoFactorOtpSevice.findByUser(authUser.getId());
            if(oldtwofactor!=null){
                twoFactorOtpSevice.deleteTwoFactorOtp(oldtwofactor);
            }
            TwoFactorOtp newTwofactor=twoFactorOtpSevice.createTwoFactorOtp(authUser,otp,jwt);
            email.sendVerification(username,otp);
            res.setSession(newTwofactor.getId());

            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }

        Authresponse res=new Authresponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login sucess");


        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails=serviceDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }
    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<Authresponse> verifysignin(@PathVariable String otp,@RequestParam String id) throws Exception {
        TwoFactorOtp twoFactorOtp=twoFactorOtpSevice.findById(id);
        if(twoFactorOtpSevice.verifyTwoFactorOtp(twoFactorOtp,otp)){
            Authresponse res=new Authresponse();
            res.setMessage("Two Factor auth verified");
            res.setTwoFactorAuth(true);
            res.setJwt(twoFactorOtp.getJwt());
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        throw new Exception("invalid otp");
    }
}
