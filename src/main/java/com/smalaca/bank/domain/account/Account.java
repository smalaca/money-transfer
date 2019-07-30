package com.smalaca.bank.domain.account;

import com.smalaca.bank.domain.Money;

import java.util.concurrent.atomic.AtomicReference;

public class Account {
    private final String owner;
    private AtomicReference<Money> balance;

    public static Account open(String owner) {
        return new Account(owner, Money.ZERO);
    }

    private Account(String owner, Money balance) {
        this.owner = owner;
        this.balance = new AtomicReference<>(balance);
    }

    public void deposit(Money amount) {
        while (true) {
            Money initBalance = balance.get();

            if (balance.compareAndSet(initBalance, initBalance.add(amount))) {
                return;
            }
        }
    }

    public void withdraw(Money amount) {
        if (hasNotEnoughMoney(amount)) {
            throw AccountBalanceException.withdraw(amount, dto());
        }

        while (true) {
            Money initBalance = balance.get();

            if (balance.compareAndSet(initBalance, initBalance.substract(amount))) {
                return;
            }
        }
    }

    private boolean hasNotEnoughMoney(Money amount) {
        return !hasEnoughToWithdraw(amount);
    }

    boolean hasEnoughToWithdraw(Money amount) {
        return balance.get().isNotLowerThan(amount);
    }

    public AccountDto dto() {
        return new AccountDto(owner, balance.get().value());
    }
}
