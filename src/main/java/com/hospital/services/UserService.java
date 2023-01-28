package com.hospital.services;

import com.hospital.dto.UserRequest;
import com.hospital.entities.Employee;
import com.hospital.entities.Role;
import com.hospital.entities.User;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repositories.EmployeeRepository;
import com.hospital.repositories.RolesRepository;
import com.hospital.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private EmployeeRepository empRepo;
    @Autowired private RolesRepository roleRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<User> getUserList() {
        return userRepo.findAll();
    }

    public User getUserById(String id) {
        Optional<User> userOptional = userRepo.findById(id);
        return userOptional
                .orElseThrow(() -> new ResourceNotFoundException("User ID {" + id + "} not found"));
    }

    public User createNewUser(UserRequest ur) {
        User user = userRepo.findByUsername(ur.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User ID {" + ur.getId() + "} is not found"));
        if(user == null){
            Role roleUser = roleRepo.findByName("ROLE_USER")
                    .orElse(new Role(UUID.randomUUID().toString(), "ROLE_USER"));
            ur.addRole(roleUser);
            log.info("After add Role: " + ur);
            Employee e = empRepo.save(Employee.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName(ur.getFirstName())
                    .lastName(ur.getLastName())
                    .email(ur.getEmail())
                    .dateImported(new Date())
                    .dateModified(new Date())
                    .build());
            ur.setEmployee(e);
            log.info("After set Employee: " + ur);
            User u = this.mapToUserObject(ur);
            u.setRoles(ur.getRoles());

            u.setEmployee(e);
            log.info(u.toString());
            User newUser = userRepo.save(u);
            log.info("User ID {" + u.getId() + "} is saved");
            return newUser;
        }
        return null;
    }

    public void deleteUserById(String id) {
        userRepo.deleteById(id);
        log.info("User ID {" + id + "} is deleted");
    }

    public void updateUserStatusById(UserRequest ur) {
        User user = userRepo.findById(ur.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User ID { " + ur.getId() + "} not found"));
        if(user != null){
            User u = this.mapToUserObject(ur);
            userRepo.save(u);
            log.info("User ID {" + u.getId() + "} is updated");
        }
    }

    private User mapToUserObject(UserRequest ur) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .username(ur.getUsername())
                .password(passwordEncoder.encode(ur.getPassword()))
                .dateImported(new Date())
                .dateModified(new Date())
                .employee(ur.getEmployee())
                .roles(ur.getRoles())
                .build();
    }
}
