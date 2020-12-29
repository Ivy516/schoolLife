package com.example.school.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.data.User;
import com.example.school.helper.DataHelper;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        TextView textView = findViewById(R.id.tv_title);
        textView.setText("我的资料");

        TextView nickName=findViewById(R.id.zl_account1);
        TextView stu = findViewById(R.id.zl_id1);
        TextView name=findViewById(R.id.zl_name1);
        TextView major = findViewById(R.id.zl_major1);
        TextView tel=findViewById(R.id.zl_tel1);
        TextView xy=findViewById(R.id.zl_xy1);
        TextView bj=findViewById(R.id.zl_bj1);

        User user=(User) getIntent().getSerializableExtra("user");

        nickName.setText(user.getId());
        stu.setText(user.getStuNo());
        name.setText(user.getUserName());
        major.setText(user.getStudent().getMajor());
        tel.setText(user.getTel());
        xy.setText(user.getStudent().getXy());
        bj.setText(user.getStudent().getBj());

//        nickName.setText(DataHelper.getShareData("userId"));
//        stu.setText(DataHelper.getShareData("stuNo"));
//        name.setText(DataHelper.getShareData("userName"));
//        major.setText(DataHelper.getShareData("major"));
//        tel.setText(DataHelper.getShareData("userAvl"));
//        xy.setText(DataHelper.getShareData("xy"));
//        bj.setText(DataHelper.getShareData("bj"));
    }
}
