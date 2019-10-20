package com.sample.droider.legacyrecipeapp.api;

public enum ErrorCode {
    NW_ERROR(404),
    PARSE_ERROR(700);

    private int errorCd;

    ErrorCode(int errorCd) {
        this.errorCd = errorCd;
    }
}