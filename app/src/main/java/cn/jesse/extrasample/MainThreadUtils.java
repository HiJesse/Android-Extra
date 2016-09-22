package cn.jesse.extrasample;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by jesse on 4/6/16.
 */
public class MainThreadUtils {
    private Handler handler;
    private static MainThreadUtils mInstance;

    private MainThreadUtils() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void execute(Runnable runnable) {
        handler.post(runnable);
    }

    public static synchronized MainThreadUtils getInstance() {
        if(mInstance == null) {
            mInstance = new MainThreadUtils();
        }
        return mInstance;
    }
}
