package com.hospital.iservices;

import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.entities.VerificationToken;
import com.hospital.exceptions.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService{
    User registerNewUserAccount(UserRequest ur)
            throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
