package com.rupam.ecommerce.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime orderDate;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal totalAmount;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetails> orderDetails = new ArrayList<>();

	public Orders() {
	}

	public Orders(LocalDateTime orderDate, BigDecimal totalAmount) {
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void addOrderDetail(OrderDetails orderDetail) {
		orderDetails.add(orderDetail);
		orderDetail.setOrder(this);
	}

	public void removeOrderDetail(OrderDetails orderDetail) {
		orderDetails.remove(orderDetail);
		orderDetail.setOrder(null);
	}

	@Override
	public String toString() {
		return "Orders{" + "id=" + id + ", orderDate=" + orderDate + ", totalAmount=" + totalAmount + '}';
	}
}