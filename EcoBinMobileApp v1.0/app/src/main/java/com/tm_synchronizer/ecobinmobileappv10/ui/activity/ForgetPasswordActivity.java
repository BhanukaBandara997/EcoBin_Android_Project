package com.tm_synchronizer.ecobinmobileappv10.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tm_synchronizer.ecobinmobileappv10.R;
import com.tm_synchronizer.ecobinmobileappv10.network.ServerConnection;

import org.json.JSONException;
import org.json.JSONObject;


public class ForgetPasswordActivity extends AppCompatActivity {

    private Button forgetPasswordBtn;
    private EditText mobileNoTxt;
    String mobileNoEntered;
    final static String BASE_URL = "";
    boolean isSend;
    ServerConnection serverConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        serverConnection = new ServerConnection(this);
        mobileNoTxt = findViewById(R.id.mobileNo_lbl);

        forgetPasswordBtn = findViewById(R.id.forget_password_send_button);
        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNoEntered = mobileNoTxt.getText().toString();
                authenticateUserPassword(mobileNoEntered);
            }
        });

    }


    private boolean authenticateUserPassword(String mobileNoEntered) {
        JSONObject object=new JSONObject();
        String url = BASE_URL+"/";

        try {
            object.put("userMobileNo",mobileNoEntered);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest authenticateUserPassword = new JsonObjectRequest(Request.Method.POST, url, object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.get("access").toString().equals("ok")){
                        isSend = true;
                    }else if (!isSend){
                        isSend = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPasswordActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        serverConnection.getRequestQueue().add(authenticateUserPassword);
        return isSend;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
