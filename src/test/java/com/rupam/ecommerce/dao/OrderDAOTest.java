package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Category;
import com.rupam.ecommerce.entity.OrderDetails;
import com.rupam.ecommerce.entity.Orders;
import com.rupam.ecommerce.entity.Product;
import com.rupam.ecommerce.entity.Role;
import com.rupam.ecommerce.entity.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDAOTest {

	private OrderDAO orderDAO;
	private UserDAO userDAO;
	private CategoryDAO categoryDAO;
	private ProductDAO productDAO;

	@BeforeEach
	void setUp() {

		orderDAO = new OrderDAO();
		userDAO = new UserDAO();
		categoryDAO = new CategoryDAO();
		productDAO = new ProductDAO();
	}

	@Test
	void testCreateOrderWithMultipleOrderDetails() {

		// Create user
		String uniqueId = String.valueOf(System.currentTimeMillis());

		Users user = new Users("order_user_" + uniqueId, null, "order_" + uniqueId + "@example.com", Role.CUSTOMER);

		userDAO.save(user, "Order@123");

		assertNotNull(user.getId());

		// Create category
		Category category = new Category("Order Category " + uniqueId, "Category for order test");

		categoryDAO.save(category);

		assertNotNull(category.getId());

		// Create first product
		Product product1 = new Product("Order Product 1 " + uniqueId, new BigDecimal("1000.00"), 10);

		product1.setCategory(category);

		productDAO.save(product1);

		assertNotNull(product1.getId());

		// Create second product
		Product product2 = new Product("Order Product 2 " + uniqueId, new BigDecimal("2000.00"), 20);

		product2.setCategory(category);

		productDAO.save(product2);

		assertNotNull(product2.getId());

		// Create order
		Orders order = new Orders(LocalDateTime.now(), new BigDecimal("5000.00"));

		order.setUser(user);

		// First order detail
		OrderDetails detail1 = new OrderDetails(2, new BigDecimal("1000.00"));

		detail1.setProduct(product1);

		// Second order detail
		OrderDetails detail2 = new OrderDetails(1, new BigDecimal("2000.00"));

		detail2.setProduct(product2);

		// Add both details to order
		order.addOrderDetail(detail1);
		order.addOrderDetail(detail2);

		// Save complete order
		orderDAO.save(order);

		assertNotNull(order.getId());

		assertEquals(2, order.getOrderDetails().size());
	}

	@Test
	void testFindOrderWithDetails() {

		String uniqueId = String.valueOf(System.currentTimeMillis());

		// User
		Users user = new Users("fetch_order_user_" + uniqueId, null, "fetch_order_" + uniqueId + "@example.com",
				Role.CUSTOMER);

		userDAO.save(user, "Fetch@123");

		// Category
		Category category = new Category("Fetch Order Category " + uniqueId, "Category for fetch test");

		categoryDAO.save(category);

		// Product 1
		Product product1 = new Product("Fetch Product 1 " + uniqueId, new BigDecimal("1500.00"), 10);

		product1.setCategory(category);

		productDAO.save(product1);

		// Product 2
		Product product2 = new Product("Fetch Product 2 " + uniqueId, new BigDecimal("2500.00"), 10);

		product2.setCategory(category);

		productDAO.save(product2);

		// Order
		Orders order = new Orders(LocalDateTime.now(), new BigDecimal("5500.00"));

		order.setUser(user);

		// Details
		OrderDetails detail1 = new OrderDetails(2, new BigDecimal("1500.00"));

		detail1.setProduct(product1);

		OrderDetails detail2 = new OrderDetails(1, new BigDecimal("2500.00"));

		detail2.setProduct(product2);

		order.addOrderDetail(detail1);
		order.addOrderDetail(detail2);

		orderDAO.save(order);

		// Fetch order with user, details and products
		Orders fetchedOrder = orderDAO.findOrderWithDetails(order.getId());

		assertNotNull(fetchedOrder);

		assertNotNull(fetchedOrder.getUser());

		assertEquals(user.getUsername(), fetchedOrder.getUser().getUsername());

		assertNotNull(fetchedOrder.getOrderDetails());

		assertEquals(2, fetchedOrder.getOrderDetails().size());

		for (OrderDetails detail : fetchedOrder.getOrderDetails()) {

			assertNotNull(detail.getProduct());
			assertNotNull(detail.getProduct().getName());
		}
	}
}