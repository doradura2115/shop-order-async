package com.example.shop_order_async.bonus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BonusAccountRepository extends JpaRepository<BonusAccount, Long> {
    Optional<BonusAccount> findByUserId(String userId);
}