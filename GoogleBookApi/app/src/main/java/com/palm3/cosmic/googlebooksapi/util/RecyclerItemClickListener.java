package com.palm3.cosmic.googlebooksapi.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * リサイクルビュー用アイテムクリックイベントリスナー定義
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    /**
     * フィールド
     */
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;


    public interface OnItemClickListener {

        /**
         * イベントリスナー（アイテムクリック）
         * @param view     ビュー情報
         * @param position インデックス
         */
        void onItemClick(View view, int position);

        /**
         * イベントリスナー（アイテム長押しクリック）
         * @param view     ビュー情報
         * @param position インデックス
         */
        void onLongItemClick(View view, int position);
    }

    /**
     * コンストラクタ
     * @param context      コンテキスト情報
     * @param recyclerView リサイクルビュー情報
     * @param listener     リスナー情報
     */
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {

        // リスナー情報
        mListener = listener;

        // ジェスチャー検出
        mGestureDetector =
                new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                    // 匿名関数
                    /**
                     * タップ時（シングル）
                     * @param e モーションイベント情報
                     * @return 論理値（デフォルトFALSE）
                     */
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    /**
                     * タップ時（長押し）
                     * @param e モーションイベント情報
                     */
                    @Override
                    public void onLongPress(MotionEvent e) {

                        // タップ領域のビューを取得
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                        // タップ情報 & リスナー情報チェック
                        if (child != null && mListener != null) {
                            // 長押しタップ検出
                            mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                        }
                    }
                });
    }

    /**
     * 親ビュー or 子ビューのタッチイベントを返すかを決める
     * @param view ビュー情報
     * @param e    エラー情報
     * @return true（親ビュー情報）or false（子ビュー情報）（論理型）
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {

        // タップ領域を取得
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        // タップ領域情報 & リスナー情報 & タッチ検出結果をチェック
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    /**
     * イベントの終了を行うか決定できる（使用しない）
     * @param view ビュー情報（オブジェクト型）
     * @param motionEvent モーションイベント情報（オブジェクト型）
     */
    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }


    /**
     * 親ビューや子ビューをタッチイベントを使わさたくないかを決める（使用してない）
     * @param disallowIntercept インターセプト値（trueで使わせない）
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}

