package com.smalaca.bank.api.rest;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.smalaca.bank.domain.account.AccountDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static java.lang.String.format;

class BankApplicationHttpClient {
    private static final String URL = "http://localhost:8080/account";
    private static final String URL_BALANCE_TEMPLATE = URL + "/%s";
    private static final String URL_TRANSFER_TEMPLATE = URL + "/%s/transfer";
    private static final String URL_DEPOSIT_TEMPLATE = URL + "/%s/deposit";

    private static final String PARAMETER_AMOUNT = "amount";
    private static final String PARAMETER_TO = "to";
    private static final String PARAMETER_OWNER = "owner";

    private final HttpClient httpClient;
    private final Gson gson;

    private BankApplicationHttpClient(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    static BankApplicationHttpClient create() {
        return new BankApplicationHttpClient(HttpClientBuilder.create().build(), new Gson());
    }

    String openAccount(String owner) {
        List<NameValuePair> parameters = ImmutableList.of(
                parameter(PARAMETER_OWNER, owner)
        );

        HttpPut httpPut = new HttpPut(URL);
        httpPut.setEntity(httpEntity(parameters));

        return execute(httpPut);
    }

    void deposit(String accountId, int balance) {
        List<NameValuePair> parameters = ImmutableList.of(
                parameter(PARAMETER_AMOUNT, String.valueOf(balance))
        );

        execute(httpPost(format(URL_DEPOSIT_TEMPLATE, accountId), parameters));
    }

    void transfer(String from, String to, int amount) {
        List<NameValuePair> parameters = ImmutableList.of(
                parameter(PARAMETER_TO, to),
                parameter(PARAMETER_AMOUNT, String.valueOf(amount))
        );

        execute(httpPost(format(URL_TRANSFER_TEMPLATE, from), parameters));
    }

    private HttpPost httpPost(String uri, List<NameValuePair> parameters) {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(httpEntity(parameters));
        return httpPost;
    }

    private BasicNameValuePair parameter(String name, String value) {
        return new BasicNameValuePair(name, value);
    }

    private HttpEntity httpEntity(List<NameValuePair> parameters) {
        try {
            return new UrlEncodedFormEntity(parameters, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    AccountDto balance(String accountId) {
        String result = execute(new HttpGet(format(URL_BALANCE_TEMPLATE, accountId)));

        return gson.fromJson(result, AccountDto.class);
    }

    private String execute(HttpUriRequest httpGet) {
        try {
            HttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
