package com.smalaca.bank.api.rest;

import com.smalaca.bank.server.BankApplicationServer;
import com.smalaca.bank.server.BankApplicationServerFactory;

class BankApplicationRunner {
    private final BankApplicationRunnable runnable = new BankApplicationRunnable();

    void start() {
        new Thread(runnable).start();
    }

    void stop() {
        runnable.stop();
    }

    private static class BankApplicationRunnable implements Runnable {
        private BankApplicationServer bankApplicationServer;

        @Override
        public void run() {
            try {
                bankApplicationServer = new BankApplicationServerFactory().create();
                bankApplicationServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void stop() {
            try {
                bankApplicationServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
