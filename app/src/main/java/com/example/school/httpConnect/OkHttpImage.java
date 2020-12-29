package com.example.school.httpConnect;

import android.util.Log;

import com.example.school.data.Goods;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.school.activity.SendGoodsActivity.TAG;

public class OkHttpImage {
    private static String url;

    public OkHttpImage(String url) {
        this.url = url;
    }

    public void uploadImage(final Goods goods, final File imageFile) {
                Log.d("dddd", "run: " + imageFile);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", goods.getName())
                        .addFormDataPart("price", String.valueOf(goods.getPrice()))
                        .addFormDataPart("description", goods.getDescription())
                        .addFormDataPart("stuNo", String.valueOf(goods.getStuNo()))
                        .addFormDataPart("img", "output_image.jpg",
                                RequestBody.create(MediaType.parse("image/jpeg"), imageFile))
                        .build();
                Request request = new Request
                        .Builder()
                        .post(requestBody)
                        .url(url)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d(TAG, "onFailure: 上传失败！" + call.toString());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d(TAG, "onResponse: " + true);
                    }
                });
            }
}
