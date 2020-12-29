package com.example.school.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.example.school.application.App;

public class DataHelper {

    private static Context context = App.getContext();

    //判断字符串是否为空
    public static Boolean isStringNull(String str){
        return str.equals("");
    }

    //获取输入
    public static String getInputString(EditText editText){
        return editText.getText().toString();
    }

    private static SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);

    private static SharedPreferences.Editor editor =sharedPreferences.edit();

    public static void putShareData(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static String getShareData(String key){
        return sharedPreferences.getString(key,"");
    }

}
