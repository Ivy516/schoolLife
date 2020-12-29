package com.example.school.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.data.Goods;
import com.example.school.httpConnect.LoginHttpConnection;
import com.example.school.httpConnect.OkHttpImage;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendGoodsActivity extends AppCompatActivity {

    EditText gname, gintroduce, gprice;
    Button insert, send;
    ImageView image;
    private Uri imageUri;
    private Goods goods = new Goods();
    static String name, introduce, time, picture;
    static int userId, p;
    String stuNo;
    private File outputImage;
    private static final String URL = "http://106.15.206.34:8080/goods/insert";
    public static final String TAG = "sendGoodsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_goods);

        gintroduce = findViewById(R.id.et_introduce);
        gname = findViewById(R.id.et_name);
        gprice = findViewById(R.id.et_price);
        insert = findViewById(R.id.bt_insert);
        image = findViewById(R.id.iv_image);
        send = findViewById(R.id.bt_send);

        SharedPreferences myData = getSharedPreferences("myData", MODE_PRIVATE);
        userId = myData.getInt("Id", 0);
        stuNo = myData.getString("stuNo", null);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(SendGoodsActivity.this,
                            "com.example.school.fileProvider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Log.d(TAG, "onClick: imageUri = " + imageUri);
//手动开启权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(SendGoodsActivity.this,
                        Manifest.permission.CAMERA)) {

                    ActivityCompat.requestPermissions(SendGoodsActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 0);

                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//                Date date = new Date(System.currentTimeMillis());
//                time = formatter.format(date);
                name = gname.getText().toString();
                String price = gprice.getText().toString();
                Log.d(TAG, "onCreate: time = " + time);
                if (price != null && !price.equals("")) {
                    p = Integer.parseInt(price);
                }
                Log.d(TAG, "onCreate: p = " + p);
                Log.d(TAG, "onCreate: name = " + name);
                Log.d(TAG, "onClick: picture = "+picture);
                Log.d(TAG, "onClick: stuNo = " + stuNo);
                introduce = gintroduce.getText().toString();
                goods.setName(name);
                goods.setPrice(Integer.parseInt(price));
                goods.setDescription(introduce);
                goods.setStuNo(stuNo);

                OkHttpImage okHttpImage = new OkHttpImage(URL);
                okHttpImage.uploadImage(goods, outputImage);
                finish();
                // System.out.println(formatter.format(date));
//                String url = "http://106.15.206.34:8080/goods/insert?gname=" + name + "&gprice=" + p + "&gdescription=" + introduce +
//                        "&gstock=1&gpicture=" + picture + "&guserid=" + userId;
//
//                LoginHttpConnection httpConnection = new LoginHttpConnection(url);
//                httpConnection.sendHttp(new LoginHttpConnection.Callback() {
//                    @Override
//                    public void response(String data) {
//                        Log.d(TAG, "response: ");
//                        if (data == null) {
//                            Toast.makeText(SendGoodsActivity.this, "发布失败,请检查网络设置！", Toast.LENGTH_LONG).show();
//                        } else {
//                            Log.d(TAG, "response: 发布成功！！！");
//                            finish();
//                        }
//                    }
//                });
                //finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "onActivityResult: requestCode = " + requestCode);
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture = converIconToString(bitmap);
                        //Log.d(TAG, "onActivityResult: picture = "+picture);
                        image.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public static String converIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1000) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
            Log.i(TAG, "Compress : " +baos.toByteArray().length+"");
        }
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

}
