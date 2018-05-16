package com.palm3.cosmic.googlebooksapi.base.ui;

import android.util.Log;

import com.palm3.cosmic.googlebooksapi.network.NetworkClient;
import com.palm3.cosmic.googlebooksapi.network.NetworkService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * ベースプレゼンタークラス定義
 */
public class BasePresenter<V> {

    /**
     * フィールド宣言
     */
    public V view;
    protected NetworkService apiStores;
    private CompositeSubscription compositeSubscription;
    private Subscriber subscriber;

    /**
     * UIにフラグメントを取り付ける
     * @param view ビュー情報（オブジェクト型）
     */
    protected void attachView(V view) {

        // ビューにセット
        this.view = view;

        // 書籍情報API機能を生成
        apiStores = NetworkClient.getRetrofit().create(NetworkService.class);
    }

    /**
     * UIからフラグメントを取り外す
     */
    public void detachView() {
        this.view = null;
        onUnsubscribe();
    }

    /**
     * 購読解除時
     */
    private void onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
            Log.e("TAG", "onUnsubscribe: ");
        }
    }

    /**
     * 購読追加
     * @param observable オブザーバブル情報（オブジェクト型）
     * @param subscriber サブスクライバー情報（オブジェクト型）
     */
    protected void addSubscribe(Observable observable, Subscriber subscriber) {
        this.subscriber = subscriber;
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    /**
     * 購読解除
     */
    public void stop() {
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
    }
}
