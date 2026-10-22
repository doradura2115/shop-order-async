package com.example.shop_order_async.order.listener;

import com.example.shop_order_async.bonus.BonusService;
import com.example.shop_order_async.order.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BonusListener {

    private static final Logger log = LoggerFactory.getLogger(BonusListener.class);
    private final BonusService bonusService;

    public BonusListener(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @Async("bonusExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBonusAsync(OrderCreatedEvent event) {
        log.info("[ASYNC BONUS] Передано в пул. Поток={}, orderId={}",
                Thread.currentThread().getName(), event.orderId());
        bonusService.spendBonus(event.orderId(), event.userId(), event.bonusToSpend());
    }
}