package com.smalaca.bank.application;

import com.smalaca.bank.domain.Money;
import com.smalaca.bank.domain.account.*;

public class AccountApplicationService {
    private final AccountRepository accountRepository;
    private final TransferService transferService;

    AccountApplicationService(AccountRepository accountRepository, TransferService transferService) {
        this.accountRepository = accountRepository;
        this.transferService = transferService;
    }

    public String open(String owner) {
        Account account = Account.open(owner);
        AccountId accountId = accountRepository.add(account);

        return accountId.value();
    }

    public void deposit(String id, double amount) {
        AccountId accountId = AccountId.from(id);
        Account account = anAccount(accountId);

        account.deposit(aMoney(amount));

        accountRepository.update(accountId, account);
    }

    public void withdraw(String id, double amount) {
        AccountId accountId = AccountId.from(id);
        Account account = anAccount(accountId);

        account.withdraw(aMoney(amount));

        accountRepository.update(accountId, account);
    }

    public void transfer(String from, String to, double amount) {
        AccountId id1 = AccountId.from(from);
        AccountId id2 = AccountId.from(to);
        Account account1 = anAccount(id1);
        Account account2 = anAccount(id2);

        transferService.transfer(account1, account2, aMoney(amount));

        accountRepository.update(id1, account1);
        accountRepository.update(id2, account2);
    }

    private Money aMoney(double amount) {
        return Money.from(amount);
    }

    public AccountDto balance(String id) {
        return anAccount(AccountId.from(id)).dto();
    }

    private Account anAccount(AccountId accountId) {
        return accountRepository.find(accountId);
    }
}
