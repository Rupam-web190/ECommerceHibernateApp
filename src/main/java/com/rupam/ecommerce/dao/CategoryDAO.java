package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Category;
import com.rupam.ecommerce.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CategoryDAO {

	// CREATE
	public void save(Category category) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.persist(category);

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to save category", e);
		}
	}

	// READ - Find by ID
	public Category findById(Long id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Category.class, id);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	// READ - Find all
	public List<Category> findAll() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Category", Category.class).getResultList();

		} catch (Exception e) {

			e.printStackTrace();
			return List.of();
		}
	}

	// UPDATE
	public void update(Category category) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.merge(category);

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to update category", e);
		}
	}

	// DELETE
	public void delete(Long id) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Category category = session.get(Category.class, id);

			if (category != null) {
				session.remove(category);
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to delete category", e);
		}
	}
}