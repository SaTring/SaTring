package com.example.application.chat;

import androidx.annotation.NonNull;

public class ChatRoom
{
    private String chatRoom_Uid;
    private String createdBy_Uid;
    private String me_Uid;
    private String me_NickName;
    private String other_Uid;
    private String other_NickName;
    private String other_PhotoUrl;
    private String lastMessage;
    private long unreadMessageCounter;

    public ChatRoom() {}

    public ChatRoom(String chatRoom_Uid, String createdBy_Uid, String me_Uid, String me_NickName, String other_Uid, String other_NickName, String other_PhotoUrl, String lastMessage)
    {
        this.chatRoom_Uid = chatRoom_Uid;
        this.createdBy_Uid = createdBy_Uid;
        this.me_Uid = me_Uid;
        this.me_NickName = me_NickName;
        this.other_Uid = other_Uid;
        this.other_NickName = other_NickName;
        this.other_PhotoUrl = other_PhotoUrl;
        this.lastMessage = lastMessage;
    }

    public void setOther_NickName(String other_NickName) { this.other_NickName = other_NickName; }
    public void setOther_Uid(String other_Uid) { this.other_Uid = other_Uid; }

    public void setChatRoom_Uid(String chatRoom_Uid) { this.chatRoom_Uid = chatRoom_Uid; }

    public void setCreatedBy_Uid(String createdBy_Uid) { this.createdBy_Uid = createdBy_Uid; }
    public void setMe_Uid(String me_Uid) { this.me_Uid = me_Uid; }
    public void setMe_NickName(String me_NickName) { this.me_NickName = me_NickName; }

    public void setOther_PhotoUrl(String other_PhotoUrl) { this.other_PhotoUrl = other_PhotoUrl; }

    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public void setUnreadMessageCounter(long unreadMessageCounter) { this.unreadMessageCounter = unreadMessageCounter; }



    public String getOther_Uid() { return other_Uid; }
    public String getOther_NickName() { return other_NickName; }

    public String getChatRoom_Uid() {
        return chatRoom_Uid;
    }

    public String getCreatedBy_Uid() {
        return createdBy_Uid;
    }

    public String getMe_Uid() {
        return me_Uid;
    }

    public String getMe_NickName() {
        return me_NickName;
    }

    public String getOther_PhotoUrl() {
        return other_PhotoUrl;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public long getUnreadMessageCounter() { return unreadMessageCounter; }

    @NonNull
    @Override
    public String toString()
    {
        return getChatRoom_Uid().toString()+" ";
    }
}
