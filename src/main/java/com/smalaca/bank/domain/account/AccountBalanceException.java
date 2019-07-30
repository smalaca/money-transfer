package com.smalaca.bank.domain.account;

import com.smalaca.bank.domain.Money;

import static java.lang.String.format;

class AccountBalanceException extends RuntimeException {
    private static final String WITHDRAWAL_EXCEPTION_TEMPLATE = "Cannot withdraw: %s from %s account.";

    private AccountBalanceException(String message) {
        super(message);
    }

    static RuntimeException withdraw(Money amount, AccountDto dto) {
        return new AccountBalanceException(format(WITHDRAWAL_EXCEPTION_TEMPLATE, amount.value(), dto.owner())); }
}
