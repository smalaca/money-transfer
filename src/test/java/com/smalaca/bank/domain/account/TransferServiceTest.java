package com.smalaca.bank.domain.account;

import com.smalaca.bank.domain.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceTest {
    private static final String SOME_OWNER = "Peter Parker";

    private final TransferService service = new TransferService();

    @Test
    void shouldTransferMoney() {
        Account from = givenAccountWith(1000);
        Account to = givenAccountWith(500);

        service.transfer(from, to, Money.from(100));

        assertBalance(from, "900");
        assertBalance(to, "600");
    }

    @Test
    void shouldRecognizeNotEnoughMoneyToTransfer() {
        Account from = givenAccountWith(100);
        Account to = givenAccountWith(500);

        Executable transfer = () -> service.transfer(from, to, Money.from(1000));

        AccountBalanceException exception = assertThrows(AccountBalanceException.class, transfer);
        assertThat(exception.getMessage()).isEqualTo("Cannot withdraw: 1000 from " + SOME_OWNER + " account.");
        assertBalance(from, "100");
        assertBalance(to, "500");
    }

    private void assertBalance(Account account, String balance) {
        assertThat(account.dto().money()).isEqualTo(balance);
    }

    private Account givenAccountWith(int money) {
        Account account = Account.open(SOME_OWNER);
        account.deposit(Money.from(money));
        return account;
    }
}