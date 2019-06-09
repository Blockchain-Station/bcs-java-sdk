package com.enjin.enjincoin.sdk.model.service;

public class PaginationInput {

    private int page;
    private int limit;

    public PaginationInput(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public PaginationInput(int page) {
        this(page, 10);
    }

}
