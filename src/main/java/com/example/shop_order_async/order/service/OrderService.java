package com.example.shop_order_async.order.service;

import com.example.shop_order_async.order.api.CreateOrderRequest;
import com.example.shop_order_async.order.domain.Order;
import com.example.shop_order_async.order.event.OrderCreatedEvent;
import com.example.shop_order_async.order.persistence.OrderRepository;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository,
                        ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest req) {
        if (req.productName().startsWith("[ROLLBACK]")) {
            throw new IllegalArgumentException("Принудительный откат: " + req.productName());
        }

        Order order = new Order(req.productName(), req.quantity(),
                req.userId(), req.totalPrice());
        Order saved = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID(),
                saved.getId(),
                saved.getProductName(),
                saved.getQuantity(),
                saved.getUserId(),
                saved.getTotalPrice(),
                req.bonusToSpend(),
                MDC.get("correlationId"),
                Instant.now()
        );
        eventPublisher.publishEvent(event);
        return saved;
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден: " + id));
    }
}