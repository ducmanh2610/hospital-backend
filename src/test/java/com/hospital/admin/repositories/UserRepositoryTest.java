package com.hospital.admin.repositories;

import com.hospital.entities.Role;
import com.hospital.entities.User;
import com.hospital.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired private UserRepository userRepo;

    @Test
    public void testCreateUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("1234$");
        String id = UUID.randomUUID().toString();
        User user = new User(id, "mod", password);
        User userSaved = userRepo.save(user);

        assertThat(userSaved).isNotNull();
        assertThat(userSaved.getId().length()).isGreaterThan(0);
    }

    @Test
    public void testAssignRoleToUser() {
        String userId = "0987c000-536a-439c-9e5f-044a15c95a37"; //admin user
        String roleId = "86435efa-af54-4ad4-b6ce-c5d833e150ff";
        User user = userRepo.findById(userId).get();
        user.addRole(new Role(roleId));

        User updatedUser = userRepo.save(user);
        Assertions.assertThat(updatedUser.getRoles()).hasSize(1);
    }
    @Test
    public void testAssignTwoRoleToUser() {
        String userId = "b2f382f3-68b6-4ef6-982c-707940fd911a"; //moderator user
        String roleEditorId = "60ef0d13-d457-47f6-bff2-a1376bc14258";
        String roleUserId = "673180a5-a52d-4720-8701-c4ff17455f3a";

        User user = userRepo.findById(userId).get();
        user.addRole(new Role(roleEditorId));
        user.addRole(new Role(roleUserId));

        User updatedUser = userRepo.save(user);
        Assertions.assertThat(updatedUser.getRoles()).hasSize(2);

    }

}
