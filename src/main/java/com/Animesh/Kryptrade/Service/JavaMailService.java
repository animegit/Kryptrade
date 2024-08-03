package com.Animesh.Kryptrade.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService {

    private JavaMailSender javaMailSender;
    public void sendVerification(String email,String otp) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,"utf-8");
        String subject="Verify OTP";
        String text="Your Otp is: "+otp;
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(email);

        try{
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new MailSendException(e.getMessage());
        }
    }
}
