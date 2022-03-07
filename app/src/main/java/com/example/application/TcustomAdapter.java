package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.firebase.RealtimeDatabaseAccess;
import com.example.application.user.User;

import java.util.List;

public class TcustomAdapter extends RecyclerView.Adapter<TcustomAdapter.ViewHolder>
{
    List<User> list;
    Context context;

    public TcustomAdapter(Context context, List<User> list)
    {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.t_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TcustomAdapter.ViewHolder holder, int position)
    {
        holder.textView_Uid.setText(list.get(position).getUid());
        holder.textView_Nick.setText(list.get(position).getNickname());
        holder.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("here", "onClick");

                RealtimeDatabaseAccess.Chat.createChatRoom(list.get(position).getUid(), list.get(position).getNickname());
                Toast.makeText(context, "chat room created!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView_Nick;
        TextView textView_Uid;
        Button button;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView_Nick=itemView.findViewById(R.id.t_item_nickname);
            textView_Uid=itemView.findViewById(R.id.t_item_name);
            button=itemView.findViewById(R.id.t_item_button);
        }
    }
}
