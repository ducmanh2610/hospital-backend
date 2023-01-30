package com.hospital.services;

import com.hospital.dto.RegisterRequest;
import com.hospital.dto.UserRequest;
import com.hospital.entities.Employee;
import com.hospital.entities.Role;
import com.hospital.entities.User;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repositories.EmployeeRepository;
import com.hospital.repositories.RolesRepository;
import com.hospital.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private EmployeeRepository empRepo;
    @Autowired private RolesRepository roleRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendEmailAddress;

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

    public void registerNewAccount(RegisterRequest rr, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String encodedPassword = passwordEncoder.encode(rr.getPassword());
        rr.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        rr.setVerificationCode(randomCode);

        Employee emp = Employee.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName(rr.getFirstName())
                    .lastName(rr.getLastName())
                    .email(rr.getEmail())
                    .status(true)
                .build();

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username(rr.getUsername())
                .password(encodedPassword)
                .verificationCode(randomCode)
                .status(false)
                .employee(emp)
                .build();

        Role userRole = roleRepo.findByName("ROLE_USER").orElse(null);
        if(userRole != null){
            Role role = Role.builder()
                    .id(UUID.randomUUID().toString())
                    .name("ROLE_USER")
                    .build();
            userRole = roleRepo.save(role);
        }

        user.addRole(userRole);

        empRepo.save(emp);
        userRepo.save(user);

        sendVerificationEmail(rr, siteURL);
    }

    public boolean verify(String verificationCode) {
        User user = userRepo.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setStatus(true);
            userRepo.save(user);

            return true;
        }
    }

    private void sendVerificationEmail(RegisterRequest rr, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = rr.getEmail();
        String fromAddress = sendEmailAddress;
        String senderName = "Verification Email";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Hospital Admin Teams.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", rr.getFirstName() + " " + rr.getLastName());
        String verifyURL = siteURL + "/api/v1/auth/verify?code=" + rr.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

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
