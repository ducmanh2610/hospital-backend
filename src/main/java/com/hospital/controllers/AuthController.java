package com.hospital.controllers;

import com.hospital.dto.GenericResponse;
import com.hospital.dto.JwtRequest;
import com.hospital.dto.JwtResponse;
import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.entities.VerificationToken;
import com.hospital.events.OnRegistrationCompleteEvent;
import com.hospital.exceptions.InvalidOldPasswordException;
import com.hospital.exceptions.UserAlreadyExistException;
import com.hospital.services.CustomUserDetailsService;
import com.hospital.services.UserService;
import com.hospital.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService detailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @GetMapping("/login?success={success}")
    public ResponseEntity<String> loginStatus(@PathVariable boolean success) {
        if(success){
            return ResponseEntity.badRequest().body("Login Failed, Check your information again");
        }
        return ResponseEntity.ok().body("Successfully login");
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = detailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        log.info(userDetails.toString());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
