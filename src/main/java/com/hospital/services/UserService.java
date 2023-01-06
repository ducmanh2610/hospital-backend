package com.hospital.services;

import com.hospital.dto.UserRequest;
import com.hospital.dto.UserResponse;
import com.hospital.entities.Level;
import com.hospital.entities.User;
import com.hospital.repositories.LevelRepository;
import com.hospital.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getUserList() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        User u = userOptional.orElse(null);
        return this.mapToUserResponse(u);
    }

    public UserResponse createNewUser(UserRequest userRequest) {
        User u = this.mapToUserObject(userRequest);
        userRepository.save(u);
        log.info("User ID {" + u.getId() + "} is saved");
        return this.mapToUserResponse(u);
    }
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
        log.info("User ID {" + id + "} is deleted");
    }

    public void updateUserStatusById(UserRequest userRequest) {
        User u = this.mapToUserObject(userRequest);
        userRepository.save(u);
        log.info("User ID {" + u.getId() +"} is updated");
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.isStatus())
                .dateImported(user.getDateImported())
                .dateModified(user.getDateModified())
                .build();
    }

    private User mapToUserObject(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.getId())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .status(userRequest.isStatus())
                .dateImported(userRequest.getDateImported())
                .dateModified(userRequest.getDateModified())
                .build();
    }
}
