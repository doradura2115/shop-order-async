package com.example.shop_order_async.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class WarehouseService {

    private static final Logger log = LoggerFactory.getLogger(WarehouseService.class);
    private final WarehouseCheckRepository repository;

    public WarehouseService(WarehouseCheckRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkStock(Long orderId, String productName, int quantity) {
        log.info("[WAREHOUSE] Проверка склада: orderId={}, product={}, qty={}",
                orderId, productName, quantity);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Проверка склада прервана", e);
        }
        boolean available = quantity <= 50;
        repository.save(new WarehouseCheck(orderId, available, Instant.now()));
        log.info("[WAREHOUSE] Проверка завершена: orderId={}, available={}", orderId, available);
    }
}