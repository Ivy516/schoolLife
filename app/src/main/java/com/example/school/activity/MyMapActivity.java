package com.example.school.activity;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.school.R;
import com.example.school.helper.LocationHelper;

public class MyMapActivity extends AppCompatActivity {

    private LocationHelper mLocationHelper;
    private MapView mapView;
    private BaiduMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        mLocationHelper = LocationHelper.getInstance( MyMapActivity.this,getApplicationContext());
        mLocationHelper.init();
        mapView = findViewById(R.id.map_view);
        mMap = mapView.getMap();
        mLocationHelper.setMap(mMap);
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MyMapActivity.this, "必须同意权限才能使用本程序",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    mLocationHelper.requestLocation();
                } else {
                    Toast.makeText(MyMapActivity.this, "发生未知错误", Toast.LENGTH_LONG).show();
                    return;
                }
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationHelper.destroy();
        mapView.onDestroy();
        mMap.setMyLocationEnabled(false);
    }


}
