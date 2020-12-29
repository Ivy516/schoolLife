package com.example.school.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.data.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText stuNo,pwd,Name,Id,tel;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的数据
                String stu=stuNo.getText().toString();
                String pass=pwd.getText().toString();
                String name=Name.getText().toString();
                String id=Id.getText().toString();
                String telphone=tel.getText().toString();


                User user = new User();
                user.setId(id);
                user.setStuNo(stu);
                user.setPassword(pass);
                user.setUserName(name);
                user.setTel(telphone);

                //HttpConnection httpConnection =new HttpConnection();
                sendSignUp(user);

            }
        });

    }

    //获取布局的控件
    private void initView(){
        stuNo=findViewById(R.id.et_stu_no);
        pwd=findViewById(R.id.et_pass);
        Name=findViewById(R.id.et_name);
        Id=findViewById(R.id.et_id);
        tel=findViewById(R.id.et_tel);
        signUp=findViewById(R.id.bt_sign_up);
    }

    private void showResponse(String response){
        //String msg;
        try{
            JSONObject jsonObject = new JSONObject(response);
            //String status=jsonObject.getString("status");
            String msg=jsonObject.getString("msg");
            if(msg.equals("SUCCESS")){
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(SignUpActivity.this,"注册失败",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendSignUp(final User user) {
        //this.student=student;
        //开启线程发起网络请求
        new Thread(new Runnable() {
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("stuNo",user.getStuNo())
                            .add("userName",user.getUserName())
                            .add("userAvl",user.getTel())
                            .add("id",user.getId())
                            .add("password",user.getPassword())
                            .build();
                    Request request=new Request.Builder()
                            .url("http://106.15.206.34:8080/user/signup?")
                            .post(requestBody)
                            .build();

                    final Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.e("SignUpActivity", "onFailure: 注册失败" );
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Log.d("SignUpActivity", "onResponse: 注册成功");
                            String data = response.body().string();
                            showData(data);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void showData(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("SignUpActivity", "run: "+data);
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
