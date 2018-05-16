package com.palm3.cosmic.googlebooksapi.base.ui;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

// import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import rx.subscriptions.CompositeSubscription;

/**
 * ベースアクティビティクラス定義
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * フィールド宣言
     */
    public Activity activity;
    CompositeSubscription compositeSubscription;
    List<Call> calls;

    /**
     * アクティビティにビューを配置
     * @param layoutResID レイアウト
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        // アクティビティ保持
        activity = this;
        // バターナイフ適用
        ButterKnife.bind(this);
    }

    /**
     * アクティビティ完全終了時
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // HTTPリクエスト解除実行
        onCancelled();
        // Rx購読解除実行
        onUnsubscribe();
    }

    /**
     * HTTPリクエストキャンセル（プライベート関数）
     */
    private void onCancelled() {
        //  リクエスト状況をチェック
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled()) {
                    // キャンセル実行
                    call.cancel();
                }
            }
        }
    }

    /**
     * 購読解除（プライベート関数）
     */
    private void onUnsubscribe() {
        // 購読状況をチェック
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            // 購読解除実行
            compositeSubscription.unsubscribe();
        }
    }
}
