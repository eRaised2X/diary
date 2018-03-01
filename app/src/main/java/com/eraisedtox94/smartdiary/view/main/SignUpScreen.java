package com.eraisedtox94.smartdiary.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.business.UserSignUpLoginBusiness;

import java.util.regex.Pattern;

/**
 * Created by spraful on 27-02-2018.
 */

public class SignUpScreen extends AppCompatActivity {

    private TextView tvAlreadyHaveAccount;
    private Button btnsignup;
    private EditText etEmail;
    private EditText etPwd;
    private EditText etConfirmPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        tvAlreadyHaveAccount = findViewById(R.id.tvSignupAlreadyAccount);
        btnsignup = findViewById(R.id.btn_do_signup);
        etPwd = findViewById(R.id.etPwdSignupScreen);
        etConfirmPwd = findViewById(R.id.etRePwdSignupScreen);

        btnsignup.setOnClickListener(new SignUpPageClickListener());
        tvAlreadyHaveAccount.setOnClickListener(new SignUpPageClickListener());
    }

    //click listener class
    private class SignUpPageClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.tvSignupAlreadyAccount:
                    // go to login page
                    Intent loginActivity = new Intent(SignUpScreen.this,LoginScreen.class);
                    startActivity(loginActivity);
                    finish();
                    break;
                case R.id.btn_do_signup:
                    //register the user
                    registerUser(etEmail.getText().toString(),etPwd.getText().toString(),etConfirmPwd.getText().toString());
                    break;
            }
        }
    }

    //register the user if fields are valid
    private void registerUser(String email, String pwd, String rePwd){
        //validate fields
        if(validateSignUpFields(email,pwd,rePwd)){
            //register user
            UserSignUpLoginBusiness.getInstance().registerUser("default",email, pwd, getApplicationContext());
        }
    }

    //validate fields
    private boolean validateSignUpFields(String email, String pwd, String rePwd) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find())
            return false;
        if(pwd!= null && pwd.length()>=4 && !pwd.equals(rePwd)){
            return false;
        }
        //TODO check mandate for special char and atleast one alphabet and one number

        return true;
    }
}
