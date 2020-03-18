package com.sample.droider.legacyrecipeapp.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class ApiManager {

    private static ApiManager apiManager;
    private final OkHttpClient httpClient;

    public ApiManager() {
        httpClient = HttpClient.getOkHttpClient();
    }

    static ApiManager getApiManager() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    /**
     * GET通信
     *
     * @param url      URL
     * @param callback {@link Callback}
     * @param context  Context
     */
    void executeGet(@NonNull HttpUrl url, @NonNull Context context, @NonNull RequestTag requestTag, @NonNull Callback callback) {

        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(requestTag);

        if (isConnectible(context)) {
            builder.cacheControl(new CacheControl.Builder().maxAge((int) HttpClient.getMaxAge(), TimeUnit.SECONDS).build());
        } else {
            // オフライン
            builder.cacheControl(new CacheControl.Builder().onlyIfCached().maxAge((int) HttpClient.getMaxAge(), TimeUnit.SECONDS).build());
        }

        builder.get();
        execute(builder.build(), callback);
    }

    /**
     * 指定されたTagの通信をキャンセルする
     *
     * @param tag {@link RequestTag}
     */
    void cancel(RequestTag tag) {
        for (Call call : httpClient.dispatcher().queuedCalls()) {
            if (Objects.equals(call.request().tag(), tag))
                call.cancel();
        }
        for (Call call : httpClient.dispatcher().runningCalls()) {
            if (Objects.equals(call.request().tag(), tag))
                call.cancel();
        }
    }

    /**
     * ネットワーク接続可能かチェック
     *
     * @param context Context
     * @return true：オンライン
     */
    private boolean isConnectible(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            return cm.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    /**
     * OkHttpを利用した通信の実処理.ワーカースレッドで非同期に行われる.
     *
     * @param request  {@link Request}
     * @param callback {@link Callback}
     */
    private void execute(final Request request, final Callback callback) {
        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // ステータスコードが200
                    callback.onSuccess(response);
                } else {
                    // ステータスコードが200以外
                    callback.onFailure(request, new IOException("HttpRequest is failed, "
                            + "status: " + response.code()
                            + ", body: " + response.body()));
                }
            }
        });
    }

    /**
     * OkHttpを利用した通信処理のコールバックをラップしてTaskに返すCallback
     */
    interface Callback {
        /**
         * 通信成功
         *
         * @param response {@link Response}
         */
        void onSuccess(Response response);

        /**
         * 通信失敗
         *
         * @param request {@link Request}
         * @param e       Exception
         */
        void onFailure(Request request, Exception e);
    }

}
