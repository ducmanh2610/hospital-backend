package com.hospital.services;

import com.hospital.adapters.UserDetailsAdapter;
import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.entities.UserDetailsImpl;
import com.hospital.entities.VerificationToken;
import com.hospital.exceptions.UserAlreadyExistException;
import com.hospital.iservices.IUserService;
import com.hospital.repositories.UserRepository;
import com.hospital.repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username" + username + "not found"));
        return UserDetailsImpl.build(user);
    }
}
