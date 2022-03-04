package com.example.application.user;

public class User
{
    private String uid;
    private String nickname;
    private String photoUrl;

    public User() {}

    public User(String uid, String nickname, String photoUrl)
    {
        this.uid = uid;
        this.nickname = nickname;
        this.photoUrl = photoUrl;
    }

    public void setUid(String uid) { this.uid = uid; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getUid() { return uid; }
    public String getNickname() { return nickname; }
    public String getPhotoUrl() { return photoUrl; }
}
