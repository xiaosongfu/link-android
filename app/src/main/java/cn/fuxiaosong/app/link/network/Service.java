package cn.fuxiaosong.app.link.network;

import com.google.gson.stream.MalformedJsonException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author fuxiaosong
 * @version 1.0.0
 * @since 2018年06月01日
 */
public class Service {
    // 静态的 Retrofit 实例
    private static Retrofit retrofit = null;
    private static Api api = null;

    // 构造 Retrofit 实例
    private static Retrofit getRetrofitClient() {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://120.77.47.141:1205/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //获取 MainRestApi
    private static Api getApi() {
        return getRetrofitClient().create(Api.class);
    }

    /**
     * 添加 link
     *
     * @param url        网页链接地址
     * @param categoryId 分类 ID
     * @param tag        标签
     */
    public static void addLink(String url, String categoryId, String tag, final OnDoneListener onDoneListener) {
        if (api == null) {
            api = getApi();
        }

        Call<Response> call = api.addLink(url, categoryId, tag);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                // 结果处理
                if (response.isSuccessful()) {
                    // 获取结果
                    Response result = response.body();
                    if (null != result) {
                        onDoneListener.onSuccess(result);
                    } else {
                        // 服务器返回数据格式错误
                        onDoneListener.onFailed("服务器返回数据格式错误");
                    }
                } else {
                    // 请求服务器失败
                    onDoneListener.onFailed("请求服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                // 根据错误类型提示对应错误
                onDoneListener.onFailed(errorType(t));
            }


        });
    }

    // 根据错误类型生成错误信息
    private static String errorType(Throwable t) {
        if (t instanceof MalformedJsonException) {
            return "服务器返回数据异常，请重试"; // 服务器返回数据不是 Json 字符串
        } else if (t instanceof SocketTimeoutException) {
            return "服务器连接超时，请重试";
        } else if (t instanceof ConnectException) {
            return "服务器连接超时，请重试";
        } else {
            return "服务器连接失败,请检查你的网络连接";
        }
    }
}
