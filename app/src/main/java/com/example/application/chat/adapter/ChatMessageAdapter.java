package com.example.application.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.chat.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalTime;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter
{
    private static final int VIEW_TYPE_ME = 0;
    private static final int VIEW_TYPE_OTHER = 1;

    private FirebaseUser firebaseUser;
    private Context context;
    private List<Message> list;

    public ChatMessageAdapter(Context context, List<Message> list)
    {
        this.firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position)
    {
        Message message = (Message) list.get(position);

        if (message.getUid_User().equals(firebaseUser.getUid()))
            return VIEW_TYPE_ME;
        else
            return VIEW_TYPE_OTHER;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;

        if (viewType == VIEW_TYPE_ME)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_me, parent,false);

            return new MyMessageViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_OTHER)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_other, parent,false);

            return new OtherMessageViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position)
    {
        Message message = (Message) list.get(position);

        switch (holder.getItemViewType())
        {
            case VIEW_TYPE_ME:
                ((MyMessageViewHolder)holder).bind(message);
                break;
            case VIEW_TYPE_OTHER:
                ((OtherMessageViewHolder)holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    protected class MyMessageViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView_Message;
        private TextView textView_Date;
        private TextView textView_Time;

        public MyMessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView_Message=itemView.findViewById(R.id.message_me_text_contents);
            textView_Date=itemView.findViewById(R.id.message_me_text_date);
            textView_Time=itemView.findViewById(R.id.message_me_text_time);

        }

        void bind(Message message)
        {

            textView_Message.setText(message.getContents());

            textView_Date.setVisibility(View.GONE);
            textView_Time.setText(message.getTime());
        }
    }

    protected class OtherMessageViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView_Message;
        private TextView textView_Date;
        private TextView textView_Time;
        private TextView textView_NickName;
        private ImageView imageView_ProfileImage;

        public OtherMessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView_Message=itemView.findViewById(R.id.message_other_text_contents);
            textView_Date=itemView.findViewById(R.id.message_other_text_date);
            textView_Time=itemView.findViewById(R.id.message_other_text_time);
            textView_NickName=itemView.findViewById(R.id.message_other_text_user_name);
            imageView_ProfileImage=itemView.findViewById(R.id.message_other_image_profile_image);
        }

        void bind(Message message)
        {
            textView_Message.setText(message.getContents());
            textView_Date.setVisibility(View.GONE);
            textView_Time.setText(message.getTime());
            textView_NickName.setText(message.getNickName());
        }
    }
}
