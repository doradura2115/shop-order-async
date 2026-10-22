package com.example.shop_order_async.warehouse;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "warehouse_checks")
public class WarehouseCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private Instant checkedAt;

    protected WarehouseCheck() {}

    public WarehouseCheck(Long orderId, boolean available, Instant checkedAt) {
        this.orderId = orderId;
        this.available = available;
        this.checkedAt = checkedAt;
    }

    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public boolean isAvailable() { return available; }
    public Instant getCheckedAt() { return checkedAt; }
}