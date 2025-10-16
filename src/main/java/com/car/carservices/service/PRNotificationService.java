// com/car/carservices/service/PRNotificationService.java
package com.car.carservices.service;

import org.springframework.beans.factory.annotation.Autowired;
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PRNotificationService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:no-reply@example.local}")
    private String from;

    @Value("${twilio.account-sid:}")
    private String twilioSid;

    @Value("${twilio.auth-token:}")
    private String twilioToken;

    // Twilio Sandbox (testing): e.g., +15005550006 or your sandbox From number
    @Value("${twilio.from-number:}")
    private String twilioFrom;

    // in PRNotificationService
    public PRNotificationService(@Autowired(required = false) JavaMailSender mailSender) {
        this.mailSender = mailSender; // may be no-op or null if you prefer
    }


    public void sendEmailCode(String toEmail, String code, @Nullable String subjectPrefix) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(toEmail);
        msg.setSubject((subjectPrefix == null ? "" : subjectPrefix + " ") + "Your verification code");
        msg.setText("Your verification code is: " + code);
        mailSender.send(msg);
    }

    

    public void sendSmsCode(String toMobile, String code) {
        if (twilioSid == null || twilioSid.isBlank()) return; // no-op if not configured
      /*   Twilio.init(twilioSid, twilioToken);
        Message.creator(
            new com.twilio.type.PhoneNumber(toMobile),
            new com.twilio.type.PhoneNumber(twilioFrom),
            "Your verification code is: " + code
        ).create();*/
    }
}
