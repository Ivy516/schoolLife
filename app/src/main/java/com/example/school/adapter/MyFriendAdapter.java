package com.example.school.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.school.R;
import com.example.school.activity.MyFriendActivity;
import com.example.school.data.User;

import java.util.ArrayList;
import java.util.List;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;

public class MyFriendAdapter extends RecyclerView.Adapter<MyFriendAdapter.myViewHolder> {

    private static List<User> users = new ArrayList<>();
    Context context;
    boolean hasMore = true;

    public MyFriendAdapter(Context context,List<User> users) {
        this.context=context;
        this.users = users;
        //this.hasMore = hasMore;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_friend,parent,false);
        final myViewHolder holder = new myViewHolder(view);
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Log.d("ddd", "onClick: " +position);
                User user = users.get(0);
                RongIM.getInstance().startPrivateChat(context, user.getStuNo(), user.getUserName());
//                RongCallKit.startSingleCall(context, user.getStuNo(),
//                        RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        User user = users.get(position);
        holder.imageView.setImageResource(R.drawable.ic_touxiang);
        holder.textView.setText(user.getUserName());
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public void resetDatas(){
        users=new ArrayList<>();
    }

    public void updateList(List<User> newDatas){
        if (newDatas!=null){
            users.addAll(newDatas);
        }
        notifyDataSetChanged();
    }

    public void setData(List<User> dataList) {
        users.clear();
        users.addAll(dataList);
        notifyDataSetChanged();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        View mItemView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            imageView = itemView.findViewById(R.id.iv_touxiang);
            textView=itemView.findViewById(R.id.tv_name);
        }
    }
}
