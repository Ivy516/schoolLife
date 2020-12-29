package com.example.school.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.school.R;

import io.rong.imkit.fragment.ConversationFragment;

public class ConversationActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        TextView textView = findViewById(R.id.tv_title);
        //textView.setText("聊天中...");
        String title=getIntent().getData().getQueryParameter("title");
        textView.setText(title);
    }
}
