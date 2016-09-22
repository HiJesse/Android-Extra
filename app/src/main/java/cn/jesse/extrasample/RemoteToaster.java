package cn.jesse.extrasample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

public class RemoteToaster implements View.OnTouchListener {

    Context mContext;
    WindowManager.LayoutParams params;
    WindowManager mWM;
    View mView;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    public RemoteToaster(Context context){
        this.mContext = context;
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = R.style.anim_view;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.gravity = Gravity.CENTER;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWM = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater inflate = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflate.inflate(R.layout.dialog_notify, null);
        mView.setOnTouchListener(this);
    }

    public void show(){

        final TextView confirm = (TextView) mView.findViewById(R.id.tv_confirm);
        final TextView content = (TextView) mView.findViewById(R.id.tv_content);


        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                startActivityByPackage("com.ppmoney.ppstock");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("cn.jesse.extrasample", "cn.jesse.extrasample.LoginActivity");
                intent.setComponent(cn);
                mContext.startActivity(intent);
            }
        });

        if (mView.getParent() != null) {
            mWM.removeView(mView);
        }
        mWM.addView(mView, params);
    }

    public void hide(){
        if(mView!=null){
            mWM.removeView(mView);
        }
    }


    private void updateViewPosition(){
        //更新浮动窗口位置参数
        params.x=(int) (x-mTouchStartX);
        params.y=(int) (y-mTouchStartY);
        mWM.updateViewLayout(mView, params);  //刷新显示
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
//        x = event.getRawX();
//        y = event.getRawY();
//        Log.i("currP", "currX"+x+"====currY"+y);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
//                //获取相对View的坐标，即以此View左上角为原点
//                mTouchStartX =  event.getX();
//                mTouchStartY =  event.getY();
//                Log.i("startP","startX"+mTouchStartX+"====startY"+mTouchStartY);
//                break;
//            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
//                updateViewPosition();
//                break;
//            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
//                updateViewPosition();
//                break;
//        }
        return true;
    }

    private void startActivityByPackage(String appPackage) {
        PackageInfo pi = null;
        try {
            pi = mContext.getPackageManager().getPackageInfo(appPackage, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);

        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        resolveIntent.setPackage(pi.packageName);

        PackageManager pm= mContext.getPackageManager();

        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
        ResolveInfo ri = apps.iterator().next();

        if (ri != null ) {

            String packageName = ri.activityInfo.packageName;

            String className = ri.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);

            mContext.startActivity(intent);
        }
    }
}
