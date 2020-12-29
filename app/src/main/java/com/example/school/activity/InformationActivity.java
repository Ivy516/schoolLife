package com.example.school.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.httpConnect.LoginHttpConnection;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class InformationActivity extends AppCompatActivity {

    TextView title;
    int userId,friendId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        title=findViewById(R.id.tv_title);
        title.setText("查询");

        TextView stu = findViewById(R.id.zl_id1);
        TextView name=findViewById(R.id.zl_name1);
        TextView major = findViewById(R.id.zl_major1);
        TextView classId=findViewById(R.id.zl_class_id1);
        LinearLayout Class = findViewById(R.id.zl_l5);
        Button addFriend = findViewById(R.id.bt_add_friend);
        final ConstraintLayout constraintLayout = findViewById(R.id.con_layout);

        final Intent intent = getIntent();

        stu.setText(intent.getStringExtra("xh"));
        name.setText(intent.getStringExtra("name"));
        major.setText(intent.getStringExtra("collage"));
        classId.setText(intent.getStringExtra("classNo"));
        friendId = intent.getIntExtra("userId",0);
        SharedPreferences myData = getSharedPreferences("myData",MODE_PRIVATE);
        userId = myData.getInt("Id",0);

        Class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(InformationActivity.this,classActivity.class);
                intent1.putExtra("stuNo",intent.getStringExtra("xh"));
                startActivity(intent1);
            }
        });

        Button addFriends = findViewById(R.id.bt_add_friend);
        addFriends.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sendHttp();
                constraintLayout.removeView(v);
            }
        });

    }

    public void sendHttp(){
        final String url = "http://106.15.206.34:8080/friend/addFriend?userId="+userId+"&&friendId="+friendId;
        LoginHttpConnection connection = new LoginHttpConnection(url);
        connection.sendHttp(new LoginHttpConnection.Callback() {
            @Override
            public void response(String data) {
                Log.d(TAG, "response: ");
            }
        });
    }
}
