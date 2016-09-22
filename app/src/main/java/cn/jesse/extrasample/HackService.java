package cn.jesse.extrasample;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by jesse on 9/21/16.
 */
public class HackService extends IntentService {

    public HackService(String name) {
        super(name);
    }

    public HackService() {
        super("Hack");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        NLogger.d("onHandleIntent");
//        Intent dialogIntent = new Intent(getBaseContext(), LoginActivity.class);
//        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getApplication().startActivity(dialogIntent);
        while (true) {
            NLogger.d("scan com.baidu.lbs.waimai");
            if (!isOpen("com.baidu.lbs.waimai")) {
                NLogger.d("com.baidu.lbs.waimai not running");
                sleep(2000);
                continue;
            }
            NLogger.d("com.baidu.lbs.waimai is running");
            sleep(5000);
            new RemoteToaster(getApplicationContext()).show();
            MainThreadUtils.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    NLogger.d("toast hack dialog");
                    new RemoteToaster(getApplicationContext()).show();
                }
            });
            break;
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isOpen(String packageName) {
        if (packageName.equals("") || packageName == null)
            return false;
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runinfo : runningAppProcesses) {
            String pn = runinfo.processName;
            if (pn.equals(packageName)
                    && runinfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                return true;
        }
        return false;
    }
}
