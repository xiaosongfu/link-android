package cn.fuxiaosong.app.link.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuxiaosong
 * @version 1.0.0
 * @since 2018年06月01日
 */
public interface Api {
    /**
     * 添加 link
     *
     * @param url 网页链接地址
     * @param categoryId 分类 ID
     * @param tag 标签
     */
    @FormUrlEncoded
    @POST("api/v1/addLink")
    public Call<Response> addLink(@Field("url") String url, @Field("categoryId") String categoryId, @Field("tag") String tag);
}
