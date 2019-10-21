package com.sample.droider.legacyrecipeapp.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

import com.sample.droider.legacyrecipeapp.api.request.Request;

import java.io.IOException;

import okhttp3.Response;

public class ApiRequestTask<T> {

    /** APIリクエスト */
    private Request<T> request;
    /** 通信の実処理を行うAPIManager */
    private final ApiManager apiManager;
    /** UIスレッドへのコールバック */
    private CallbackToMainThread<T> callbackToMainThreadWeakReference;
    /** UIスレッドへポストするためのHandler */
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ApiRequestTask(Request<T> request, CallbackToMainThread<T> callbackToMainThread) {
        this.request = request;
        apiManager = ApiManager.getApiManager();
        this.callbackToMainThreadWeakReference = callbackToMainThread;
    }

    /**
     * 通信開始
     *
     * @param context Context
     * @return ApiRequestTask
     */
    public ApiRequestTask<T> execute(Context context) {
        if (HttpMethod.GET == request.getHttpMethod()) {
            executeGet(context);
        }
        return this;
    }

    /**
     * キャンセル
     */
    public void cancel() {
        apiManager.cancel(request.getRequestTag());
        callbackToMainThreadWeakReference.onCancel();
        callbackToMainThreadWeakReference = null;
    }

    /**
     * GET通信
     *
     * @param context Context
     */
    private void executeGet(@NonNull Context context) {
        apiManager.executeGet(request.getUrl(), context, request.getRequestTag(), new ApiManager.Callback() {
            @Override
            public void onSuccess(final Response response) {

                try {
                    // JSONをパース
                    final T result = request.getParser().parse(response.body().string());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callbackToMainThreadWeakReference != null) {
                                callbackToMainThreadWeakReference.onSuccess(result);
                            }
                        }
                    });
                } catch (NullPointerException | IOException e) {
                    onFailure(response.request(), e);
                }
            }

            @Override
            public void onFailure(okhttp3.Request request, Exception e) {
                if (callbackToMainThreadWeakReference != null) {
                    callbackToMainThreadWeakReference.onError(ErrorCode.NW_ERROR, e.getMessage(), e);
                }
            }
        });
    }

    /**
     * UIスレッドへのコールバック
     *
     * @param <T> 通信結果
     */
    public interface CallbackToMainThread<T> {
        void onSuccess(T result);

        void onError(ErrorCode errorCode, String errorMessage, Exception e);

        void onCancel();
    }
}