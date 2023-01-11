package com.hospital.utils;

import com.hospital.entities.User;
import com.hospital.entities.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
@RequiredArgsConstructor
public class JavaMailUtil {
    public final MessageSource messages;
    public SimpleMailMessage constructResendVerificationTokenEmail
            (String contextPath, Locale locale, VerificationToken newToken, User user) {
        String confirmationUrl =
                contextPath + "/regitrationConfirm.html?token=" + newToken.getToken();
        String message = messages.getMessage("message.resendToken", null, locale);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " rn" + confirmationUrl);
        email.setFrom("manh.user1@gmail.com");
        email.setTo(user.getEmail());
        return email;
    }
}
