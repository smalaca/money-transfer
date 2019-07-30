package com.smalaca.bank;

import com.smalaca.bank.server.BankApplicationServerFactory;

public class BankApplication {
    public static void main(String[] args) {
        try {
            new BankApplicationServerFactory().create().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
