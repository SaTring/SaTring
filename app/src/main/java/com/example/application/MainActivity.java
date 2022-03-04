package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.application.chat.ChatRoomListActivity;
import com.example.application.firebase.RealtimeDatabaseAccess;
import com.example.application.user.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{

    TextView textView_PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //User user0 = new User("234fesd2123_Jina","callmeRena","photo_url_Jina");
        //User user1 = new User("23sdw122345_Marin","marin","photo_url_Marin");


        //RealtimeDatabaseAccess.Chat.createChatRoom(user0.getUid(),user0.getNickname());
        //RealtimeDatabaseAccess.Chat.createChatRoom(user1.getUid(),user1.getNickname());

        textView_PhoneNumber=findViewById(R.id.mainactivity_text_phone_number);
        textView_PhoneNumber.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        startActivity(new Intent(this, ChatRoomListActivity.class));
    }
}