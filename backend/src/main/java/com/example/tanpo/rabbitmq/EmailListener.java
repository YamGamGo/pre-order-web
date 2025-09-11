package com.example.tanpo.rabbitmq;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    private static final Logger logger = LoggerFactory.getLogger(EmailListener.class);

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "paymentQueue")
    public void sendEmail(String emailMessage) {
        try {
            JsonObject jsonObject = JsonParser.parseString(emailMessage).getAsJsonObject();
            String email = jsonObject.get("email").getAsString();
            String subject = jsonObject.get("subject").getAsString();
            String body = jsonObject.get("body").getAsString();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            logger.info("이메일 전송 성공: {}", email);
        } catch (Exception e) {
            logger.error("이메일 존송 오류 발생: {}", e.getMessage());
        }
    }
}


