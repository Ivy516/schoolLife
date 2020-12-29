package com.example.school.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.school.R;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChaXunActivity extends AppCompatActivity {

    public EditText id;
    public Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_xun);
        id=findViewById(R.id.et_chaxun);
        bt=findViewById(R.id.bt_chaxun);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stuNo=id.getText().toString();
                sendHttp(stuNo);
            }
        });

    }

    private void sendHttp(final String stuNo) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();

//                RequestBody requestBody= new FormBody.Builder()
//                        .add("code",stuNo)
//                        .build();

                Request request=new Request.Builder()
                        .url("http://106.15.206.34:8080/user/search?condition="+stuNo)
                        .build();

                final Call call=client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("loginActivity", "onFailure: 查询失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            final String data = response.body().string();
                            Log.d("loginActivity", "onResponse: "+data);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showData(data);
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    private void showData(String data) {
        Log.d("chaxun", "showData: " + data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            boolean seccess = jsonObject.getBoolean("success");
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            //JSONObject stu = jsonObject1.getJSONObject("stu");
            if (seccess) {
                JSONObject stu = jsonObject1.getJSONObject("stu");
                JSONObject user = jsonObject1.getJSONObject("user");
                Intent intent = new Intent(ChaXunActivity.this, InformationActivity.class);
                intent.putExtra("xh", stu.getString("stuNo"));
                intent.putExtra("name", stu.getString("xm"));
                intent.putExtra("classNo", stu.getString("bj"));
                intent.putExtra("collage", stu.getString("zym"));
                intent.putExtra("userId",user.getInt("userId"));
                startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
