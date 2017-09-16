package com.eraisedtox94.smartdiary.view.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eraisedtox94.smartdiary.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

/**
 * Created by spraful on 26-08-2017.
 */

public class LoginSignupScreen extends AppCompatActivity {


    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private Button btn_login;
    private Button btn_signup;

    TextView tvTitleFirstHalf;
    TextView tvTitleSecondHalf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup_screen);


        tvTitleFirstHalf =  (TextView)findViewById(R.id.tvTitleFirstHalf);
        tvTitleSecondHalf =  (TextView)findViewById(R.id.tvTitleSecondHalf);

        btn_login =(Button)findViewById(R.id.btn_login);
        btn_signup =(Button)findViewById(R.id.btn_signup);

        tvTitleFirstHalf.setTypeface(Typeface.createFromAsset(this.getAssets(),"fonts/blokletters_balpen.ttf"));
        tvTitleSecondHalf.setTypeface(Typeface.createFromAsset(this.getAssets(),"fonts/blokletters_balpen.ttf"));

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //loginButton = (LoginButton)findViewById(R.id.login_button);
        //loginButton.setReadPermissions("email");
        info = (TextView)findViewById(R.id.info);

        //String str = AccessToken.getCurrentAccessToken().getToken();
        //String name = Profile.getCurrentProfile().getName();
        //Log.d("fb token earlier:",str);
        //Log.d("fb name:",name);
        //info.setText(str);

        // Callback registration
        /*loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("fb login","success");
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("fb login","cancelled");
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("fb login","error occured");
                info.setText("Login attempt failed.");
            }
        });*/


        btn_signup.setOnTouchListener(new MyButtonTouchListener(btn_signup)) ;
        btn_login.setOnTouchListener(new MyButtonTouchListener(btn_login)) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public class MyButtonTouchListener implements View.OnTouchListener{

        //ctor
        Button myButton ;
        MyButtonTouchListener(View myView){
            myButton = (Button) myView;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    //v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);

                    myButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    myButton.setBackgroundResource(R.drawable.login_signup_btn_enabled);
                    //v.invalidate();
                    Toast.makeText(getBaseContext(),myButton.getText(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginSignupScreen.this,LoginScreen.class);
                    startActivity(intent);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    //v.getBackground().clearColorFilter();
                    myButton.setTextColor(getResources().getColor(R.color.colorMyWhite));
                    myButton.setBackgroundResource(R.drawable.login_signup_btn_disabled);
                    //v.invalidate();
                    break;
                }
            }
            return false;
        }
    }
}
