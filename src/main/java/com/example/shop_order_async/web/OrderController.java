package com.example.shop_order_async.web;

import com.example.shop_order_async.order.api.CreateOrderRequest;
import com.example.shop_order_async.order.api.OrderResponse;
import com.example.shop_order_async.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest req) {
        var order = orderService.createOrder(req);
        // Теперь метод from() найден, так как класс OrderResponse лежит там, где нужно
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return OrderResponse.from(orderService.findById(id));
    }
}