package com.smalaca.bank.persistance.inmemory;

import com.smalaca.bank.domain.account.AccountId;

class NoAccountFoundException extends RuntimeException {
    NoAccountFoundException(AccountId id) {
        super("Account with id: " + id.value() + " not found.");
    }
}
