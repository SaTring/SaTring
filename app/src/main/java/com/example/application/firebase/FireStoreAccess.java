package com.example.application.firebase;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.application.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStoreAccess
{
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser firebaseUser=mAuth.getCurrentUser();

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static class Auth
    {
        private static final String TAG = "TAG_AUTH";

        public static void login(Context context, PhoneAuthCredential credential)
        {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                firebaseUser=task.getResult().getUser();

                                context.startActivity(new Intent(context, MainActivity.class).putExtra("user", firebaseUser.getPhoneNumber()));
                            }
                            else
                            {
                                if (task.getResult() instanceof FirebaseAuthInvalidCredentialsException)
                                {

                                }
                            }

                        }
                    });
        }

        public static String getUserUid()
        {
            return firebaseUser.getUid();
        }


    }
}
