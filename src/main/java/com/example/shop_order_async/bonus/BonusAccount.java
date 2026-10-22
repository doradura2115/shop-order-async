package com.example.shop_order_async.bonus;

import jakarta.persistence.*;

@Entity
@Table(name = "bonus_accounts")
public class BonusAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String userId;

    @Column(nullable = false)
    private long balance;

    protected BonusAccount() {}

    public BonusAccount(String userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public long getBalance() { return balance; }
    public void setBalance(long balance) { this.balance = balance; }
}