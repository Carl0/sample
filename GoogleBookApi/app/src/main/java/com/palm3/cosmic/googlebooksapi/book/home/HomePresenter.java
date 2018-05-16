package com.palm3.cosmic.googlebooksapi.book.home;

import android.app.Activity;
import android.content.Intent;

import com.palm3.cosmic.googlebooksapi.base.ui.BasePresenter;
import com.palm3.cosmic.googlebooksapi.book.detail.DetailActivityMVP;
import com.palm3.cosmic.googlebooksapi.model.pojo.Books;
import com.palm3.cosmic.googlebooksapi.model.pojo.Item;
import com.palm3.cosmic.googlebooksapi.network.NetworkCallback;

/**
 * 書籍検索一覧画面用のプレゼンタークラス定義
 */
class HomePresenter extends BasePresenter<HomeView> {

    // private String key;

    /**
     * コンストラクタ
     * @param view ビュー情報（オブジェクト型）
     */
    HomePresenter(HomeView view) {
        // ベースプレゼンター側によるビューのアタッチ処理
        super.attachView(view);
    }

    /**
     * 書籍情報ロード処理
     * @param key 検索キーワード（文字列）
     */
    void loadData(String key) {

        // ローディングを表示
        view.showLoading();

        // 購読追加（ベースプレゼンター側によるHTTPリクエスト処理開始）
        addSubscribe(apiStores.getTopBooks(key), new NetworkCallback<Books>() {
            @Override
            public void onSuccess(Books model) {
                view.getDataSuccess(model);
            }

            @Override
            public void onFailure(String message) {
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    /**
     * 書籍アイテムビュー情報を取得し、書籍詳細画面へ遷移
     * @param item     アイテム情報（オブジェクト型）
     * @param activity 書籍詳細情報画面用の起動対象アクティビティ情報（オブジェクト型）
     */
    void getItem(Item item, Activity activity) {

        // 起動対象アクティビティ生成
        Intent intent = new Intent(activity, DetailActivityMVP.class);
        // 呼び出し先アクティビティへデータを渡す
        intent.putExtra("id", item.getId());

        // 書籍検索詳細画面へ遷移
        view.moveToDetail(intent);
    }
}
