package com.palm3.cosmic.googlebooksapi.book.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.palm3.cosmic.googlebooksapi.R;
import com.palm3.cosmic.googlebooksapi.base.mvp.BookActivityMVP;
import com.palm3.cosmic.googlebooksapi.model.pojo.Item;
import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 * 書籍情報詳細画面用のアクティビティクラス定義
 */
public class DetailActivityMVP extends BookActivityMVP<DetailPresenter> implements DetailView, View.OnClickListener {

    /**
     * フィールド宣言
     */
    private String bookId;

    // バインド処理
    @BindView(R.id.description) TextView description;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.buttonRefresh) FloatingActionButton buttonRefresh;
    @BindView(R.id.descriptionLayout) CardView descriptionLayout;

    /**
     * プレゼンター生成実施
     * @return プレゼンター生成情報
     */
    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        buttonRefresh.setOnClickListener(this);
        bookId = getIntent().getStringExtra("id");
        presenter.loadData(bookId);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        descriptionLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        descriptionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void getDataSuccess(Item item) {
        description.setText(Html.fromHtml(item.getVolumeInfo().getDescription()));
        Glide.with(this).load(item.getVolumeInfo().getImageLinks().getThumbnail()).into(image);
        collapsingToolbarLayout.setTitle(item.getVolumeInfo().getTitle());
    }

    @Override
    public void getDataFail(String message) {
        Toast.makeText(this, "Please load again",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshData() {
        presenter.loadData(bookId);
    }

    @Override
    public void onClick(View view) {
        refreshData();
    }
}
