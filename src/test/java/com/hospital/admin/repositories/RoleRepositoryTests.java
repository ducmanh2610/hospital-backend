package com.hospital.admin.repositories;

import com.hospital.entities.Role;
import com.hospital.repositories.RolesRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	@Autowired
	private RolesRepository rolesRepo;

	@Test
	public void testCreateRoles() {
		Role admin = new Role(UUID.randomUUID().toString(),"ROLE_ADMIN");
		Role editor = new Role(UUID.randomUUID().toString(),"ROLE_EDITOR");
		Role user = new Role(UUID.randomUUID().toString(),"ROLE_USER");

		rolesRepo.saveAll(List.of(admin, editor, user));
		long numberOfRoles = rolesRepo.count();
		assertEquals(3, numberOfRoles);
	}

	@Test
	public void testListRoles() {
		List<Role> listRoles = rolesRepo.findAll();
		assertThat(listRoles.size()).isGreaterThan(0);
		listRoles.forEach(System.out::println);
	}
}
