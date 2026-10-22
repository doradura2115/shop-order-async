package com.example.shop_order_async.bonus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BonusService {

    private static final Logger log = LoggerFactory.getLogger(BonusService.class);
    private final BonusAccountRepository repository;

    public BonusService(BonusAccountRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void spendBonus(Long orderId, String userId, long amount) {
        log.info("[BONUS] Старт списания бонусов: orderId={}, user={}, amount={}",
                orderId, userId, amount);

        if (amount == -1) {
            throw new IllegalArgumentException("Искусственная ошибка списания бонусов");
        }

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Поток списания бонусов прерван", e);
        }

        BonusAccount acc = repository.findByUserId(userId)
                .orElseGet(() -> new BonusAccount(userId, 10_000));
        long newBalance = Math.max(0, acc.getBalance() - amount);
        acc.setBalance(newBalance);
        repository.save(acc);

        log.info("[BONUS] Списание завершено: orderId={}, новый баланс={}", orderId, newBalance);
    }
}