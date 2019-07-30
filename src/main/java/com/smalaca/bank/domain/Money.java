package com.smalaca.bank.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal amount;

    public static Money from(double amount) {
        return new Money(new BigDecimal(amount));
    }

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return amount.equals(money1.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public Money substract(Money amount) {
        BigDecimal result = this.amount.subtract(amount.amount);
        return new Money(result);
    }

    public Money add(Money amount) {
        BigDecimal result = this.amount.add(amount.amount);
        return new Money(result);
    }

    public boolean isNotLowerThan(Money amount) {
        return this.amount.compareTo(amount.amount) != -1;
    }

    public String value() {
        return amount.toString();
    }
}
