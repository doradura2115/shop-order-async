package com.example.shop_order_async.order.listener;

import com.example.shop_order_async.order.event.OrderCreatedEvent;
import com.example.shop_order_async.warehouse.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class WarehouseListener {

    private static final Logger log = LoggerFactory.getLogger(WarehouseListener.class);
    private final WarehouseService warehouseService;

    public WarehouseListener(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Async("warehouseExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleWarehouseAsync(OrderCreatedEvent event) {
        log.info("[ASYNC WAREHOUSE] Передано в пул. Поток={}, orderId={}",
                Thread.currentThread().getName(), event.orderId());
        warehouseService.checkStock(event.orderId(), event.productName(), event.quantity());
    }
}