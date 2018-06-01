package cn.fuxiaosong.app.link.network;

/**
 * @author fuxiaosong
 * @version 1.0.0
 * @since 2018年06月01日
 */
public class Response {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
