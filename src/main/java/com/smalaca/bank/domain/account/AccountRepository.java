package com.smalaca.bank.domain.account;

public interface AccountRepository {
    AccountId add(Account account);

    Account find(AccountId id);

    void update(AccountId id, Account account);
}
