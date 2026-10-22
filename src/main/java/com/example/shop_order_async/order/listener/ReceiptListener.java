package com.example.shop_order_async.order.listener;

import com.example.shop_order_async.order.event.OrderCreatedEvent;
import com.example.shop_order_async.receipt.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ReceiptListener {

    private static final Logger log = LoggerFactory.getLogger(ReceiptListener.class);
    private final ReceiptService receiptService;

    public ReceiptListener(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReceipt(OrderCreatedEvent event) {
        log.info("[RECEIPT LISTENER] AFTER_COMMIT, поток={}, orderId={}",
                Thread.currentThread().getName(), event.orderId());
        receiptService.issue(event.orderId(), event.totalPrice());
    }
}