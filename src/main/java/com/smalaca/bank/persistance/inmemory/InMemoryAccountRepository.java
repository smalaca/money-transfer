package com.smalaca.bank.persistance.inmemory;

import com.smalaca.bank.domain.account.Account;
import com.smalaca.bank.domain.account.AccountId;
import com.smalaca.bank.domain.account.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<AccountId, Account> accounts = new HashMap<>();

    @Override
    public AccountId add(Account account) {
        AccountId accountId = AccountId.from(randomId());
        accounts.put(accountId, account);

        return accountId;
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Account find(AccountId id) {
        if (doesNotExist(id)) {
            throw new NoAccountFoundException(id);
        }

        return accounts.get(id);
    }

    @Override
    public void update(AccountId id, Account account) {
        if (doesNotExist(id)) {
            throw new NoAccountFoundException(id);
        }

        accounts.put(id, account);
    }

    private boolean doesNotExist(AccountId id) {
        return !accounts.containsKey(id);
    }
}
