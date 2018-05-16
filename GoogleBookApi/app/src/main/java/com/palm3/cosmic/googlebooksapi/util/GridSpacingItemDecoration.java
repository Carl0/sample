package com.palm3.cosmic.googlebooksapi.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * リサイクルビューデコラータクラス定義
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * フィールド宣言
     */
    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private Context context;

    /**
     * コンストラクタ
     * @param context コンテキスト
     * @param spanCount スパン数
     * @param spacing スペース
     * @param includeEdge 枠
     */
    public GridSpacingItemDecoration(Context context, int spanCount, int spacing, boolean includeEdge) {
        this.context = context;
        this.spanCount = spanCount;
        this.spacing = dpToPx(spacing);
        this.includeEdge = includeEdge;
    }

    /**
     * アイテムインデックス情報取得
     * @param outRect 縁
     * @param view    ビュー情報
     * @param parent  ビュー情報
     * @param state   ビューステータス情報
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // インデックス情報
        int position = parent.getChildAdapterPosition(view);

        // カラム情報
        int column = position % spanCount;

        // 縁がある場合
        if (includeEdge) {
            // 左の差異
            outRect.left = spacing - getDistanceLeft(column);
            // 右の差異
            outRect.right = getDistanceRight(column);
            // インデックスがスパン以下なら
            if (position < spanCount) {
                // 上
                outRect.top = spacing;
            }
            // 下
            outRect.bottom = spacing;
        } else {
            // 縁がない場合
            // 左の差異
            outRect.left = getDistanceLeft(column);
            // 右の差異
            outRect.right = spacing - getDistanceRight(column);
            // インデクスがスパン以上なら
            if (position >= spanCount) {
                outRect.top = spacing;
            }
        }
    }

    /**
     * ユーティリティ関数 左の差異を返却する
     * @param column カラム数
     * @return 差異情報
     */
    private int getDistanceLeft(int column) {
        return column * spacing / spanCount;
    }

    /**
     * ユーティリティ関数 右の差異を返却する
     * @param column カラム数
     * @return 差異情報
     */
    private int getDistanceRight(int column) {
        return (column + 1) * spacing / spanCount;
    }

    /**
     * ユーティリティ関数 上の差異を返却する
     * @param dp カラム数
     * @return 差異情報
     */
    private int dpToPx(int dp) {
        // リソース情報取得
        Resources r = context.getResources();
        return Math.round(
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
