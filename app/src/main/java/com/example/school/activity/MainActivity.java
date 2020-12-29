package com.example.school.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.data.User;
import com.example.school.fragment.Fragment1;
import com.example.school.fragment.Fragment2;
import com.example.school.fragment.Fragment3;
import com.example.school.fragment.Fragment4;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private static final String TAG ="MainActivity.class" ;
    private RadioGroup radioGroup;
    private RadioButton rb1,rb2,rb3,rb4;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment fragment3;
    private Fragment4 fragment4;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=(User) getIntent().getSerializableExtra("user");
        linkRongCloud();
        bindView();
    }

    private void bindView(){
        RadioButton home = findViewById(R.id.tab1);
        RadioButton gongneng=findViewById(R.id.tab2);
        RadioButton lt=findViewById(R.id.tab3);
        RadioButton my=findViewById(R.id.tab4);

        Drawable drawable1 = getResources().getDrawable(R.drawable.tab_menu_home);
        drawable1.setBounds(1,10,70,80);
        Drawable drawable2=getResources().getDrawable(R.drawable.tab_menu_gn);
        drawable2.setBounds(1,10,70,80);
        Drawable drawable3 = getResources().getDrawable(R.drawable.tab_menu_lt);
        drawable3.setBounds(1,10,70,80);
        Drawable drawable4=getResources().getDrawable(R.drawable.tab_menu_my);
        drawable4.setBounds(1,10,70,80);


        home.setCompoundDrawables(null,drawable1,null,null);
        gongneng.setCompoundDrawables(null,drawable2,null,null);
        lt.setCompoundDrawables(null,drawable3,null,null);
        my.setCompoundDrawables(null,drawable4,null,null);

        radioGroup=findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);

        rb1=findViewById(R.id.tab1);
        rb2=findViewById(R.id.tab2);
        rb3=findViewById(R.id.tab3);
        rb4=findViewById(R.id.tab4);

        rb1.setChecked(true);
    }

    public void hideAllFragment(FragmentTransaction transaction){
        if(fragment1!=null){
            transaction.hide(fragment1);
        }
        if(fragment2!=null){
            transaction.hide(fragment2);
        }
        if(fragment3!=null){
            transaction.hide(fragment3);
        }
        if (fragment4!=null){
            transaction.hide(fragment4);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        TextView textView=findViewById(R.id.tv_title);
        switch (checkedId){
            case R.id.tab1:
                textView.setText("主页");
                if(fragment1==null){
                    fragment1=new Fragment1();
                    transaction.add(R.id.fragment_container,fragment1);
                }else{
                    transaction.show(fragment1);
                }
                break;
            case R.id.tab2:
                textView.setText("功能");
                if(fragment2==null){
                    fragment2=new Fragment2();
                    transaction.add(R.id.fragment_container,fragment2);
                }else{
                    transaction.show(fragment2);
                }
                break;
            case R.id.tab3:
                textView.setText("聊天");
                if(fragment3==null){
                    fragment3=setConversationView();
                    transaction.add(R.id.fragment_container,fragment3);
                }else{
                    transaction.show(fragment3);
                }
                break;
            case R.id.tab4:
                textView.setText("我的");
                if (fragment4==null){
                    fragment4=new Fragment4(user);
                    transaction.add(R.id.fragment_container,fragment4);
                }else{
                    transaction.show(fragment4);
                }
                break;
        }
        transaction.commit();
    }

    public void linkRongCloud(){
        RongIM.connect(user.getToken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "onSuccess: "+s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d(TAG, "onError: "+errorCode);
            }
        });
    }

    public Fragment setConversationView(){
        Fragment fragment = null;
        if (fragment==null){
            ConversationListFragment conversationListFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" +
                    this.getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .build();
            conversationListFragment.setUri(uri);
            fragment=conversationListFragment;
        }
        return fragment;
    }

}
