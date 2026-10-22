package com.example.shop_order_async.order.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 100)
    private String userId;

    @Column(nullable = false)
    private long totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    protected Order() {}

    public Order(String productName, int quantity, String userId, long totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.NEW;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getUserId() { return userId; }
    public long getTotalPrice() { return totalPrice; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setStatus(OrderStatus status) { this.status = status; }
}