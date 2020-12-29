package com.example.school.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.adapter.MyFriendAdapter;
import com.example.school.data.User;
import com.example.school.httpConnect.OkHttpFriend;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyFriendActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    private static List<User> users=new ArrayList<>();
    private MyFriendAdapter adapter;
    private OkHttpFriend okHttpFriend;
    private static String url = "http://106.15.206.34:8080/friend/friendList";
    int userId;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);

        TextView title = findViewById(R.id.tv_title);
        title.setText("我的好友");
        SharedPreferences myData = getSharedPreferences("myData",MODE_PRIVATE);
        userId = myData.getInt("Id",0);
        //LinearLayout l = findViewById(R.id.l_layout);
//        l.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RongIM.getInstance().startPrivateChat(MyFriendActivity.this, "2017210270","dwx");
//                RongCallKit.startSingleCall(MyFriendActivity.this, "2017210270", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
//            }
//        });


        init();
    }

    private void init() {
        mContext = MyFriendActivity.this;
        recyclerView=findViewById(R.id.all_contact_listView);
        swipeRefreshLayout =findViewById(R.id.swipe_layout);
        okHttpFriend = new OkHttpFriend(url+"?userId="+userId);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetDatas();
                okHttpFriend.sendHttp(new OkHttpFriend.Callback() {
                    @Override
                    public void response(String data) {
                        showData(data);
                        adapter.setData(users);
                    }
                });
            }
        });

        adapter = new MyFriendAdapter(mContext,users);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

//    private List<User> createUsers(){
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setUserName("Ivy");
//            users.add(user);
//        }
//        return users;
//    }


//    @Override
//    public void onRefresh() {
//        swipeRefreshLayout.setRefreshing(true);
//        adapter.resetDatas();
//        sendHttp();
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String userId = users.get(position).getStuNo();
//        String name = users.get(position).getUserName();
//        RongIM.getInstance().startPrivateChat(this, userId,name);
//    }
//
//    public void sendHttp(){
//        final String url = "http://106.15.206.34:8080/friend/friendList?userId="+userId;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                OkHttpClient client = new OkHttpClient.Builder()
//                        .connectTimeout(10, TimeUnit.SECONDS)
//                        .readTimeout(10, TimeUnit.SECONDS)
//                        .build();
//
//                Request request=new Request.Builder()
//                        .url(url)
//                        .build();
//
//                final Call call=client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                        Log.e("myFriendActivity", "onFailure: 失败");
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        if(response.isSuccessful()){
//                            final String data = response.body().string();
//                            Log.d("myFriendActivity", "onResponse: "+data);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    showData(data);
//                                }
//                            });
//                        }
//                    }
//                });
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.updateList(users);
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//        }).start();
//    }
//
    private void showData(String data){
        Log.d("myFriendActivity", "showData: " + data);
        users.clear();
        try{
            JSONObject jsonObject = new JSONObject(data);
            JSONArray datas = jsonObject.getJSONArray("data");
            for(int i=0;i<datas.length();i++){
                JSONObject jsonObject1 = datas.getJSONObject(i);
                User user = new User();
                user.setStuNo(jsonObject1.getString("userStuNo"));
                user.setUserName(jsonObject1.getString("userName"));
                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    public void chatWithFriend(String targetUserId, String title) {
        RongIM.getInstance().startPrivateChat(MyFriendActivity.this, targetUserId,title);
                RongCallKit.startSingleCall(MyFriendActivity.this, targetUserId,
                        RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
            }
}

