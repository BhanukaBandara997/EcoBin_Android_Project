package com.tm_synchronizer.ecobinmobileappv10.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tm_synchronizer.ecobinmobileappv10.R;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText new_password, reEnter_new_password;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        new_password = findViewById(R.id.new_password_lbl);
        reEnter_new_password = findViewById(R.id.reEnter_new_password_lbl);
        submit_button = findViewById(R.id.submit_button);
        // Get user inputs to variables for validation

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }
}
