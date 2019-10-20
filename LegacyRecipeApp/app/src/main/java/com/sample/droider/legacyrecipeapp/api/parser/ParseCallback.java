package com.sample.droider.legacyrecipeapp.api.parser;

public interface ParseCallback<T> {
    T parse(String responseBody);
}