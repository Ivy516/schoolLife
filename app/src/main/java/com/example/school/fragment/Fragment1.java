package com.example.school.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.school.R;
import com.example.school.activity.DongTaiActivity;
import com.example.school.adapter.CardRecyclerViewAdapter;
import com.example.school.data.Card;
import com.example.school.java.EndLessRecyclerOnScrollListener;

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

public class Fragment1 extends Fragment {
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static List<Card> cards = new ArrayList<>();
    CardRecyclerViewAdapter adapter;
    static int page = 0;
    Boolean success = true;
    LinearLayoutManager linearLayoutManager;
    int lastVisibleItem; //加载最后时的索引
    public final static int UPDATE = 0;
    public final static int REFLASH = 1;
    private static int state;
    private boolean hasMore = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        recyclerView = view.findViewById(R.id.rec_card_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sendhttp(0);
        //adapter = new CardRecyclerViewAdapter(this,cards);
        initAdapter();

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                state = UPDATE;
                cards.clear();
                sendhttp(0);
            }
        });

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            //int lastVisibleItem;
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == adapter.getItemCount()) {
//                    state = REFLASH;
//                    sendhttp(page++);
//                }
////                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
////                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
////                        getActivity().runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                sendhttp(++page);
////                            }
////                        });
////                    }
////                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
////                        getActivity().runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                sendhttp(++page);
////                            }
////                        });
////                    }
////                }
////                if (adapter == null) {
////                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
////                        sendhttp(++page);
////                        Log.d(TAG, "onScrollStateChanged: " + page);
////                    }
////                }
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                if (layoutManager instanceof LinearLayoutManager) {
//                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
//                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
//                    int last = manager.findLastCompletelyVisibleItemPosition();
//                    lastVisibleItem = firstVisibleItem+(last-firstVisibleItem)+1;
//                }
//                //lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//            }
//        });

        recyclerView.setAdapter(adapter);

        FloatingActionButton fat = view.findViewById(R.id.fat);
        fat.show();

        if (recyclerView != null) {
            ViewGroup parentViewGroup = (ViewGroup) view;
            if (parentViewGroup != null) {
                parentViewGroup.removeView(recyclerView);
            }
        }
        fat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DongTaiActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initAdapter(){
        adapter = new CardRecyclerViewAdapter(this,cards);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndLessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadstate(adapter.LOADING);

                if (hasMore){
                    state = UPDATE;
                    page++;
                    sendhttp(page);
                    adapter.setLoadstate(adapter.LOADING_COMPLETE);
                }else{
                    adapter.setLoadstate(adapter.LOADING_END);
                }
            }
        });
    }

    public void sendhttp(int page) {
        final String url = "http://106.15.206.34:8080/dynamics/index?page=" + page;
        Log.d(TAG, "sendhttp: " + page);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    final Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
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

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Message message = handler.obtainMessage();
                    message.what = state;
                    message.obj = cards;
                    handler.sendMessage(message);
                    Log.d(TAG, "run: state = "+state);
                }
            }
        }).start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE:
                    cards = (ArrayList<Card>) msg.obj;
                    adapter.setList(cards);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case REFLASH:
                    Log.d(TAG, "handleMessage: state = "+state);
                    cards = (ArrayList<Card>) msg.obj;
                    adapter.setList(cards);
                    break;
                default:
                    break;
            }
        }
    };


    public void showData(String data) {
        try {
            //cards.clear();
            JSONObject jsonObject = new JSONObject(data);
            success = jsonObject.getBoolean("success");
            if (success) {
                hasMore = true;
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Card card = new Card();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject dynamics = jsonObject1.getJSONObject("dynamics");
                    String content = dynamics.getString("content");
                    Long time = dynamics.getLong("gmtCreate");
                    int id = dynamics.getInt("id");
                    String userName = dynamics.getString("userName");
                    String location = dynamics.getString("location");
                    card.setContent(content);
                    card.setTime(ft.format(new Date(time)));
                    card.setName(userName);
                    card.setAvaId(R.drawable.ic_my);
                    card.setId(id);
                    card.setLocation(location);
                    card.setLike(false);
                    cards.add(card);
                }
            }else{
                hasMore = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.d(TAG, "showData: "+cards.size());

    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        sendhttp(0);
//        adapter = new CardRecyclerViewAdapter(getContext(), cards);
//        Log.d(TAG, "onActivityCreated: " + cards.size());
//        // adapter.setData(cards);
//        linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
//    }

}
