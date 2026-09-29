package com.rupam.ecommerce;

import com.rupam.ecommerce.dao.CategoryDAO;
import com.rupam.ecommerce.dao.OrderDAO;
import com.rupam.ecommerce.dao.ProductDAO;
import com.rupam.ecommerce.dao.UserDAO;

import com.rupam.ecommerce.entity.Category;
import com.rupam.ecommerce.entity.OrderDetails;
import com.rupam.ecommerce.entity.Orders;
import com.rupam.ecommerce.entity.Product;
import com.rupam.ecommerce.entity.Role;
import com.rupam.ecommerce.entity.Users;

import com.rupam.ecommerce.util.HibernateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		CategoryDAO categoryDAO = new CategoryDAO();
		ProductDAO productDAO = new ProductDAO();
		UserDAO userDAO = new UserDAO();
		OrderDAO orderDAO = new OrderDAO();

		System.out.println("===== E-COMMERCE HIBERNATE APPLICATION =====");

		// ==========================================
		// 1. CREATE CATEGORY
		// ==========================================

		Category electronics = new Category("Electronics", "Electronic devices and accessories");

		categoryDAO.save(electronics);

		System.out.println("Category created with ID: " + electronics.getId());

		// ==========================================
		// 2. CREATE PRODUCTS
		// ==========================================

		Product laptop = new Product("Dell Laptop", new BigDecimal("65000.00"), 10);

		Product mouse = new Product("Wireless Mouse", new BigDecimal("1200.00"), 50);

		Product keyboard = new Product("Mechanical Keyboard", new BigDecimal("3500.00"), 25);

		electronics.addProduct(laptop);
		electronics.addProduct(mouse);
		electronics.addProduct(keyboard);

		/*
		 * Category already exists in database. Save products individually.
		 */
		productDAO.save(laptop);
		productDAO.save(mouse);
		productDAO.save(keyboard);

		System.out.println("Products created successfully.");

		// ==========================================
		// 3. CREATE USER
		// ==========================================

		Users user = new Users("rupam", null, "rupam@example.com", Role.CUSTOMER);

		userDAO.save(user, "rupam123");

		System.out.println("User created with ID: " + user.getId());

		// ==========================================
		// 4. CREATE ORDER
		// ==========================================

		BigDecimal laptopTotal = laptop.getPrice().multiply(BigDecimal.valueOf(1));

		BigDecimal mouseTotal = mouse.getPrice().multiply(BigDecimal.valueOf(2));

		BigDecimal keyboardTotal = keyboard.getPrice().multiply(BigDecimal.valueOf(1));

		BigDecimal totalAmount = laptopTotal.add(mouseTotal).add(keyboardTotal);

		Orders order = new Orders(LocalDateTime.now(), totalAmount);

		order.setUser(user);

		// ==========================================
		// 5. CREATE ORDER DETAILS
		// ==========================================

		OrderDetails laptopDetail = new OrderDetails(1, laptop.getPrice());

		laptopDetail.setProduct(laptop);

		OrderDetails mouseDetail = new OrderDetails(2, mouse.getPrice());

		mouseDetail.setProduct(mouse);

		OrderDetails keyboardDetail = new OrderDetails(1, keyboard.getPrice());

		keyboardDetail.setProduct(keyboard);

		// Add details to order

		order.addOrderDetail(laptopDetail);
		order.addOrderDetail(mouseDetail);
		order.addOrderDetail(keyboardDetail);

		// ==========================================
		// 6. SAVE ORDER
		// ==========================================

		orderDAO.save(order);

		System.out.println("Order created with ID: " + order.getId());

		System.out.println("Order total: ₹" + order.getTotalAmount());

		// ==========================================
		// 7. FETCH ORDER
		// ==========================================

		Orders fetchedOrder = orderDAO.findOrderWithDetails(order.getId());

		if (fetchedOrder != null) {

			System.out.println("\n===== ORDER DETAILS =====");

			System.out.println("Order ID: " + fetchedOrder.getId());

			System.out.println("Customer: " + fetchedOrder.getUser().getUsername());

			System.out.println("Email: " + fetchedOrder.getUser().getEmail());

			System.out.println("Order Date: " + fetchedOrder.getOrderDate());

			System.out.println("Total Amount: ₹" + fetchedOrder.getTotalAmount());

			System.out.println("\nProducts:");

			for (OrderDetails detail : fetchedOrder.getOrderDetails()) {

				System.out.println(detail.getProduct().getName() + " | Quantity: " + detail.getQuantity()
						+ " | Unit Price: ₹" + detail.getUnitPrice());
			}
		}

		// ==========================================
		// 8. FIND ALL PRODUCTS
		// ==========================================

		List<Product> products = productDAO.findAll();

		System.out.println("\n===== ALL PRODUCTS =====");

		for (Product product : products) {

			System.out.println(product.getId() + " | " + product.getName() + " | ₹" + product.getPrice() + " | Stock: "
					+ product.getStockQuantity());
		}

		// ==========================================
		// 9. FIND PRODUCTS BY CATEGORY
		// ==========================================

		List<Product> categoryProducts = productDAO.findByCategory(electronics.getId());

		System.out.println("\n===== PRODUCTS IN ELECTRONICS =====");

		for (Product product : categoryProducts) {

			System.out.println(product.getName());
		}

		// ==========================================
		// SHUTDOWN
		// ==========================================

		HibernateUtil.shutdown();

		System.out.println("\nHibernate shutdown completed.");
	}
}