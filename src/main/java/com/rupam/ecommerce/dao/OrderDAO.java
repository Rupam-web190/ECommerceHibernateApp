package com.rupam.ecommerce.dao;

import com.rupam.ecommerce.entity.Orders;
import com.rupam.ecommerce.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDAO {

	// CREATE ORDER WITH MULTIPLE ORDER DETAILS
	public void save(Orders order) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.persist(order);

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			throw new RuntimeException("Failed to save order", e);
		}
	}

	// READ - Find order by ID
	public Orders findById(Long id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Orders.class, id);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	// READ - Find all orders
	public List<Orders> findAll() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Orders", Orders.class).getResultList();

		} catch (Exception e) {

			e.printStackTrace();
			return List.of();
		}
	}

	// FETCH ORDER WITH USER, ORDER DETAILS AND PRODUCTS
	public Orders findOrderWithDetails(Long orderId) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("""
					SELECT DISTINCT o
					FROM Orders o
					JOIN FETCH o.user
					LEFT JOIN FETCH o.orderDetails od
					LEFT JOIN FETCH od.product
					WHERE o.id = :orderId
					""", Orders.class).setParameter("orderId", orderId).uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	// UPDATE
	public void update(Orders order) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.merge(order);

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to update order", e);
		}
	}

	// DELETE
	public void delete(Long id) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Orders order = session.get(Orders.class, id);

			if (order != null) {
				session.remove(order);
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to delete order", e);
		}
	}
}