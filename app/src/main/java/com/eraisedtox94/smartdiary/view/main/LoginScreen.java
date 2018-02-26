package com.eraisedtox94.smartdiary.view.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.app.SessionManager;
import com.eraisedtox94.smartdiary.business.UserLoginBusiness;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by spraful on 16-09-2017.
 */

public class LoginScreen extends AppCompatActivity {

    private Button btn_login;
    private TextView tvForgotPwd;
    private EditText etLoginPwd;
    private EditText etLoginUname;
    private ProgressDialog pDialog;
    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;
    public SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            Intent intent = new Intent(LoginScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        tvForgotPwd = (TextView) findViewById(R.id.tvForgotPwd);
        btn_login =(Button)findViewById(R.id.btn_do_login);
        etLoginPwd = (EditText) findViewById(R.id.etPwdLoginScreen);
        etLoginUname =(EditText)findViewById(R.id.etUNameLoginScreen);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (LoginButton)findViewById(R.id.login_button);
        fbLoginButton.setReadPermissions("email");
        //String str = AccessToken.getCurrentAccessToken().getToken();
        //String name = Profile.getCurrentProfile().getName();
        //Log.d("fb token earlier:",str);
        //Log.d("fb name:",name);

        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("fb login","success");
                session.setLogin(true);
                TakeMeHome();
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("fb login","cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("fb login","error occured");
            }
        });

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
                    LogUser(etLoginUname.getText().toString(), etLoginPwd.getText().toString());
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //to call login of business method
    private void LogUser(String username, String password){
        // Progress dialog
        //TODO dialog not working pls chk
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.show();
        if(UserLoginBusiness.getInstance().userLogin(username,password,getApplicationContext())){
            TakeMeHome();
        }
        pDialog.dismiss();
    }

    //Go to Home Page
    private void TakeMeHome(){
        Intent intent = new Intent(LoginScreen.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}