package com.smalaca.bank.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class BankApplicationServer {
    private final Server jettyServer;
    private final ResourceConfig resourceConfig;

    BankApplicationServer(Server jettyServer, ResourceConfig resourceConfig) {
        this.jettyServer = jettyServer;
        this.resourceConfig = resourceConfig;
    }

    public void start() throws Exception {
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(resourceConfig));
        ServletContextHandler context = new ServletContextHandler(jettyServer, "/");
        context.addServlet(jerseyServlet, "/*");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    public void stop() throws Exception {
        try {
            jettyServer.stop();
        } finally {
            jettyServer.destroy();
        }
    }
}
