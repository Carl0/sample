package com.palm3.cosmic.googlebooksapi.network;

import com.palm3.cosmic.googlebooksapi.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ネットワーククライントクラス定義
 */
public class NetworkClient {

    /**
     * フィールド定義
     */
    private static Retrofit retrofit;

    /**
     * クライント生成
     * @return クライント情報（オブジェクト型）
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {

            // クライアント生成
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            // クライント設定
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        // 生成結果
        return retrofit;
    }
}
