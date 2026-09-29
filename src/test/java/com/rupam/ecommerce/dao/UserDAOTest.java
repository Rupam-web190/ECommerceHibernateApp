package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Role;
import com.rupam.ecommerce.entity.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

	private UserDAO userDAO;

	@BeforeEach
	void setUp() {
		userDAO = new UserDAO();
	}

	@Test
	void testCreateUserAndPasswordHashing() {

		Users user = new Users("test_create_user_" + System.currentTimeMillis(), null,
				"create_" + System.currentTimeMillis() + "@example.com", Role.CUSTOMER);

		String plainPassword = "Test@123";

		userDAO.save(user, plainPassword);

		assertNotNull(user.getId());

		assertNotEquals(plainPassword, user.getPassword());

		assertTrue(BCrypt.checkpw(plainPassword, user.getPassword()));
	}

	@Test
	void testReadUser() {

		String username = "read_user_" + System.currentTimeMillis();

		String email = "read_" + System.currentTimeMillis() + "@example.com";

		Users user = new Users(username, null, email, Role.CUSTOMER);

		userDAO.save(user, "Read@123");

		Users found = userDAO.findById(user.getId());

		assertNotNull(found);

		assertEquals(username, found.getUsername());

		assertEquals(email, found.getEmail());

		assertEquals(Role.CUSTOMER, found.getRole());
	}

	@Test
	void testFindUserByUsername() {

		String username = "username_test_" + System.currentTimeMillis();

		Users user = new Users(username, null, "username_" + System.currentTimeMillis() + "@example.com",
				Role.CUSTOMER);

		userDAO.save(user, "User@123");

		Users found = userDAO.findByUsername(username);

		assertNotNull(found);

		assertEquals(username, found.getUsername());
	}

	@Test
	void testUpdateUser() {

		String username = "update_user_" + System.currentTimeMillis();

		Users user = new Users(username, null, "old_" + System.currentTimeMillis() + "@example.com", Role.CUSTOMER);

		userDAO.save(user, "Update@123");

		user.setEmail("updated_" + System.currentTimeMillis() + "@example.com");

		user.setRole(Role.ADMIN);

		userDAO.update(user);

		Users updated = userDAO.findById(user.getId());

		assertNotNull(updated);

		assertEquals(Role.ADMIN, updated.getRole());
	}

	@Test
	void testDeleteUser() {

		Users user = new Users("delete_user_" + System.currentTimeMillis(), null,
				"delete_" + System.currentTimeMillis() + "@example.com", Role.CUSTOMER);

		userDAO.save(user, "Delete@123");

		Long id = user.getId();

		userDAO.delete(id);

		Users deleted = userDAO.findById(id);

		assertNull(deleted);
	}

}