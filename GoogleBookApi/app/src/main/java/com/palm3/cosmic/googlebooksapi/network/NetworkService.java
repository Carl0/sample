package com.palm3.cosmic.googlebooksapi.network;

import com.palm3.cosmic.googlebooksapi.model.pojo.Books;
import com.palm3.cosmic.googlebooksapi.model.pojo.Item;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 書籍情報キーワード検索API
 */
public interface NetworkService {

    /**
     * 書籍情報キーワード検索リソース
     */
    @GET("volumes")
    Observable<Books> getTopBooks(@Query("q") String key);

    /**
     * 書籍情報ID検索リソース
     */
    @GET("volumes/{id}")
    Observable<Item> getDetailBook(@Path("id") String id);

    /**
     * 書籍タイトル検索リソース
     */
    @GET("volumes")
    Observable<Item> getTitleBook(@Query("q=title:") String author);

    /**
     * 書籍著者検索リソース
     */
    @GET("volumes")
    Observable<Item> getAuthorBook(@Query("q=inauthor:") String author);
}

