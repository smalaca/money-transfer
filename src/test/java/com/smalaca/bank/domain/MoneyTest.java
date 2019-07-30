package com.smalaca.bank.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void shouldPresentValueOfZero() {
        String result = Money.ZERO.value();

        assertThat(result).isEqualTo("0");
    }

    @Test
    void shouldAddMoney() {
        Money money = Money.from(100);
        Money toAdd = Money.from(200);

        Money result = money.add(toAdd);

        assertThat(result.value()).isEqualTo("300");
        assertThat(result).isNotSameAs(money);
    }

    @Test
    void shouldSubstractMoney() {
        Money money = Money.from(300);
        Money toSubstract = Money.from(200);

        Money result = money.substract(toSubstract);

        assertThat(result.value()).isEqualTo("100");
        assertThat(result).isNotSameAs(money);
    }

    @Test
    void shouldRecognizeLowerValue() {
        Money money = Money.from(300);
        Money toCompare = Money.from(200);

        boolean result = money.isNotLowerThan(toCompare);

        assertThat(result).isTrue();
    }

    @Test
    void shouldRecognizeTheSameValueIsNotLower() {
        int amount = 300;
        Money money = Money.from(amount);
        Money toCompare = Money.from(amount);

        boolean result = money.isNotLowerThan(toCompare);

        assertThat(result).isTrue();
    }

    @Test
    void shouldRecognizeGreaterValue() {
        Money money = Money.from(200);
        Money toCompare = Money.from(300);

        boolean result = money.isNotLowerThan(toCompare);

        assertThat(result).isFalse();
    }
}