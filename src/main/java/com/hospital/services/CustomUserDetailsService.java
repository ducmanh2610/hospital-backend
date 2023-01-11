package com.hospital.services;

import com.hospital.adapters.UserDetailsAdapter;
import com.hospital.dto.UserRequest;
import com.hospital.entities.VerificationToken;
import com.hospital.exceptions.UserAlreadyExistException;
import com.hospital.iservices.IUserService;
import com.hospital.repositories.UserRepository;
import com.hospital.repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private  Map<String, User> roles = new HashMap<>();

    @PostConstruct
    public void init() {
        roles.put("admin2", new User("admin", "{noop}admin1", getAuthority("ROLE_ADMIN")));
        roles.put("user2", new User("user", "{noop}user1", getAuthority("ROLE_USER")));
    }

    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public com.hospital.entities.User getUser(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public void saveRegisteredUser(com.hospital.entities.User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.hospital.entities.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        return new UserDetailsAdapter(user);
    }
    public com.hospital.entities.User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public com.hospital.entities.User registerNewUserAccount(UserRequest ur) throws UserAlreadyExistException {
        if (emailExists(ur.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + ur.getEmail());
        }
        com.hospital.entities.User user = this.mapToUserObject(ur);
        return userRepository.save(user);
    }
    public VerificationToken generateNewVerificationToken(String token) {
        VerificationToken oldToken = tokenRepository.findByToken(token);
        com.hospital.entities.User user = oldToken.getUser();
        VerificationToken newToken = new VerificationToken(token, user);
        return tokenRepository.saveAndFlush(newToken);
    }

    public void changeUserPassword(com.hospital.entities.User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }
    @Override
    public void createVerificationToken(com.hospital.entities.User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    public void createPasswordResetTokenForUser(com.hospital.entities.User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    public String validatePasswordResetToken(String token) {
        final VerificationToken passToken = tokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    private com.hospital.entities.User mapToUserObject(UserRequest ur) {
        return com.hospital.entities.User.builder()
                .id(ur.getId())
                .username(ur.getUsername())
                .password(ur.getPassword())
                .status(ur.isStatus())
                .email(ur.getEmail())
                .dateImported(ur.getDateImported())
                .dateModified(ur.getDateModified())
                .roles(ur.getRoles())
                .build();
    }
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    private boolean isTokenFound(VerificationToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(VerificationToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
    public boolean checkIfValidOldPassword(com.hospital.entities.User user, String oldPassword){
        return user.getUsername().equals(oldPassword);
    }
}
