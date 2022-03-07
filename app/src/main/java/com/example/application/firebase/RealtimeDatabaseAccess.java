package com.example.application.firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.application.chat.ChatRoom;
import com.example.application.chat.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RealtimeDatabaseAccess
{
    private static FirebaseDatabase database=FirebaseDatabase.getInstance();
    private static DatabaseReference reference=database.getReference();
    private static ValueEventListener postListener;

    private static FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public static class Chat
    {
        public static DatabaseReference getDatabaseReference()
        {
            return reference;
        }
        public static void createChatRoom(String other_Uid, String other_Nickname)
        {

            Log.d("here", other_Uid);
            Log.d("here", other_Nickname);
            //1. make chat room with random uid value
            DatabaseReference reference_ChatRoomUid=
                    reference
                            .child("ChatRoomList")
                            .push();


            String uid_ChatRoomCreated = reference_ChatRoomUid.getKey();
            Log.d("here", uid_ChatRoomCreated);
            //2. add an object of chat room created to "ChatRoomList"
            ChatRoom chatRoom=
                    new ChatRoom(uid_ChatRoomCreated,
                            firebaseUser.getUid(),
                            firebaseUser.getUid(),
                            "mynick",
                            other_Uid,
                            other_Nickname,
                            "_",
                            " ");

            reference_ChatRoomUid.setValue(chatRoom);

            //3. add an object of chat room created to "ChatRooms_ByUser" each other
            reference
                    .child("ChatRooms_ByUser")
                    .child(firebaseUser.getUid())
                    .child(uid_ChatRoomCreated)
                    .setValue(chatRoom);

            reference
                    .child("ChatRooms_ByUser")
                    .child(other_Uid)
                    .child(uid_ChatRoomCreated)
                    .setValue(chatRoom);

            //3. create a path of message history to "MessageHistory"
            sendMessage(new Message(firebaseUser.getUid(),other_Uid,"mynick","user opened chat room with you :)",uid_ChatRoomCreated));

            //4. create a path of unread message counter to "UnreadMessage"
            reference
                    .child("UnreadMessage")
                    .child(uid_ChatRoomCreated)
                    .child(firebaseUser.getUid())
                    .child("Counter")
                    .setValue(0);
            reference
                    .child("UnreadMessage")
                    .child(uid_ChatRoomCreated)
                    .child(other_Uid)
                    .child("Counter")
                    .setValue(0);
        }

        /**
        public static void getChatRoomList(FireCallback<List<ChatRoom>> callback)
        {

            reference
                    .child("ChatRooms_ByUser")
                    .child(firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                List<ChatRoom> list= new ArrayList<>();

                                for (DataSnapshot item:task.getResult().getChildren())
                                {
                                    list.add(item.getValue(ChatRoom.class));
                                    Log.d("here", item.getValue(ChatRoom.class).toString());

                                }

                                callback.callback(list);
                            }
                            else
                            {
                                Log.d("error", task.getException().getMessage());
                            }
                        }
                    });
        }**/


        public static void sendMessage(Message message)
        {
            DatabaseReference messageUid =
                    reference
                            .child("MessageHistory")
                            .child(message.getUid_ChatRoom())
                            .push();

            String uid = messageUid.getKey();
            message.setUid_Message(uid);

            reference
                    .child("MessageHistory")
                    .child(message.getUid_ChatRoom())
                    .child(uid)
                    .setValue(message);

            reference
                    .child("ChatRoomList")
                    .child(message.getUid_ChatRoom())
                    .child("lastMessage")
                    .setValue(message.getContents());

            reference
                    .child("ChatRooms_ByUser")
                    .child(message.getUid_User())
                    .child(message.getUid_ChatRoom())
                    .child("lastMessage")
                    .setValue(message.getContents());

            reference
                    .child("ChatRooms_ByUser")
                    .child(message.getUid_Other())
                    .child(message.getUid_ChatRoom())
                    .child("lastMessage")
                    .setValue(message.getContents());

            reference
                    .child("UnreadMessage")
                    .child(message.getUid_ChatRoom())
                    .child(message.getUid_Other())
                    .child("Counter")
                    .setValue(ServerValue.increment(1));
        }

        public static void getMessageHistory(String uid_ChatRoom, FireCallback<List<Message>> callback)
        {
            reference
                    .child("MessageHistory")
                    .child(uid_ChatRoom)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                List<Message> list=new ArrayList<>();

                                for (DataSnapshot item:task.getResult().getChildren())
                                    list.add(item.getValue(Message.class));

                                Log.d("here!!",list.toString() );

                                callback.callback(list);
                            }
                            else
                            {
                                Log.d("error", task.getException().getMessage());
                            }

                        }
                    });
        }
    }



}
