package cn.jesse.extrasample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.jesse.nativelogger.NLogger;
import cn.jesse.nativelogger.logger.LoggerLevel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NLogger.getInstance()
                .builder()
                .fileLogger(false)
                .tag("EXTRA")
                .loggerLevel(LoggerLevel.DEBUG)
                .build();
    }
}
