package cn.fuxiaosong.app.link;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import cn.fuxiaosong.app.link.network.OnDoneListener;
import cn.fuxiaosong.app.link.network.Response;

/**
 * https://blog.csdn.net/u012436608/article/details/52914316
 * https://blog.csdn.net/zhaizu/article/details/70154301
 *
 * @author fuxiaosong
 * @version 1.0.0
 * @since 2018年06月01日
 */
public class ClipboardService extends Service {
    private static final String COPIED = "copied-> ";
    private static final String ADD_LINK = "addLink-> ";

    private ClipboardManager clipboardManager;
    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0) {
                CharSequence clipContent = clipboardManager.getPrimaryClip().getItemAt(0).getText();
                if (clipContent != null) {
                    String text = clipContent.toString();
                    // 监测到有复制剪切
                    Toast.makeText(ClipboardService.this, COPIED + text, Toast.LENGTH_SHORT).show();
                    // 判断内容并决定是否发起网络请求
                    if (text.startsWith("http://") || text.startsWith("https://")) {
                        // 发起网络请求
                        cn.fuxiaosong.app.link.network.Service.addLink(text, "", "", new OnDoneListener() {
                            @Override
                            public void onFailed(String errMeg) {
                                Toast.makeText(ClipboardService.this, ADD_LINK + errMeg, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(Response data) {
                                Toast.makeText(ClipboardService.this, ADD_LINK + data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ClipboardService.this, COPIED + "url is not correct", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        assert clipboardManager != null;
        clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
