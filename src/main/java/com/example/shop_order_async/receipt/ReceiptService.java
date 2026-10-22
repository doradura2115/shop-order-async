package com.example.shop_order_async.receipt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ReceiptService {

    private static final Logger log = LoggerFactory.getLogger(ReceiptService.class);
    private final ReceiptRepository repository;

    public ReceiptService(ReceiptRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void issue(Long orderId, long totalPrice) {
        log.info("[RECEIPT] Формирование чека: orderId={}, sum={}", orderId, totalPrice);
        repository.save(new Receipt(orderId, totalPrice, Instant.now()));
        log.info("[RECEIPT] Чек сформирован: orderId={}", orderId);
    }
}