package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Product;
import com.rupam.ecommerce.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAO {

	// CREATE
	public void save(Product product) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.persist(product);

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to save product", e);
		}
	}

	// READ - Find by ID
	public Product findById(Long id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Product.class, id);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	// READ - Find all
	public List<Product> findAll() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Product", Product.class).getResultList();

		} catch (Exception e) {

			e.printStackTrace();
			return List.of();
		}
	}

	// UPDATE
	public void update(Product product) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.merge(product);

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to update product", e);
		}
	}

	// DELETE
	public void delete(Long id) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Product product = session.get(Product.class, id);

			if (product != null) {
				session.remove(product);
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to delete product", e);
		}
	}

	// Bonus: Find products by category
	public List<Product> findByCategory(Long categoryId) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Product p WHERE p.category.id = :categoryId", Product.class)
					.setParameter("categoryId", categoryId).getResultList();

		} catch (Exception e) {

			e.printStackTrace();
			return List.of();
		}
	}
}
