package com.smalaca.bank.server;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.server.ResourceConfig;

public class BankApplicationServerFactory {
    private static final int PORT = 8080;

    public BankApplicationServer create() {
        ResourceConfig resourceConfig = new BankApplicationConfigurationFactory().create();

        return new BankApplicationServer(new Server(PORT), resourceConfig);
    }
}
