package com.smalaca;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTransferAppTest {
    @Test
    void shouldAnswerWithTrue() {
        String name = "Money Transfer App";
        MoneyTransferApp moneyTransferApp = new MoneyTransferApp(name);

        assertThat(moneyTransferApp.name()).isEqualTo(name);
    }
}
