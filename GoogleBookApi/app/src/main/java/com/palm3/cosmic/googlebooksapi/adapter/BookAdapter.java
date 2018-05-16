package com.palm3.cosmic.googlebooksapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.palm3.cosmic.googlebooksapi.R;
import com.palm3.cosmic.googlebooksapi.model.pojo.Item;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * リサイクルビュー用のアダプタークラス定義（書籍情報検索画面用）
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    /**
     * フィールド宣言
     */
    private List<Item> books;
    private int rowLayout;
    private Context context;

    /**
     * コンストラクタ
     * @param books     書籍情報（オブジェクト型）
     * @param rowLayout 行レイアウト情報（整数型）
     * @param context   コンテキスト情報（オブジェクト型）
     */
    public BookAdapter(List<Item> books, int rowLayout, Context context) {
        this.books = books;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    /**
     * ビューホルダー生成時
     * @param parent   リサイクルビュー情報（オブジェクト型）
     * @param viewType ビュータイプ（整数型）
     * @return ビューホルダー生成情報
     */
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // リサイクルビュー生成
        View view = LayoutInflater.from(
                parent.getContext()).inflate(rowLayout, parent, false);
        // ビューホルダー生成
        return new BookViewHolder(view);
    }

    /**
     * リサイクルビューアイテムにデータをバインドする
     * @param holder   ホルダー生成情報（オブジェクト型）
     * @param position アイテムインデックス情報（整数型）
     */
    @Override
    public void onBindViewHolder(BookViewHolder holder, final int position) {

        try {
            // バインド（書籍タイトル）
            holder.title.setText(books.get(position)
                    .getVolumeInfo().getTitle());
            // バインド（書籍タイトル）
            holder.author.setText(String.format("著者情報: %s", books.get(position)
                    .getVolumeInfo().getAuthors().get(0)));
            // バインド（書籍サムネイル画像）
            Glide.with(context).load(books.get(position)
                    .getVolumeInfo().getImageLinks().getThumbnail())
                    .into(holder.thumbnail);
            // レーティングスター数
            holder.rating.setRating(books.get(position).
                    getVolumeInfo().getAverageRating().floatValue());
        } catch (NullPointerException e) {
            //
            holder.rating.setRating(0);
        }
    }

    /**
     * 書籍情報検索結果件数を取得
     * @return 書籍情報検索結果件数（整数型）
     */
    @Override
    public int getItemCount() {
        return books.size();
    }

    /**
     * リサイクルビュー用のビューホルダークラス定義（インナークラス）
     */
    static class BookViewHolder extends RecyclerView.ViewHolder {

        /**
         * フィール宣言
         */
        TextView title;
        TextView author;
        ImageView thumbnail;
        RatingBar rating;

        /**
         * コンスタント
         * @param v ビュー情報
         */
        BookViewHolder(View v) {
            super(v);

            // レイアウト検索（書籍タイトルビュー）
            title = (TextView) v.findViewById(R.id.title);

            // レイアウト検索（書籍著者ビュー）
            author = (TextView) v.findViewById(R.id.author);

            // レイアウト検索（書籍サムネイル画像ビュー）
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

            // レイアウト検索（書籍レーティングビュー）
            rating = (RatingBar) v.findViewById(R.id.rating);
        }
    }
}

