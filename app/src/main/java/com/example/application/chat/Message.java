package com.example.application.chat;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable
{
    private String uid_Message;
    private String uid_User;
    private String uid_Other;
    private String nickName;
    private String contents;
    private String date;
    private String time;
    private String uid_ChatRoom;

    public Message() {}
    public Message(String uid_User, String uid_Other, String nickName, String contents, String uid_ChatRoom)
    {
        this.uid_User = uid_User;
        this.uid_Other = uid_Other;
        this.nickName = nickName;
        this.contents = contents;

        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));

        this.uid_ChatRoom=uid_ChatRoom;
    }

    public void setContents(String contents) { this.contents = contents; }
    public void setUid_Message(String uid_Message) { this.uid_Message = uid_Message; }

    public String getUid_Message() { return uid_Message; }
    public String getUid_User() { return uid_User; }
    public String getUid_Other() { return uid_Other; }
    public String getNickName() { return nickName; }
    public String getContents() { return contents; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getUid_ChatRoom() { return uid_ChatRoom; }

    @NonNull
    @Override
    public String toString()
    {
        return "message: "+getUid_Message();
    }
}
