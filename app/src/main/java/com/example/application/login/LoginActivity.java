package com.example.application.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.application.R;
import com.example.application.firebase.FireStoreAccess;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity
{
    private Context context;
    private FirebaseAuth mAuth;

    private EditText editText_PhoneNumber;
    private Button btn_OK;

    private PinView pinView_OTP;
    private Button btn_Verify;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phoneNumber;
    private String mVerificationId;

    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context=this;
        mAuth=FirebaseAuth.getInstance();

        editText_PhoneNumber=findViewById(R.id.loginactivity_edit_phone_number);
        pinView_OTP=findViewById(R.id.loginactivity_pin_otp);
        pinView_OTP.setVisibility(View.GONE);

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(verificationId, forceResendingToken);

                mVerificationId=verificationId;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {

            }
        };

        btn_OK=findViewById(R.id.loginactivity_btn_ok);
        btn_OK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                phoneNumber=editText_PhoneNumber.getText().toString();

                if (phoneNumber.length()<5 || phoneNumber.isEmpty())
                    Toast.makeText(context, "invalid phone number", Toast.LENGTH_SHORT).show();
                else
                {
                    sendOTP(phoneNumber);
                    pinView_OTP.setVisibility(View.VISIBLE);
                    btn_Verify.setVisibility(View.VISIBLE);
                }


            }
        });

        btn_Verify=findViewById(R.id.loginactivity_btn_vetify);
        btn_Verify.setVisibility(View.GONE);
        btn_Verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                otp = pinView_OTP.getText().toString();
                verifyOTP(otp);
            }
        });
    }

    private void sendOTP(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+82"+phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(LoginActivity.this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void verifyOTP(String OTP)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,OTP);

        FireStoreAccess.Auth.login(context,credential);
    }
}