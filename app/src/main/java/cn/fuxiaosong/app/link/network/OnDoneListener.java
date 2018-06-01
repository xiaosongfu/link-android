package cn.fuxiaosong.app.link.network;

/**
 * @author fuxiaosong
 * @version 1.0.0
 * @since 2018年01月09日
 */
public interface OnDoneListener {
    void onFailed(String errMeg);

    void onSuccess(Response data);
}
