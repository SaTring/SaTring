package com.example.application.chat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.chat.ChatRoom;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>
{
    private Context context;
    private List<ChatRoom> list;

    private RoomClickListener roomClickListener;

    public ChatRoomAdapter(Context context, List<ChatRoom> list, RoomClickListener roomClickListener)
    {
        this.context = context;
        this.list = list;
        this.roomClickListener = roomClickListener;
    }

    public interface RoomClickListener
    {
        void onRoomClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_room, parent, false);

        return new ViewHolder(view, roomClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomAdapter.ViewHolder holder, int position)
    {
            holder.getTextView_OtherNickName().setText(list.get(position).getOther_NickName());
            holder.getTextView_LatestMessage().setText(list.get(position).getLastMessage());
            holder.getTextView_Counter().setText(String.valueOf(list.get(position).getUnreadMessageCounter()));
        Log.d("here", list.get(position).getLastMessage());
        Log.d("here", String.valueOf(list.get(position).getUnreadMessageCounter()));

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView imageView_ProfileImage;
        private TextView textView_OtherNickName;
        private TextView textView_LatestMessage;
        private TextView textView_Counter;
        private RoomClickListener roomClickListener_ViewHolder;

        public ViewHolder(@NonNull View itemView, RoomClickListener roomClickListener_ViewHolder)
        {
            super(itemView);

            imageView_ProfileImage = itemView.findViewById(R.id.item_chat_room_image_profile_image);
            textView_OtherNickName = itemView.findViewById(R.id.item_chat_room_text_nickname);
            textView_LatestMessage = itemView.findViewById(R.id.item_chat_room_text_latest_message);
            textView_Counter = itemView.findViewById(R.id.item_chat_room_text_count);

            this.roomClickListener_ViewHolder=roomClickListener_ViewHolder;
            itemView.setOnClickListener(this);
        }

        public TextView getTextView_OtherNickName() { return textView_OtherNickName;}
        public TextView getTextView_LatestMessage() { return textView_LatestMessage;}
        public TextView getTextView_Counter() { return textView_Counter;}

        @Override
        public void onClick(View view)
        {
            roomClickListener_ViewHolder.onRoomClick(getAdapterPosition());
        }
    }

}
