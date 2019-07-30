package com.smalaca.bank.persistance.inmemory;

import com.smalaca.bank.domain.Money;
import com.smalaca.bank.domain.account.Account;
import com.smalaca.bank.domain.account.AccountId;
import com.smalaca.bank.domain.account.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryAccountRepositoryTest {
    private static final String SOME_OWNER = "Peter Parker";

    private final AccountRepository repository = new InMemoryAccountRepository();

    @Test
    void shouldThrowExceptionInCaseOfNoFoundAccount() {
        Executable search = () -> repository.find(AccountId.from("notExisting"));

        NoAccountFoundException exception = Assertions.assertThrows(NoAccountFoundException.class, search);
        assertThat(exception).hasMessage("Account with id: notExisting not found.");
    }

    @Test
    void shouldFindExistingAccount() {
        Account account = Account.open(SOME_OWNER);

        AccountId accountId = repository.add(account);

        Account result = repository.find(accountId);
        assertThat(result).isSameAs(account);
    }

    @Test
    void shouldUpdateAccount() {
        AccountId accountId = repository.add(Account.open(SOME_OWNER));
        Account account = repository.find(accountId);
        account.deposit(Money.from(100));

        repository.update(accountId, account);

        assertThat(repository.find(accountId).dto().money()).isEqualTo("100");
    }

    @Test
    void shouldThrowExceptionInCaseOfUpdatingNotExitingAccount() {
        Executable search = () -> repository.update(AccountId.from("notExisting"), Account.open(SOME_OWNER));

        NoAccountFoundException exception = Assertions.assertThrows(NoAccountFoundException.class, search);
        assertThat(exception).hasMessage("Account with id: notExisting not found.");
    }

}