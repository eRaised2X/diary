package com.eraisedtox94.smartdiary.business;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.eraisedtox94.smartdiary.app.AppConstants;
import com.eraisedtox94.smartdiary.app.DiaryApplication;
import com.eraisedtox94.smartdiary.app.SQLiteHandler;
import com.eraisedtox94.smartdiary.app.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by spraful on 21-02-2018.
 */

public class UserLoginBusiness {

    private static UserLoginBusiness mInstance;
    private SQLiteHandler sqLiteHandler;
    private SessionManager session;
    private boolean flag = false;
    private String TAG = UserLoginBusiness.class.getName();

    //create a new instance if does not exist .
    public static UserLoginBusiness getInstance(){
        if(mInstance!=null){
            return mInstance;
        }
        else{
            return new UserLoginBusiness();
        }
    }

    //login user
    public boolean userLogin(final String userEmail, final String password, final Context context){

        // SQLite database handler
        sqLiteHandler = new SQLiteHandler(context);

        // Session manager
        session = new SessionManager(context);

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            return true;
        }
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConstants.URL_LOGIN
        ,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    onSuccess(response);
                }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onFailure(error);
                }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", userEmail);
                params.put("password", password);

                return params;
            }

        };
        // Adding request to request queue
        DiaryApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
        return flag;
    }

        //error response
        private void onFailure(VolleyError error){
            Log.e(TAG, "Login Error: " + error);
            flag = false;
        }

        //success response
        private void onSuccess(String response){
            Log.d(TAG, "Login Response: " + response.toString());

            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getBoolean("error");

                // Check for error node in json
                if (!error) {
                    // user successfully logged in
                    // Create login session
                    session.setLogin(true);
                    flag = true;

                    // Now store the user in SQLite
                    String uid = jObj.getString("uid");

                    JSONObject user = jObj.getJSONObject("user");
                    String name = user.getString("name");
                    String email = user.getString("email");
                    String created_at = user
                            .getString("created_at");

                    // Inserting row in users table
                    sqLiteHandler.addUser(name, email, uid, created_at);

                } else {
                    // Error in login. Get the error message
                    String errorMsg = jObj.getString("error_msg");
                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
            }
        }
}