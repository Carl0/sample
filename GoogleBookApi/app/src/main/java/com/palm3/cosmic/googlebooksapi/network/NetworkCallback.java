package com.palm3.cosmic.googlebooksapi.network;

import android.util.Log;

import retrofit2.HttpException;

import rx.Subscriber;

/**
 * ネットワークコールバック処理の抽象クラス定義
 * 使用箇所（メイン画面用プレゼンター、詳細画面用プレゼンター）
 */
public abstract class NetworkCallback<M> extends Subscriber<M> {

    /**
     * フィールド宣言
     */
    // 定数
    private static final String TAG = NetworkCallback.class.getName();
    // 抽象
    public abstract void onSuccess(M model);
    public abstract void onFailure(String message);
    public abstract void onFinish();

    /**
     * Rx購読処理のエラーハンドリング
     * @param e エラー情報（オブジェクト型）
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            // キャスト
            HttpException he = (HttpException) e;
            // エラー時のステータスコードを取得
            int statusCode = he.code();
            // エラーメッセージ取得
            String message = he.getMessage();
            // ステータスコードデバッグ出力
            Log.i(TAG, "code : " + statusCode);
            onFailure(message);
        } else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    /**
     * 正常処理時
     * @param model モデル情報
     */
    @Override
    public void onNext(M model) {
        onSuccess(model);
    }

    /**
     * 正常完了時
     */
    @Override
    public void onCompleted() {
        onFinish();
    }
}
