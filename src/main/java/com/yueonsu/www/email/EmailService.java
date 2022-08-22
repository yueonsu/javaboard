package com.yueonsu.www.email;

import com.yueonsu.www.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired private JavaMailSender javaMailSender;

    public String sendCode(UserEntity entity) {
        Random random = new Random();
        String key = "";

        for(int i=0; i<6; i++) {
            String ran = String.valueOf(random.nextInt(10));
            key += ran;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(entity.getSEmail());
        message.setSubject("회원가입 인증번호는 " + key + "입니다.");
        message.setText("회원가입 인증번호는 " + key + "입니다.");
//        message.setFrom("yutestyu@gmail.com");

        javaMailSender.send(message);

        return key;
    }
}
