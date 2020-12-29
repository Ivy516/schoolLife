package com.example.school.httpConnect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttpFriend {
    public String mUrl;
    public OkHttpFriend(String url) {
        mUrl = url;
    }
    public interface Callback{
        void response(String data);
    }

    public void sendHttp(final OkHttpFriend.Callback callback){
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection httpConnection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(mUrl);
                    httpConnection=(HttpURLConnection) url.openConnection();
                    httpConnection.setRequestMethod("GET");
                    httpConnection.setConnectTimeout(5000);
                    httpConnection.setReadTimeout(5000);
                    InputStream in=httpConnection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if (callback!=null){
                        callback.response(response.toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (reader!=null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (httpConnection!=null){
                        httpConnection.disconnect();
                    }
                }
            }
        }).start();
    }
}
