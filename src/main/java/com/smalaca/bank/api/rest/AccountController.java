package com.smalaca.bank.api.rest;

import com.google.gson.Gson;
import com.smalaca.bank.application.AccountApplicationService;
import com.smalaca.bank.domain.account.AccountDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountController {
    private final AccountApplicationService accountApplicationService;
    private final Gson gson = new Gson();

    @Inject
    public AccountController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @PUT
    public Response open(@FormParam("owner") String owner) {
        String id = accountApplicationService.open(owner);

        return Response.status(200).entity(id).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response balance(@PathParam("id") String id) {
        AccountDto dto = accountApplicationService.balance(id);

        return Response.status(200).entity(gson.toJson(dto)).build();
    }

    @POST
    @Path("/{id}/deposit")
    public Response deposit(@PathParam("id") String id, @FormParam("amount") double amount) {
        accountApplicationService.deposit(id, amount);
        return Response.status(200).entity(id).build();
    }

    @POST
    @Path("/{id}/withdraw")
    public Response withdraw(@PathParam("id") String id, @FormParam("amount") double amount) {
        accountApplicationService.withdraw(id, amount);
        return Response.status(200).entity(id).build();
    }

    @POST
    @Path("/{id}/transfer")
    public Response transfer(@PathParam("id") String from, @FormParam("to") String to, @FormParam("amount") double amount) {
        accountApplicationService.transfer(from, to, amount);
        return Response.status(200).entity(from).build();
    }
}
