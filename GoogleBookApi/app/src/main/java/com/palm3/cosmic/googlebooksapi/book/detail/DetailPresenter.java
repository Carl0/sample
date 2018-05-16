package com.palm3.cosmic.googlebooksapi.book.detail;

import com.palm3.cosmic.googlebooksapi.base.ui.BasePresenter;
import com.palm3.cosmic.googlebooksapi.model.pojo.Item;
import com.palm3.cosmic.googlebooksapi.network.NetworkCallback;

/**
 * 書籍情報検索画面用のプレゼンタークラス定義
 */
public class DetailPresenter extends BasePresenter<DetailView> {

    /**
     * コンストラクタ
     * @param view ビュー情報（オブジェクト型）
     */
    DetailPresenter(DetailView view) {
        // ベースプレゼンター側によるビューのアタッチ処理
        super.attachView(view);
    }

    /**
     * 書籍情報ロード処理
     * @param id 検索ID（整数型）
     */
    void loadData(String id) {

        // ローディングを表示
        view.showLoading();

        // 購読追加（ベースプレゼンター側によるHTTPリクエスト処理開始）
        addSubscribe(apiStores.getDetailBook(id), new NetworkCallback<Item>() {
            @Override
            public void onSuccess(Item model) {
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

}
