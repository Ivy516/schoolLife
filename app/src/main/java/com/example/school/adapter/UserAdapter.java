package com.example.school.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.data.User;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        User user = getItem(position);
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.useritem_view,null);
            viewHolder = new ViewHolder();
            viewHolder.userName=view.findViewById(R.id.username);
            viewHolder.userName.setText(user.getUserName());
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder=(ViewHolder) view.getTag();
            viewHolder.userName.setText(user.getUserName());
        }
        return view;

    }


    public class ViewHolder{
        TextView userName;
    }
}
