package com.smalaca.bank.application;

import com.smalaca.bank.domain.account.Account;
import com.smalaca.bank.domain.account.AccountId;
import com.smalaca.bank.domain.account.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class FakeAccountRepository implements AccountRepository {
    private final Map<AccountId, Account> accounts = new HashMap<>();

    @Override
    public AccountId add(Account account) {
        AccountId accountId = AccountId.from(UUID.randomUUID().toString());
        accounts.put(accountId, account);
        return accountId;
    }

    @Override
    public Account find(AccountId id) {
        return accounts.get(id);
    }

    @Override
    public void update(AccountId id, Account account) {
        accounts.put(id, account);
    }
}
