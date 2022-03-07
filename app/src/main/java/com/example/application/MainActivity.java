package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.chat.ChatRoomListActivity;
import com.example.application.firebase.FireStoreAccess;
import com.example.application.firebase.RealtimeDatabaseAccess;
import com.example.application.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Context context;

    TextView textView_PhoneNumber;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private Button button;

    private List<User> otherUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        otherUserList = new ArrayList<>();

        getUserUidList();
        //User user0 = new User("234fesd2123_Jina","callmeRena","photo_url_Jina");
        //User user1 = new User("23sdw122345_Marin","marin","photo_url_Marin");


        //RealtimeDatabaseAccess.Chat.createChatRoom(user0.getUid(),user0.getNickname());
        //RealtimeDatabaseAccess.Chat.createChatRoom(user1.getUid(),user1.getNickname());

        textView_PhoneNumber=findViewById(R.id.mainactivity_text_phone_number);
        textView_PhoneNumber.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        button=findViewById(R.id.mainactivity_btn_gotochatroom);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(context, ChatRoomListActivity.class));
            }
        });

        recyclerView=findViewById(R.id.mainactivity_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        adapter =  new TcustomAdapter(context, otherUserList);
        recyclerView.setAdapter(adapter);
    }

    private void getUserUidList()
    {
        FireStoreAccess.Auth.getFirebaseFirestore().collection("Database/User/NickNameList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            if (!task.getResult().isEmpty())
                            {
                                int count =1;
                                for (QueryDocumentSnapshot items: task.getResult())
                                {
                                    otherUserList.add(new User(items.get("userUid").toString(),"T_User0"+count++,""));
                                }
                            }
                            else
                            {
                                Toast.makeText(context, "uid list EMPTY!!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(context, "ERROR!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}