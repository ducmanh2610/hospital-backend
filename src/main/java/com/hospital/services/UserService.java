package com.hospital.services;

import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username " + username + " not found");
        });
    }

    public User createNewUser(UserRequest ur) {
        User u = this.mapToUserObject(ur);
        userRepository.save(u);
        log.info("User ID {" + u.getId() + "} is saved");
        return u;
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
        log.info("User ID {" + id + "} is deleted");
    }

    public void updateUserStatusById(UserRequest ur) {
        User u = this.mapToUserObject(ur);
        userRepository.save(u);
        log.info("User ID {" + u.getId() + "} is updated");
    }

    private User mapToUserObject(UserRequest ur) {
        return User.builder()
                .id(ur.getId())
                .username(ur.getUsername())
                .password(ur.getPassword())
                .email(ur.getEmail())
                .dateImported(ur.getDateImported())
                .dateModified(ur.getDateModified())
//                .roles(ur.getRoles())
                .build();
    }
}
