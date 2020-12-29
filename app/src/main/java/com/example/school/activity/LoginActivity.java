package com.example.school.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.data.Student;
import com.example.school.data.User;
import com.example.school.helper.DataHelper;
import com.example.school.httpConnect.LoginHttpConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "loginActivity";
    EditText id;
    EditText passWord;
    Button login;
    TextView signUp;
    String token="jUBuy1gVXxwPF9aXH7WRJJXiEll0WAcN@b5gu.cn.rongnav.com;b5gu.cn.rongcfg.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void showData(String string) {
        try{
            JSONObject jsonObject = new JSONObject(string);
            String status=jsonObject.getString("success");
            JSONObject data=jsonObject.getJSONObject("data");
            JSONObject stu=data.getJSONObject("stu");
            JSONObject user =data.getJSONObject("user");
            JSONObject token = data.getJSONObject("token");
            if(status.equals("true")){
                User mUser = new User();
                Student student = new Student();
                student.setBj(stu.getString("bj"));
                student.setMajor(stu.getString("zym"));
                student.setName(stu.getString("xm"));
                student.setStuNo(stu.getString("stuNo"));
                student.setXy(stu.getString("yxm"));
                student.setId(stu.getInt("id"));
                Log.d(TAG, "showData: "+stu.getString("bj"));

                mUser.setId(user.getString("userAccount"));
                mUser.setTel(user.getString("userAvl"));
                mUser.setUserName(user.getString("userName"));
                mUser.setPassword(user.getString("userPwd"));
                mUser.setStuNo(user.getString("userStuNo"));
                mUser.setToken(token.getString("token"));
                mUser.setStudent(student);

                SharedPreferences.Editor editor=getSharedPreferences("myData",MODE_PRIVATE).edit();
                editor.putString("token",token.getString("token"));
                editor.putString("bj",stu.getString("bj"));
                editor.putString("major",stu.getString("zym"));
                editor.putString("name",stu.getString("xm"));
                editor.putString("stuNo",stu.getString("stuNo"));
                editor.putString("xy",stu.getString("yxm"));
                editor.putInt("stuId",stu.getInt("id"));
                editor.putString("userId",user.getString("userAccount"));
                editor.putString("userAvl",user.getString("userAvl"));
                editor.putString("userName",user.getString("userName"));
                editor.putString("Password",user.getString("userPwd"));
                editor.putInt("Id",user.getInt("userId"));
                editor.apply();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                finish();
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
            else {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }


    }

    //获取控件
    public void initView() {
        id = findViewById(R.id.et_stuNo);
        passWord = findViewById(R.id.et_pwd);
        login = findViewById(R.id.bt_login);
        signUp=findViewById(R.id.tv_sign_up);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
        signUp.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                String mId = id.getText().toString();
                String mPassWord = passWord.getText().toString();

                if (mId.equals("")||mPassWord.equals("")){
                    Toast.makeText(this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginHttpConnection httpConnection = new LoginHttpConnection("http://106.15.206.34:8080/user/login?id="+mId+"&password="+mPassWord);
                httpConnection.sendHttp(new LoginHttpConnection.Callback() {
                    @Override
                    public void response(String data) {
                        if (data==null){
                            Toast.makeText(LoginActivity.this,"登录失败,请检查网络设置！",Toast.LENGTH_LONG).show();
                        }else {
                            showData(data);
                        }
                    }
                });
                break;
            case R.id.tv_sign_up:
                ComponentName componentName = new ComponentName(LoginActivity.this,SignUpActivity.class);
                Intent intent=new Intent();
                intent.setComponent(componentName);
                startActivity(intent);
                break;
        }

    }
}
