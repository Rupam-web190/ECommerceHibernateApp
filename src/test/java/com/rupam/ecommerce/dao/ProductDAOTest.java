package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Category;
import com.rupam.ecommerce.entity.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDAOTest {

	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;

	@BeforeEach
	void setUp() {
		productDAO = new ProductDAO();
		categoryDAO = new CategoryDAO();
	}

	@Test
	void testCreateProduct() {

		Category category = new Category("Product Test Category" + System.currentTimeMillis(),
				"Category for product testing");

		categoryDAO.save(category);

		Product product = new Product("Test Laptop", new BigDecimal("55000.00"), 10);

		product.setCategory(category);

		productDAO.save(product);

		assertNotNull(product.getId());
	}

	@Test
	void testReadProduct() {

		Category category = new Category("Read Product Category" + System.currentTimeMillis(), "Testing product read");

		categoryDAO.save(category);

		Product product = new Product("Read Test Laptop", new BigDecimal("60000.00"), 15);

		product.setCategory(category);

		productDAO.save(product);

		Product found = productDAO.findById(product.getId());

		assertNotNull(found);

		assertEquals("Read Test Laptop", found.getName());

		assertEquals(new BigDecimal("60000.00"), found.getPrice());
	}

	@Test
	void testUpdateProduct() {

		Category category = new Category("Update Product Category" + System.currentTimeMillis(),
				"Testing product update");

		categoryDAO.save(category);

		Product product = new Product("Old Product", new BigDecimal("1000.00"), 5);

		product.setCategory(category);

		productDAO.save(product);

		product.setName("Updated Product");
		product.setPrice(new BigDecimal("1500.00"));
		product.setStockQuantity(20);

		productDAO.update(product);

		Product updated = productDAO.findById(product.getId());

		assertNotNull(updated);

		assertEquals("Updated Product", updated.getName());

		assertEquals(new BigDecimal("1500.00"), updated.getPrice());

		assertEquals(20, updated.getStockQuantity());
	}

	@Test
	void testDeleteProduct() {

		Category category = new Category("Delete Product Category" + System.currentTimeMillis(),
				"Testing product deletion");

		categoryDAO.save(category);

		Product product = new Product("Delete Test Product", new BigDecimal("2000.00"), 5);

		product.setCategory(category);

		productDAO.save(product);

		Long id = product.getId();

		productDAO.delete(id);

		Product deleted = productDAO.findById(id);

		assertNull(deleted);
	}

}