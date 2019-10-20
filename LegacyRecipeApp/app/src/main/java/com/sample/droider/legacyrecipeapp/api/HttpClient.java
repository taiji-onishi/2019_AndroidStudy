package com.sample.droider.legacyrecipeapp.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpClient {

    private static OkHttpClient okHttpClient;
    private static final long TIMEOUT = 30L;
    private static final long MAX_AGE = TimeUnit.HOURS.toSeconds(1L);

    private HttpClient() {
    }

    /**
     * OkHttpClientを返す.ここでタイムアウトやキャッシュ先などの設定を行う.
     *
     * @return OkHttpClient
     */
    static synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            // タイムアウト時間の設定
            builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    static long getMaxAge() {
        return MAX_AGE;
    }
}
