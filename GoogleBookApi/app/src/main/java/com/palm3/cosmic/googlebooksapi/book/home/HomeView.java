package com.palm3.cosmic.googlebooksapi.book.home;

import android.content.Intent;

import com.palm3.cosmic.googlebooksapi.model.pojo.Books;

/**
 * メイン画面の基本機能のインターフェース定義
 */
interface HomeView {

    /**
     * ローディング（表示）
     */
    void showLoading();

    /**
     * ローディング（非表示）
     */
    void hideLoading();

    /**
     * データ取得（成功）
     * @param model モデル情報（オブジェクト型）
     */
    void getDataSuccess(Books model);

    /**
     * データ取得（失敗）
     * @param message 出力メッセージ情報（オブジェクト型）
     */
    void getDataFail(String message);

    /**
     * 詳細画面遷移
     * @param intent インテント情報（オブジェクト型）
     */
    void moveToDetail(Intent intent);
}
