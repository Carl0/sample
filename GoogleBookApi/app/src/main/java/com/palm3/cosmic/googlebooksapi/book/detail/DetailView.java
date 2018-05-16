package com.palm3.cosmic.googlebooksapi.book.detail;

import com.palm3.cosmic.googlebooksapi.model.pojo.Item;

/**
 * 書籍検索詳細画面の基本機能のインターフェース定義
 */
interface DetailView {

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
     * @param item アイテム情報（オブジェクト型）
     */
    void getDataSuccess(Item item);

    /**
     * データ取得（失敗）
     * @param message 出力メッセージ情報（オブジェクト型）
     */
    void getDataFail(String message);

    /**
     * リフレッシュ処理
     */
    void refreshData();
}
