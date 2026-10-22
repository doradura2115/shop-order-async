package com.example.shop_order_async.order.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequest(
        @NotBlank String productName,
        @Min(1) int quantity,
        @NotBlank String userId,
        @Positive long totalPrice,
        @Positive long bonusToSpend
) {}