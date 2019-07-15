package com.smalaca;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTransferAppTest {
    @Test
    void shouldAnswerWithTrue() {
        MoneyTransferApp moneyTransferApp = new MoneyTransferApp();

        assertThat(moneyTransferApp.name()).isEqualTo("Money Transfer App");
    }
}
