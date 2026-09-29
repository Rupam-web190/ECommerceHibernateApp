package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDAOTest {

	private CategoryDAO categoryDAO;

	@BeforeEach
	void setUp() {
		categoryDAO = new CategoryDAO();
	}

	@Test
	void testCreateCategory() {

		Category category = new Category("Test Electronics " + System.currentTimeMillis(),
				"Category created during JUnit test");

		categoryDAO.save(category);

		assertNotNull(category.getId());
	}

	@Test
	void testReadCategory() {

		Category category = new Category("Read Test Category " + System.currentTimeMillis(), "Testing read operation");

		categoryDAO.save(category);

		Category found = categoryDAO.findById(category.getId());

		assertNotNull(found);

		assertEquals(category.getName(), found.getName());
	}

	@Test
	void testUpdateCategory() {

		Category category = new Category("Old Category Name " + System.currentTimeMillis(), "Old description");

		categoryDAO.save(category);

		category.setName("Updated Category Name " + System.currentTimeMillis());

		category.setDescription("Updated description");

		categoryDAO.update(category);

		Category updated = categoryDAO.findById(category.getId());

		assertNotNull(updated);

		assertEquals(category.getName(), updated.getName());

		assertEquals("Updated description", updated.getDescription());
	}

	@Test
	void testDeleteCategory() {

		Category category = new Category("Delete Test Category " + System.currentTimeMillis(),
				"This category will be deleted");

		categoryDAO.save(category);

		Long id = category.getId();

		categoryDAO.delete(id);

		Category deleted = categoryDAO.findById(id);

		assertNull(deleted);
	}
}