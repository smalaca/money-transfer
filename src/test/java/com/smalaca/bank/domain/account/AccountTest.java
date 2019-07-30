package com.smalaca.bank.domain.account;

import com.smalaca.bank.domain.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {
    private static final String SOME_OWNER = "Peter Parker";
    private static final String NO_MONEY = "0";

    @Test
    void shouldReturnAccountDto() {
        Account account = givenNewAccount();

        AccountDto result = account.dto();

        assertThat(result.owner()).isEqualTo(SOME_OWNER);
        assertBalance(result, NO_MONEY);
    }

    @Test
    void shouldDepositMoney() {
        Account account = givenNewAccount();

        account.deposit(aMoney(100));

        assertBalance(account.dto(), "100");
    }

    @Test
    void shouldWithdrawMoney() {
        Account account = givenAccountWith(100);

        account.withdraw(aMoney(60));

        assertBalance(account.dto(), "40");
    }

    @Test
    void shouldThrowExceptionWhenWithdrawalIsMoreThanBalance() {
        Account account = givenAccountWith(100);

        Executable withdrawal = () -> account.withdraw(aMoney(1000));

        AccountBalanceException exception = assertThrows(AccountBalanceException.class, withdrawal);
        assertThat(exception.getMessage()).isEqualTo("Cannot withdraw: 1000 from " + SOME_OWNER + " account.");
        assertBalance(account.dto(), "100");
    }

    @Test
    void shouldRecognizeWhenHasGotEnoughMoneyToWithdraw() {
        Account account = givenAccountWith(100);

        boolean result = account.hasEnoughToWithdraw(aMoney(50));

        assertThat(result).isTrue();
    }

    @Test
    void shouldRecognizeThatCanWithdrawAllMoney() {
        int balance = 100;
        Account account = givenAccountWith(balance);

        boolean result = account.hasEnoughToWithdraw(aMoney(balance));

        assertThat(result).isTrue();
    }

    @Test
    void shouldRecognizeWhenDoesNotHaveGotEnoughMoneyToWithdraw() {
        Account account = givenAccountWith(100);

        boolean result = account.hasEnoughToWithdraw(aMoney(1000));

        assertThat(result).isFalse();
    }

    private void assertBalance(AccountDto result, String expected) {
        assertThat(result.money()).isEqualTo(expected);
    }

    private Account givenAccountWith(int money) {
        Account account = givenNewAccount();
        account.deposit(aMoney(money));
        return account;
    }

    private Money aMoney(int money) {
        return Money.from(money);
    }

    private Account givenNewAccount() {
        return Account.open(SOME_OWNER);
    }
}