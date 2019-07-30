package com.smalaca.bank.domain.account;

import com.smalaca.bank.domain.Money;

public class TransferService {
    public void transfer(Account from, Account to, Money money) {
        if (from.hasEnoughToWithdraw(money)) {
            from.withdraw(money);
            to.deposit(money);
        } else {
            throw AccountBalanceException.withdraw(money, from.dto());
        }
    }
}
