package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import com.Animesh.Kryptrade.Model.TwoFactorAuth;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Repository.UserRepo;
import com.Animesh.Kryptrade.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email= JwtProvider.getEmail(jwt);
        User user=userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("User Not found!!");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user=userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("User Not found!!");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user=userRepo.findById(userId);
        if(user.isEmpty()){
            throw new Exception("user not found");
        }
        return user.get();
    }

    @Override
    public User enableTwofactor(Verification_Type verificationType,String SendTo, User user) {
        TwoFactorAuth twoFactorAuth=new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSentTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);
        return userRepo.save(user);
    }

    @Override
    public User updatepswd(User user, String newpswd) {
        user.setPassword(newpswd);
        return userRepo.save(user);
    }
}
