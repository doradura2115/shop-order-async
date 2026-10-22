package com.example.shop_order_async.order.api;

import com.example.shop_order_async.order.domain.Order;
import java.time.Instant;

public record OrderResponse(
        Long id,
        String productName,
        int quantity,
        String userId,
        long totalPrice,
        String status,
        Instant createdAt
) {
    public static OrderResponse from(Order o) {
        return new OrderResponse(o.getId(), o.getProductName(), o.getQuantity(),
                o.getUserId(), o.getTotalPrice(), o.getStatus().name(), o.getCreatedAt());
    }
}