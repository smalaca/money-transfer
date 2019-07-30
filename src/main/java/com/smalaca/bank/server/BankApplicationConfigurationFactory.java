package com.smalaca.bank.server;

import com.smalaca.bank.application.AccountApplicationService;
import com.smalaca.bank.application.AccountApplicationServiceFactory;
import com.smalaca.bank.persistance.inmemory.InMemoryAccountRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

class BankApplicationConfigurationFactory {
    private static final String API_REST_PACKAGE = "com.smalaca.bank.api.rest";

    ResourceConfig create() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(API_REST_PACKAGE);

        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new AccountApplicationServiceFactory().create(new InMemoryAccountRepository())).to(AccountApplicationService.class);
            }
        });

        return resourceConfig;
    }
}
