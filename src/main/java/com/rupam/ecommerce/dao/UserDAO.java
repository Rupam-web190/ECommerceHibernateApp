package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Users;
import com.rupam.ecommerce.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserDAO {

	// CREATE
	public void save(Users user, String plainPassword) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

			user.setPassword(hashedPassword);

			session.persist(user);

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to save user", e);
		}
	}

	// READ - Find by ID
	public Users findById(Long id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Users.class, id);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	// READ - Find all
	public List<Users> findAll() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Users", Users.class).getResultList();

		} catch (Exception e) {

			e.printStackTrace();
			return List.of();
		}
	}

	// Find by username
	public Users findByUsername(String username) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Users u WHERE u.username = :username", Users.class)
					.setParameter("username", username).uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	// UPDATE
	public void update(Users user) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.merge(user);

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to update user", e);
		}
	}

	// DELETE
	public void delete(Long id) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Users user = session.get(Users.class, id);

			if (user != null) {
				session.remove(user);
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to delete user", e);
		}
	}
}
