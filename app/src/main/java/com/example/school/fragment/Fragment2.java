package com.example.school.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.school.R;
import com.example.school.activity.ChaXunActivity;
import com.example.school.activity.MyMapActivity;
import com.example.school.activity.SecondaryMarketActivity;
import com.example.school.activity.XiaoLiActivity;
import com.example.school.activity.classActivity;
import com.example.school.data.User;


public class Fragment2 extends Fragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);


        ImageView course =view.findViewById(R.id.iv_class);
        ImageView chaxun=view.findViewById(R.id.iv_chaxun);
        ImageView xiaoli = view.findViewById(R.id.iv_xiaoli);
        ImageView secondShop=view.findViewById(R.id.iv_second_shop);
        ImageView map = view.findViewById(R.id.iv_map);

        course.setOnClickListener(this);
        chaxun.setOnClickListener(this);
        xiaoli.setOnClickListener(this);
        secondShop.setOnClickListener(this);
        map.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_class:
                Intent intent = new Intent();
                intent.setClass(getActivity(), classActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_chaxun:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), ChaXunActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_xiaoli:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), XiaoLiActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv_second_shop:
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), SecondaryMarketActivity.class);
                startActivity(intent3);
                break;
            case R.id.iv_map:
                Intent intent4 = new Intent();
                intent4.setClass(getActivity(), MyMapActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }
}
