package com.example.school.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.school.R;
import com.example.school.helper.LocationHelper;
import com.example.school.httpConnect.LoginHttpConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DongTaiActivity extends AppCompatActivity {

    private static final String TAG = "DongTaiActivity";
    private EditText content;
    private Button send;
    String userName;
    int stuId;
    LocationHelper mLocationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_dong_tai);
        mLocationHelper = LocationHelper.getInstance(DongTaiActivity.this, getApplicationContext());
        mLocationHelper.init();

        SharedPreferences sharedPreferences=getSharedPreferences("myData",MODE_PRIVATE);
        stuId=sharedPreferences.getInt("stuId",0);
        userName=sharedPreferences.getString("userName","");

        initView();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c=content.getText().toString();
                String location = mLocationHelper.getAddress();
                Log.d(TAG, "onClick: " + location);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                String time = simpleDateFormat.format(new Date());

                String url = "http://106.15.206.34:8080/dynamics/publish?id="+stuId+"&&content="+c
                        +"&&img="+time+"&&name="+userName+"&&location="+location;
                LoginHttpConnection httpConnection=new LoginHttpConnection(url);
                httpConnection.sendHttp(new LoginHttpConnection.Callback() {
                    @Override
                    public void response(String data) {
                        showData(data);
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(DongTaiActivity.this, "必须同意权限才能使用本程序",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    mLocationHelper.requestLocation();
                } else {
                    Toast.makeText(DongTaiActivity.this, "发生未知错误", Toast.LENGTH_LONG).show();
                    return;
                }
            default:
                break;
        }
    }

    public void initView(){
        content = findViewById(R.id.bianji);
        send = findViewById(R.id.send);
    }

    private void showData(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            String status=jsonObject.getString("success");
            if (status.equals("true")){
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
