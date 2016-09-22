package cn.jesse.extrasample;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.List;

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
        while (true) {
            if (!isOpen("com.baidu.lbs.waimai")) {
                sleep(2000);
                continue;
            }

            sleep(5000);

            MainThreadUtils.getInstance().execute(new Runnable() {
                @Override
                public void run() {
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
