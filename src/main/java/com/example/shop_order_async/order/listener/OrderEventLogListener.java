package com.example.shop_order_async.order.listener;

import com.example.shop_order_async.order.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventLogListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventLogListener.class);

    @EventListener
    public void handleSync(OrderCreatedEvent event) {
        log.info("[SYNC LISTENER] Заказ создан (до COMMIT): eventId={}, orderId={}, product={}, thread={}",
                event.eventId(), event.orderId(), event.productName(),
                Thread.currentThread().getName());
    }
}