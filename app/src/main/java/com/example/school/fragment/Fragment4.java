package com.example.school.fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.school.R;
import com.example.school.activity.DataActivity;
import com.example.school.activity.LoginActivity;
import com.example.school.activity.MyFriendActivity;
import com.example.school.activity.MyGoodsActivity;
import com.example.school.activity.SendGoodsActivity;
import com.example.school.data.User;
import com.example.school.helper.DataHelper;


@SuppressLint("ValidFragment")
public class Fragment4 extends Fragment implements View.OnClickListener{

    User user;

    public Fragment4(User user){
        this.user=user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4,container,false);

        TextView stuNo=view.findViewById(R.id.tv_id);
        TextView nickName = view.findViewById(R.id.tv_nickname);
        stuNo.setText(user.getStuNo());
        nickName.setText(user.getId());

        LinearLayout l1=view.findViewById(R.id.l1);
        LinearLayout l2 = view.findViewById(R.id.l2);
        LinearLayout l3 = view.findViewById(R.id.l3);
        LinearLayout l4 = view.findViewById(R.id.l4);
        LinearLayout l5 = view.findViewById(R.id.l5);
        LinearLayout l6 = view.findViewById(R.id.l6);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        l6.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.l1:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), DataActivity.class);
                intent1.putExtra("user",user);
                startActivity(intent1);
                break;
            case R.id.l2:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), MyFriendActivity.class);
                startActivity(intent2);
                break;
            case R.id.l3:
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), SendGoodsActivity.class);
                startActivity(intent3);
                break;
            case R.id.l4:
                Intent intent4 = new Intent();
                intent4.setClass(getActivity(), MyGoodsActivity.class);
                startActivity(intent4);
                break;
            case R.id.l5:
                break;
            case R.id.l6:
                Intent intent6 = new Intent();
                intent6.setClass(getActivity(),LoginActivity.class);
                startActivity(intent6);
                break;
        }
    }
}
