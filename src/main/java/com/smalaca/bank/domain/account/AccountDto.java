package com.smalaca.bank.domain.account;

public class AccountDto {
    private final String owner;
    private final String money;

    AccountDto(String owner, String money) {
        this.owner = owner;
        this.money = money;
    }

    public String owner() {
        return owner;
    }

    public String money() {
        return money;
    }
}
