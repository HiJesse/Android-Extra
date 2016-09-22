package cn.jesse.extrasample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jesse on 9/21/16.
 */
public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);


        final EditText account = (EditText) findViewById(R.id.edt_account);
        final EditText pwd = (EditText) findViewById(R.id.edt_pwd);
        Button verify = (Button) findViewById(R.id.btn_verify);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
