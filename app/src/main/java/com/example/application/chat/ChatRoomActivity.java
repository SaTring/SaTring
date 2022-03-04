package com.example.application.chat;

import com.example.application.R;
import com.example.application.chat.adapter.ChatMessageAdapter;
import com.example.application.firebase.FireCallback;
import com.example.application.firebase.FireStoreAccess;
import com.example.application.firebase.RealtimeDatabaseAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity
{
    private Context context;

    private RecyclerView recyclerView;
    private ChatMessageAdapter adapter;

    private EditText editText_TypingSpace;
    private Button btn_Send;

    private List<Message> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent = getIntent();
        list= (List<Message>) intent.getSerializableExtra("MessageHistory");
        String uid_Other = intent.getStringExtra("uid_Other");

        Log.d("here!!!",list.toString());

        context = this;

        recyclerView = findViewById(R.id.chatroomactivity_recyclerview_message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new ChatMessageAdapter(context,list);
        recyclerView.setAdapter(adapter);


        editText_TypingSpace=findViewById(R.id.chatroomactivity_edit_typing_space);
        btn_Send = findViewById(R.id.chatroomactivity_btn_send);
        btn_Send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String sentence = editText_TypingSpace.getText().toString();
                if (!sentence.matches(""))
                {
                    RealtimeDatabaseAccess.Chat.sendMessage
                                    (new Message(FireStoreAccess.Auth.getUserUid(),
                                            uid_Other,
                                    "mynick",
                                            sentence,
                                            list.get(0).getUid_ChatRoom()));

                    editText_TypingSpace.getText().clear();
                }
                else
                {
                    Toast.makeText(context, "Empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        receiveMessage(list.get(0).getUid_ChatRoom());


    }

    public void receiveMessage(String uid_ChatRoom)
    {
        RealtimeDatabaseAccess.Chat.getDatabaseReference()
                .child("MessageHistory")
                .child(uid_ChatRoom)
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        updateUI();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
    }

    public void updateUI()
    {
        RealtimeDatabaseAccess.Chat.getMessageHistory(list.get(0).getUid_ChatRoom(), new FireCallback<List<Message>>()
        {
            @Override
            public void callback(List<Message> object)
            {
                list.addAll(object);

                adapter=new ChatMessageAdapter(context,list);
                recyclerView.setAdapter(adapter);

            }
        });
    }
}