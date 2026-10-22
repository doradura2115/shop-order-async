package com.example.shop_order_async.order.event;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        Long orderId,
        String productName,
        int quantity,
        String userId,
        long totalPrice,
        long bonusToSpend,
        String correlationId,
        Instant occurredAt
) {}