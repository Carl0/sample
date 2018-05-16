package com.palm3.cosmic.googlebooksapi.book.home;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.palm3.cosmic.googlebooksapi.R;
import com.palm3.cosmic.googlebooksapi.adapter.BookAdapter;
import com.palm3.cosmic.googlebooksapi.base.mvp.BookActivityMVP;
import com.palm3.cosmic.googlebooksapi.model.pojo.Books;
import com.palm3.cosmic.googlebooksapi.model.pojo.Item;

import com.palm3.cosmic.googlebooksapi.util.GridSpacingItemDecoration;
import com.palm3.cosmic.googlebooksapi.util.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * 書籍情報検索一覧画面用のアクティビティクラス定義
 */
public class HomeActivityMVP extends BookActivityMVP<HomePresenter>
        implements HomeView, SearchView.OnQueryTextListener {

    /**
     * フィールド宣言
     */
    // デフォルトクエリ値
    private String query = "Programming";

    private List<Item> list;

    // バインド処理
    @BindView(R.id.recycleView) RecyclerView recyclerView;
    @BindView(R.id.progress) ProgressBar progressBar;
    // @BindView(R.id.search_view) SearchView searchView;


    /**
     * プレゼンター生成実施
     * @return プレゼンター生成情報
     */
    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    /**
     * アクティビティ初期化時の登録
     * @param savedInstanceState アプリ状態情報（オブジェクト型）
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ホープ用アクティビティにホーム用ビューを配置
        setContentView(R.layout.activity_home);

        // レイアウトマネージャー指定
        recyclerView.setLayoutManager(
                new GridLayoutManager(this, 2));
        // アイテムビューデザイン指定（グリッド状）
        recyclerView.addItemDecoration(
                new GridSpacingItemDecoration(this, 2, 10, true));
        // アニメーション指定（デフォルト）
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // イベントリスナー指定（ビューアイテムタップ用）
        recyclerView.addOnItemTouchListener(selectItemOnRecyclerView());

        // 検索キーワードで検索開始
        presenter.loadData(query);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // インテントセット
        setIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // [NOTE]: searchViewに音声の結果を入力する
            // [NOTE]: 音声入力後、別のアクティビティへ飛ばす等の処理はここで書ける
            // // boolean submit = false;
            // // setQuery(query, submit);
            presenter.loadData(query);
        }
    }

    /**
     * リサイクルビューのビューアイテムのイベントリスナーを設定する
     * @return リサイクルビューイベントリスナー情報（オブジェクト型）
     */
    private RecyclerItemClickListener selectItemOnRecyclerView() {

        // ビューアイテムのイベントリスナー設定（リサイクルビュー）
        return new RecyclerItemClickListener(
                this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            // タップ時の処理
            @Override
            public void onItemClick(View view, int position) {
                // ビューアイテムを選択し、書籍詳細画面へ遷移する
                presenter.getItem(list.get(position), activity);
            }

            // 長押しタップ時の処理
            @Override
            public void onLongItemClick(View view, int position) {
                // ビューアイテムを選択し、書籍詳細画面へ遷移する
                presenter.getItem(list.get(position), activity);
            }
        });
    }

    /**
     * オプションメニュー生成
     * @param menu メニュー情報（オブジェクト型）
     * @return オプションメニュー生成情報（オブジェクト型）
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // 検索ビューレイアウトを生成
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // メニューから検索用のアクションアイテムを検索
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();



        // 検索機能を有効化
        setupSearchView(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * アクションバーに検索機能を設定する（プライベート関数）
     * @param searchView 検索ビュー情報（オブジェクト型）
     */
    private void setupSearchView(SearchView searchView) {

        // デフォルトアイコン指定
        searchView.setIconifiedByDefault(true);

        // クエリヒント
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        // 入力候補を表示する
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            List<SearchableInfo> searchableInfo = searchManager.getSearchablesInGlobalSearch();
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchableInfo) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            searchView.setSearchableInfo(info);
        }
        searchView.setOnQueryTextListener(this);
    }

    /**
     * ローディング状況（表示）
     */
    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * ローディング状況（非表示）
     */
    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 書籍情報の取得成功時の処理
     * @param books 書籍情報の検索結果（オブジェクト型）
     */
    @Override
    public void getDataSuccess(Books books) {

        // 書籍情報を取得
        this.list = books.getItems();

        // 書籍情報をリサイクルビューのアダプターにセット
        recyclerView.setAdapter(new BookAdapter(list, R.layout.item, getApplicationContext()));
    }

    /**
     * 書籍情報の取得失敗時の処理
     * @param message モデル情報（オブジェクト型）
     */
    @Override
    public void getDataFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 書籍詳細情報アクティビティへ移行
     * @param intent インテント情報（オブジェクト型）
     */
    @Override
    public void moveToDetail(Intent intent) {
        // 移行開始
        startActivity(intent);
    }

    /**
     * 書籍情報のリフレッシュ処理
     * @param view ビュー情報（オブジェクト型）
     */
    public void refresh(View view) {
        // 書籍情報ロード処理実施
        presenter.loadData(query);
    }

    /**
     * 書籍情報を取得する
     * @param query クエリパラメータ情報（文字列型）
     */
    private void getData(String query) {
        if (query != null) {
            this.query = query;
            // クエリパラメータをもとに書籍情報のロード処理開始
            presenter.loadData(query);
        }
    }

    /**
     * 検索ボタン押下時にコールされる
     * @param query 検索キーワード（文字列）
     * @return 論理値
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        // 書籍情報ロード処理
        getData(query);
        return false;
    }

    /**
     * 検索テキスト内容が変更される度にコールされる
     * @param newText 変更された検索キーワード（文字列型）
     * @return 論理値
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        presenter.stop();
        getData(newText);
        return false;
    }
}
