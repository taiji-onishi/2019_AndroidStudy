package com.sample.droider.legacyrecipeapp.api.request;

import android.support.annotation.NonNull;

import com.sample.droider.legacyrecipeapp.api.HttpMethod;
import com.sample.droider.legacyrecipeapp.api.RequestTag;
import com.sample.droider.legacyrecipeapp.api.parser.ParseCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

public class Request<T> {

    private String scheme;
    private String authority;
    private String path;
    private HttpMethod httpMethod;
    private RequestTag requestTag;
    private Map<String, String> params;
    private ParseCallback<T> parser;

    Request() {}

    private Request(@NonNull String scheme, @NonNull String authority, @NonNull String path,
                    @NonNull HttpMethod httpMethod,@NonNull Map<String, String> params,
                    @NonNull ParseCallback<T> parser) {
        this.scheme = scheme;
        this.authority = authority;
        this.path = path;
        this.httpMethod = httpMethod;
        this.params = params;
        this.parser = parser;
    }

    static class Builder<T> {
        private String path;
        private HttpMethod httpMethod;
        private Map<String, String> params;
        private ParseCallback<T> parser;

        Builder<T> path(@NonNull String path) {
            this.path = path;
            return this;
        }

        Builder<T> httpMethod(@NonNull HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        Builder<T> params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        Builder<T> parser(@NonNull ParseCallback<T> parser) {
            this.parser = parser;
            return this;
        }

        Request<T> build() {
            if (params == null) {
                this.params = new HashMap<>();
            }
            return new Request<>("http", "10.0.2.2", path, httpMethod, params, parser);
        }
    }

    /**
     * リクエスト先のURLを生成する
     */
    public HttpUrl getUrl() {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
        urlBuilder.scheme(scheme);
        urlBuilder.host(authority);
        urlBuilder.port(3000);
        urlBuilder.addPathSegment(path);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return urlBuilder.build();
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public ParseCallback<T> getParser() {
        return parser;
    }

    public RequestTag getRequestTag() {
        if (requestTag == null) {
            requestTag = new RequestTag(getUrl().url().toString());
        }
        return requestTag;
    }
}
