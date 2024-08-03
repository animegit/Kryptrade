package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Domain.Verification_Type;
import com.Animesh.Kryptrade.Model.User;

public interface UserService  {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;
    public User enableTwofactor(Verification_Type verificationType,String sendTo, User user);
    User updatepswd(User user,String newpswd);

}
