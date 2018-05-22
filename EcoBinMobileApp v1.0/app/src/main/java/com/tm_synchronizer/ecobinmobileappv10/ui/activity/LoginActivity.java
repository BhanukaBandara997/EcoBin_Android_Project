package com.tm_synchronizer.ecobinmobileappv10.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tm_synchronizer.ecobinmobileappv10.R;
import com.tm_synchronizer.ecobinmobileappv10.model.AuthenticateUserParameter;
import com.tm_synchronizer.ecobinmobileappv10.network.ServerConnection;
import com.tm_synchronizer.ecobinmobileappv10.ui.other.PreferenceData;
import com.tm_synchronizer.ecobinmobileappv10.ui.utils.NotificationManager;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    // UI components
    private Button loginBtn;
    private TextView forgetPasswordLbl;
    private EditText mUserEmail, mPassword;
    private ServerConnection serverConnection;
    static final String BASE_URL = " https://ecobintest.herokuapp.com/";
    public static String logemail;
    // Local Variables for validation
    String userEmailAddress, passwordEntered;
    Context mContext;
    boolean isAuthenticated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mUserEmail = findViewById(R.id.userEmail_lbl);
        mPassword = findViewById(R.id.password_lbl);
        loginBtn = findViewById(R.id.login_button);
        forgetPasswordLbl = findViewById(R.id.forget_password_lbl);
        serverConnection = new ServerConnection(this);
        // Get user inputs to variables for validation

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reset errors.
                mUserEmail.setError(null);
                mPassword.setError(null);

                userEmailAddress = mUserEmail.getText().toString();
                passwordEntered = mPassword.getText().toString();

                boolean focusPassword = false;
                boolean focusEmail = false;

                View focusView = null;

                if (userEmailAddress.length() <= 0) {
                    mPassword.setError(getString(R.string.error_invalid_password));
                    focusView = mPassword;
                    focusPassword = true;
                }

                if (passwordEntered.length() <= 0) {
                    mUserEmail.setError(getString(R.string.error_email_address));
                    focusView = mUserEmail;
                    focusEmail = true;
                }

                if (focusPassword) {
                    focusView.requestFocus();
                    focusPassword = false;
                }
                else if (focusEmail) {
                    focusView.requestFocus();
                    focusEmail = false;
                }
                else if (!focusEmail || !focusPassword){
                    if(authenticateUser(userEmailAddress,passwordEntered)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("emailAddress", logemail);
                        startActivity(intent);
                    }
                }
            }
        });

        forgetPasswordLbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean authenticateUser(final String userEmailAddress, String userPassword) {
        JSONObject object=new JSONObject();
        try {
            object.put("email",userEmailAddress);
            object.put("password",userPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest authenticateUserResult = new JsonObjectRequest(Request.Method.POST, " https://ecobintest.herokuapp.com/api/transact/echoaccess", object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(LoginActivity.this,response.get("access").toString(),Toast.LENGTH_SHORT);
                    if(response.get("access").toString().equals("ok")){
                        isAuthenticated = true;
                        /*bVd edited*/
                        logemail=userEmailAddress;
                    }else if (!isAuthenticated){
                        isAuthenticated = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        serverConnection.getRequestQueue().add(authenticateUserResult);

        return isAuthenticated;
    }
}
