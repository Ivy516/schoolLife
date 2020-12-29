package com.example.school.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.adapter.GoodsAdapter;
import com.example.school.data.Goods;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class SecondaryMarketActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Goods> goodsList = new ArrayList<>();
    GoodsAdapter goodsAdapter;
    int lastVisibleItem;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_market);

        recyclerView=findViewById(R.id.secondary_market);
        textView=findViewById(R.id.tv_title);
        textView.setText("二手商店");

        sendHttp();
        goodsAdapter = new GoodsAdapter(this,goodsList,goodsList.size()>0? true : false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(goodsAdapter);

        swipeRefreshLayout=findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                goodsAdapter.resetDatas();
                sendHttp();
            }
        });

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            //int lastVisibleItem;
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState==RecyclerView.SCROLL_STATE_IDLE){
//                    if (goodsAdapter.isFadeTips()==false&&lastVisibleItem+1==goodsAdapter.getItemCount()){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                sendHttp();
//                            }
//                        });
//                    }
//                    if (goodsAdapter.isFadeTips()==true&&lastVisibleItem+2==goodsAdapter.getItemCount()){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                sendHttp();
//                            }
//                        });
//                    }
//                }
//                if (goodsAdapter==null){
//                    if (newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==goodsAdapter.getItemCount()){
//                        sendHttp();
//                        //Log.d(TAG, "onScrollStateChanged: "+page);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
//            }
//        });



    }

    public void sendHttp(){
        final String url = "http://106.15.206.34:8080/goods/selectAll";
        //Log.d(TAG, "sendhttp: "+page);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.e("fragment1", "onFailure: 连接失败");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Log.d("fragment1", "onResponse: 连接成功");
                            String data = response.body().string();
                            showData(data);
                        }
                    });
            }

    private void showData(String data) {
        try{
            Log.d("second", "showData: data = " + data);
            JSONObject jsonObject = new JSONObject(data);
            boolean success = jsonObject.getBoolean("success");
            if(success){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    Goods goods = new Goods();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    goods.setDescription(jsonObject1.getString("gdescription"));
                    goods.setStuNo(jsonObject1.getString("gstuNo"));
                    goods.setName(jsonObject1.getString("gname"));
                    goods.setPrice(jsonObject1.getInt("gprice"));
                    goods.setTime(jsonObject1.getString("gtime"));
                    String temp = jsonObject1.getString("gpicture").substring(23);
                    goods.setPicture(base64ToBitmap(temp));
                    goodsList.add(goods);
                }
            }
            goodsAdapter.setData(goodsList);
            swipeRefreshLayout.setRefreshing(false);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Bitmap base64ToBitmap(String base64String) {
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
