package com.example.application.chat;

import com.example.application.R;
import com.example.application.chat.adapter.ChatRoomAdapter;
import com.example.application.firebase.FireCallback;
import com.example.application.firebase.FireStoreAccess;
import com.example.application.firebase.RealtimeDatabaseAccess;
import com.example.application.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomListActivity extends AppCompatActivity implements ChatRoomAdapter.RoomClickListener
{

    private Context context;

    private List<ChatRoom> list;

    private RecyclerView recyclerView;
    private ChatRoomAdapter adapter;

    private ChatRoomAdapter.RoomClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);

        context =this;
        listener=this;
        list = new ArrayList<>();

        recyclerView= findViewById(R.id.chatroomlistactivity_recyclerView_chatroom_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        list.clear();
        getChatRoomList();
        listenCounterChange();
    }

    public void getChatRoomList()
    {
        RealtimeDatabaseAccess.Chat.getDatabaseReference()
                .child("ChatRooms_ByUser")
                .child(FireStoreAccess.Auth.getUserUid())
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for (DataSnapshot item: snapshot.getChildren())
                            list.add(item.getValue(ChatRoom.class));

                        adapter = new ChatRoomAdapter(context, list, listener);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
    }

    private void listenCounterChange()
    {
        RealtimeDatabaseAccess.Chat.getDatabaseReference()
                .child("UnreadMessage")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for (DataSnapshot items : snapshot.getChildren())
                        {
                            Log.d("onDataChange!!!", items.getKey());
                            Log.d("onDataChange!!!", items.getValue().toString());
                        }

                        //updateUI(snapshot.getKey(), 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
    }

    private void updateUI(String uid_ChatRoom, long value)
    {
        List<ChatRoom> listTemp = new ArrayList<>();

        for (ChatRoom items: list)
        {
            if (items.getChatRoom_Uid().equals(uid_ChatRoom))
            {
                items.setUnreadMessageCounter(value);
            }
            listTemp.add(items);
        }

        adapter = new ChatRoomAdapter(context, listTemp, listener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRoomClick(int position)
    {

        RealtimeDatabaseAccess.Chat.getMessageHistory(list.get(position).getChatRoom_Uid(), new FireCallback<List<Message>>()
        {
            @Override
            public void callback(List<Message> object)
            {
                ArrayList<Message> messageList = new ArrayList<>();
                messageList.addAll(object);

                Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra("MessageHistory",(Serializable) messageList);
                intent.putExtra("uid_Other", list.get(position).getOther_Uid());
                context.startActivity(intent);
            }
        });

        //context.startActivity(new Intent());
    }
}