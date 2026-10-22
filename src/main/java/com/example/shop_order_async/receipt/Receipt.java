package com.example.shop_order_async.receipt;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false)
    private long totalPrice;

    @Column(nullable = false)
    private Instant issuedAt;

    protected Receipt() {}

    public Receipt(Long orderId, long totalPrice, Instant issuedAt) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.issuedAt = issuedAt;
    }

    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public long getTotalPrice() { return totalPrice; }
    public Instant getIssuedAt() { return issuedAt; }
}