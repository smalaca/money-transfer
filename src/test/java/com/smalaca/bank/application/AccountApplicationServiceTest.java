package com.smalaca.bank.application;

import com.smalaca.bank.domain.account.AccountDto;
import com.smalaca.bank.domain.account.AccountId;
import com.smalaca.bank.domain.account.AccountRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountApplicationServiceTest {
    private static final String SOME_OWNER = "Peter Parker";

    private final AccountRepository repository = new FakeAccountRepository();
    private final AccountApplicationService service = new AccountApplicationServiceFactory().create(repository);

    @Test
    void shouldOpenAccount() {
        String id = service.open(SOME_OWNER);

        AccountDto account = repository.find(AccountId.from(id)).dto();
        assertThat(account.money()).isEqualTo("0");
        assertThat(account.owner()).isEqualTo(SOME_OWNER);
    }

    @Test
    void shouldReturnAccountBalance() {
        String id = givenAccount();

        AccountDto result = service.balance(id);

        assertThat(result.money()).isEqualTo("0");
        assertThat(result.owner()).isEqualTo(SOME_OWNER);
    }

    @Test
    void shouldDepositMoney() {
        String id = givenAccount();

        service.deposit(id, 1000);

        assertThat(aBalanceOf(id)).isEqualTo("1000");
    }

    @Test
    void shouldWithdrawMoney() {
        String id = givenAccountWithBalance(1000);

        service.withdraw(id, 400);

        assertThat(aBalanceOf(id)).isEqualTo("600");
    }

    @Test
    void shouldTransferMoney() {
        String from = givenAccountWithBalance(1000);
        String to = givenAccountWithBalance(400);

        service.transfer(from, to, 150);

        assertThat(aBalanceOf(from)).isEqualTo("850");
        assertThat(aBalanceOf(to)).isEqualTo("550");
    }

    private String givenAccountWithBalance(int balance) {
        String accountId = givenAccount();
        service.deposit(accountId, balance);

        return accountId;
    }

    private String aBalanceOf(String id) {
        return service.balance(id).money();
    }

    private String givenAccount() {
        return service.open(SOME_OWNER);
    }
}