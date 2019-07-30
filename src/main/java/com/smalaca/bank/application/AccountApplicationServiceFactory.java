package com.smalaca.bank.application;

import com.smalaca.bank.domain.account.TransferService;
import com.smalaca.bank.domain.account.AccountRepository;

public class AccountApplicationServiceFactory {
    public AccountApplicationService create(AccountRepository repository) {
        return new AccountApplicationService(repository, new TransferService());
    }
}
