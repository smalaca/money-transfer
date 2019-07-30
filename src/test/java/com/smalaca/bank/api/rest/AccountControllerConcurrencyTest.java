package com.smalaca.bank.api.rest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.StrictAssertions.assertThat;

class AccountControllerConcurrencyTest {
    private final BankApplicationHttpClient bankApplicationHttpClient = BankApplicationHttpClient.create();
    private final BankApplicationRunner bankApplicationRunner = new BankApplicationRunner();
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        bankApplicationRunner.start();
    }

    @AfterEach
    void tearDown() {
        bankApplicationRunner.stop();
    }

    @Test
    void shouldCompleteAllTransfers() throws InterruptedException {
        List<String> ids = ImmutableList.of(
                givenAccount("Tony Stark", 20000),
                givenAccount("Peter Parker", 9000),
                givenAccount("Steve Rogers", 13000),
                givenAccount("Mary Jane Watson", 25000),
                givenAccount("Bruce Banner", 12500),
                givenAccount("Nick Fury", 10000),
                givenAccount("Natasha Romanoff", 10000),
                givenAccount("Clint Barton", 15000),
                givenAccount("Wanda Maximoff", 7500),
                givenAccount("James Howlett", 17000),
                givenAccount("Charles Xavier", 11000)
        );


        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            String id1 = randomId(ids);
            String id2 = randomIdDifferentThan(id1, ids);

            executor.execute(randomTransfer(id1, id2));
        }

        executor.awaitTermination(5L, TimeUnit.SECONDS);

        assertThat(sumOfBalances(ids)).isEqualTo(150000);
    }

    private Runnable randomTransfer(String id1, String id2) {
        return () -> bankApplicationHttpClient.transfer(id1, id2, random.nextInt(50));
    }

    private String randomId(List<String> ids) {
        return ids.get(random.nextInt(ids.size()));
    }

    private String randomIdDifferentThan(String id, List<String> ids) {
        String otherId = randomId(ids);

        while (id.equals(otherId)) {
            otherId = randomId(ids);
        }

        return otherId;
    }

    private int sumOfBalances(List<String> ids) {
        return ids.stream()
                .map(id -> bankApplicationHttpClient.balance(id).money())
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private String givenAccount(String owner, int balance) {
        String accountId = bankApplicationHttpClient.openAccount(owner);
        bankApplicationHttpClient.deposit(accountId, balance);
        return accountId;
    }
}