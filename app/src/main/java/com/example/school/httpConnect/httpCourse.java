package com.example.school.httpConnect;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class httpCourse {
    String http;

    public httpCourse(String http) {
        this.http=http;
    }

    public interface Callback{
        void finish(String data);
    }

public void sendCourseHttp(final Callback callback){
    Log.d(TAG, "courseHttp: "+http);
    new Thread(new Runnable() {
        @Override
        public void run() {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            InputStream inputStream;
            try{
                URL url=new URL(http);
                connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                inputStream=connection.getInputStream();
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer data = new StringBuffer();
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    data.append(line);
                }
                inputStream.close();
                if (callback !=null){
                    callback.finish(data.toString());
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (bufferedReader!=null){
                    try{
                        bufferedReader.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }


        }
    }) .start();
}
}
