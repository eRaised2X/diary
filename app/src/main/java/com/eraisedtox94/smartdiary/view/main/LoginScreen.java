package com.eraisedtox94.smartdiary.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eraisedtox94.smartdiary.R;

/**
 * Created by spraful on 16-09-2017.
 */

public class LoginScreen extends AppCompatActivity {

    private Button btn_login;
    private TextView tvForgotPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        tvForgotPwd = (TextView) findViewById(R.id.tvForgotPwd);
        btn_login =(Button)findViewById(R.id.btn_do_login);

        btn_login.setOnClickListener(new MyClickListener());
        tvForgotPwd.setOnClickListener(new MyClickListener());
    }

    public class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.tvForgotPwd :
                    //Intent intent = new Intent(LoginScreen.this,ForgotPwd.class);
                    //startActivity(intent);
                    Toast.makeText(getBaseContext(),"Email sent",Toast.LENGTH_LONG).show();
                    break;
                case R.id.btn_do_login:
                    Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
