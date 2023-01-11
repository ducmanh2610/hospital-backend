package com.hospital.controllers;

import com.hospital.dto.GenericResponse;
import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.entities.VerificationToken;
import com.hospital.events.OnRegistrationCompleteEvent;
import com.hospital.exceptions.InvalidOldPasswordException;
import com.hospital.exceptions.UserAlreadyExistException;
import com.hospital.services.CustomUserDetailsService;
import com.hospital.utils.JavaMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {
    @Autowired
    private CustomUserDetailsService detailsService;
    @Autowired
    private JavaMailUtil javaMailUtil;
    @Autowired
    private ApplicationEventPublisher aep;
    protected MailSender mailSender;
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserRequest ur, HttpServletRequest request) {
        try {
            User userRegistered = detailsService.registerNewUserAccount(ur);
            String appUrl = request.getContextPath();
            aep.publishEvent(new OnRegistrationCompleteEvent(userRegistered,
                    request.getLocale(), appUrl));
            return ResponseEntity.ok("User " + userRegistered.getUsername() + " is registered successfully");
        } catch (UserAlreadyExistException uaeEx) {
            return ResponseEntity.status(409).body("User is already exist");
        } catch (RuntimeException re){
            return ResponseEntity.status(400).body("Email is Invalid");
        }
    }

    @GetMapping("/registered?token={token}")
    public ResponseEntity<String> sendTokenToEmail(@PathVariable String token) {
        return ResponseEntity.ok("Email verification has been sent");
    }
    @GetMapping("/resend-token")
    public ResponseEntity<GenericResponse> resendRegistrationToken(
            HttpServletRequest request, @RequestParam("token") String existingToken) {
        VerificationToken newToken = detailsService.generateNewVerificationToken(existingToken);

        User user = detailsService.getUser(newToken.getToken());
        String appUrl = "https://" + request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
        SimpleMailMessage email =
                javaMailUtil.constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
        mailSender.send(email);

        GenericResponse g = new GenericResponse(
                javaMailUtil.messages.getMessage("message.resendToken", null, request.getLocale()));
        return ResponseEntity.ok(g);
    }
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    @PostMapping("/update-password")
    public ResponseEntity<String> changePassword(
            Locale locale,
            @RequestParam("password") String password,
            @RequestParam("oldPassword") String oldPassword) {
        User user = detailsService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (!detailsService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        detailsService.changeUserPassword(user, password);
        return ResponseEntity.ok("Password Changed");
    }
    @GetMapping("/registration-confirm")
    public ResponseEntity<String> confirmRegistration(
            Locale locale, @RequestParam("token") String token) {
        VerificationToken verificationToken = detailsService.getVerificationToken(token);
        HttpHeaders headers = new HttpHeaders();
        if (verificationToken == null) {
            String message = javaMailUtil.messages.getMessage("auth.message.invalidToken", null, locale);
            headers.setLocation(URI.create("/bad-user?lang=" + locale.getLanguage()));
            return ResponseEntity.status(302).headers(headers).body(message);
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            boolean isExpired = true;
            String message = javaMailUtil.messages.getMessage("auth.message.expired", null, locale);
            headers.setLocation(URI.create("/bad-user?lang=" + locale.getLanguage()));
            return ResponseEntity.status(302).headers(headers).body(message);
        }

        user.setEnabled(true);
        detailsService.saveRegisteredUser(user);
        String message = javaMailUtil.messages.getMessage("message.accountVerified", null, locale);
        return ResponseEntity.status(302).headers(headers).body(message);
    }
    @GetMapping("/change-password")
    public ResponseEntity<String> showChangePasswordPage(Locale locale, @RequestParam("token") String token) {
        String result = detailsService.validatePasswordResetToken(token);
        HttpHeaders headers = new HttpHeaders();
        if(result != null) {
            String message = javaMailUtil.messages.getMessage("auth.message." + result, null, locale);
            headers.setLocation(URI.create("/login?lang=" + locale.getLanguage()));
            return ResponseEntity.status(200).headers(headers).body(message);
        } else {
            headers.setLocation(URI.create("/update-password?lang=" + locale.getLanguage()));
            return ResponseEntity.status(200).headers(headers).body("Update password");
        }
    }
}
