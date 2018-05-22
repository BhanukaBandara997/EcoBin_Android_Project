package com.tm_synchronizer.ecobinmobileappv10.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tm_synchronizer.ecobinmobileappv10.R;

public class ConfirmVerificationActivity extends AppCompatActivity {

    EditText verificationCodeLbl;
    Button confirmVerificationSendButton, resendVerificationCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_verification_code);

        verificationCodeLbl = findViewById(R.id.verification_code_lbl);
        confirmVerificationSendButton = findViewById(R.id.confirm_verification_send_button);
        resendVerificationCodeButton = findViewById(R.id.resend_verification_code_button);
        // Get user inputs to variables for validation

        confirmVerificationSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        resendVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }
}
