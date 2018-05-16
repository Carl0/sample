package com.palm3.cosmic.googlebooksapi.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.palm3.cosmic.googlebooksapi.base.ui.BaseActivity;
import com.palm3.cosmic.googlebooksapi.base.ui.BasePresenter;

/**
 * MVPアクティビティ抽象クラス定義
 */
public abstract class BookActivityMVP<P extends BasePresenter> extends BaseActivity {

    /**
     * フィールド宣言
     */
    protected P presenter;

    /**
     * プレゼンター生成用
     * @return プレゼンター情報
     */
    protected abstract P createPresenter();

    /**
     * アクティビティ初期化時
     * @param savedInstanceState アプリ状態情報（オブジェクト型）
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // プレゼンター生成
        presenter = createPresenter();
    }

    /**
     * アクティビティ完全終了時（プレゼンターをデタッチ）
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (presenter != null) {
            // プレゼンターを取り除く
            presenter.detachView();
        }
    }
}


