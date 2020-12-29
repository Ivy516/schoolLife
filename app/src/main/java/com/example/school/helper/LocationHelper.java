package com.example.school.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocationHelper {
    public LocationClient mLocationClient;
    private Context mAppContext;
    private Context mContext;
    private String mAddress;
    private Boolean isFirstLocate = false;
    private BaiduMap mMap;
    private static LocationHelper mLocationHelper;

    private LocationHelper(Context context, Context appContext) {
        mContext = context;
        mAppContext = appContext;
    }

    public static LocationHelper getInstance(Context context, Context appContext) {
        if (mLocationHelper == null) {
            synchronized (LocationHelper.class) {
                if (mLocationHelper == null) {
                    mLocationHelper = new LocationHelper(context, appContext);
                }
            }
        }
        return mLocationHelper;
    }

    public void setMap(BaiduMap map) {
        mMap = map;
    }

    public void init() {
        mLocationClient = new LocationClient(mAppContext);
        mLocationClient.registerLocationListener(new MyLocationListener());
        //申请权限
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE)
        != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            String[] permission = permissions.toArray(new String[permissions.size()]);
            ActivityCompat.requestPermissions((Activity) mContext, permission, 1);
        } else {
            requestLocation();
        }
    }

    //定位服务启动
    public void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    //每5秒更新一次位置
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        //定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public void destroy(){
        mLocationClient.stop();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
            bdLocation.getLocType() == BDLocation.TypeNetWorkLocation || mMap != null) {
                navigateTo(bdLocation);
            }

            StringBuilder address = new StringBuilder();
            address.append(bdLocation.getProvince())
                    .append(bdLocation.getCity())
                    .append(bdLocation.getDistrict())
                    .append(bdLocation.getStreet());
            mAddress = address.toString();
            Log.d("dd", "onReceiveLocation: " + mAddress);
        }
    }

    public String getAddress() {
        return mAddress;
    }

    public void navigateTo(BDLocation location) {
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        if (locationData != null && mMap != null)
        mMap.setMyLocationData(locationData);
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(16f);
            mMap.animateMapStatus(update);
            isFirstLocate = false;
        }
    }
}
