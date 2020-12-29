package com.example.school.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.data.Course;
import com.example.school.httpConnect.httpCourse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class classActivity extends AppCompatActivity {
    ArrayList<Course> courses = new ArrayList<>();
    public int gridHeight,gridWidth;
    private RelativeLayout layout;
    private RelativeLayout tmpLayout,m1,m2,m3,m4,m5,m6,m7;
    private int nowWeek;
    private TextView mChangeWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        SharedPreferences pre =getSharedPreferences("myData",MODE_PRIVATE);

        mChangeWeek=findViewById(R.id.tv_title);
        mChangeWeek.setText("第1周");
        nowWeek=mChangeWeek.getText().charAt(1);
        mChangeWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeWeek(v);
            }
        });
        tmpLayout=findViewById(R.id.monday);
        init();

//        Intent intent = getIntent();
//        String stuNo =intent.getStringExtra("stuNo");

        httpCourse hc= new httpCourse("http://106.15.206.34:9090/class/student?stuNo="+pre.getString("stuNo",""));
        hc.sendCourseHttp(new httpCourse.Callback() {
            @Override
            public void finish(String data) {
                showData(data);
            }
        });

        hand();

    }

    private void hand(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0){
                    gridHeight=msg.arg1;
                    gridWidth=msg.arg2;
                }
                Course cs;

                for (int i = 0; i <courses.size() ; i++) {
                    cs=courses.get(i);
                    if (cs.getMinWeek()<=nowWeek&&cs.getMaxWeek()>=nowWeek) {
                        addView(cs.getClassDayDate(), cs.getMin(), cs.getMax(), cs.getClassName()+"-"+cs.getSite());
                    }
                }
                super.handleMessage(msg);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                tmpLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        gridWidth=tmpLayout.getWidth();
                        gridHeight=tmpLayout.getHeight()/12;
                        Message message = new Message();
                        message.what = 0;
                        message.arg1 = gridHeight;
                        message.arg2 = gridWidth;
                        handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }


    private void init() {
        m1=findViewById(R.id.monday);
        m2=findViewById(R.id.tuesday);
        m3=findViewById(R.id.wednesday);
        m4=findViewById(R.id.thursday);
        m5=findViewById(R.id.friday);
        m6=findViewById(R.id.saturday);
        m7=findViewById(R.id.sunday);
    }

    private void remove(){
        m1.removeAllViews();
        m2.removeAllViews();
        m3.removeAllViews();
        m4.removeAllViews();
        m5.removeAllViews();
        m6.removeAllViews();
        m7.removeAllViews();
    }

    private TextView createTv(int start, int end, String text){
        TextView tv = new TextView(this);
        //指定高度和宽度
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth,gridHeight*(end-start+1));
        //指定位置
        tv.setY(gridHeight*(start-1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(text);
        return tv;
    }

    private void addView(int i,int start,int end,String text){
        TextView tv=new TextView(this);
        //TextView tv;
        switch (i){
            case 1:
                layout=findViewById(R.id.monday);
                break;
            case 2:
                layout=findViewById(R.id.tuesday);
                break;
            case 3:
                layout=findViewById(R.id.wednesday);
                break;
            case 4:
                layout=findViewById(R.id.thursday);
                break;
            case 5:
                layout=findViewById(R.id.friday);
                break;
            case 6:
                layout=findViewById(R.id.saturday);
                break;
            case 7:
                layout=findViewById(R.id.sunday);
                break;
        }
        //tv=createTv(start,end,text);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(gridWidth,gridHeight*(end-start+1));
        tv.setY(gridHeight*(start-1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(text);
        tv.setBackgroundColor(Color.argb(100,start*5,(start+end)*20,0));
        layout.addView(tv,params);
    }
    private void showData(String data){
        System.out.println(data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("class");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                Course course = new Course();

                course.setStuNo(jsonObject1.getString("stuNo"));
                course.setClassType(jsonObject1.getString("classType"));
                course.setClassName(jsonObject1.getString("className"));
                course.setRequired(jsonObject1.getString("classCategory"));
                course.setTeacher(jsonObject1.getString("classTeacher"));
                course.setClassDate(jsonObject1.getString("classDate"));
                course.setSite(jsonObject1.getString("classClassroom"));

                courses.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < courses.size(); i++) {
            Course cs;
            cs=courses.get(i);
            System.out.println("学号："+cs.getStuNo());
            System.out.println("课程："+cs.getClassName());
            System.out.println("课程类型："+cs.getClassType());
            System.out.println("教学老师："+cs.getTeacher());
            System.out.println("上课地点："+cs.getSite());
            System.out.println("时间：星期"+cs.getClassDayDate());
            System.out.println("最大节数："+cs.getMax());
            System.out.println("最小节数："+cs.getMin());
            System.out.println("最大周数："+cs.getMaxWeek());
            System.out.println("最小周数："+cs.getMinWeek());
        }
    }

    //切换当前窗口
    public void showChangeWeek(View v){
        View view = View.inflate(this,R.layout.change_week_layout,null);
        ListView listView = view.findViewById(R.id.weekList);

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <21 ; i++) {
            arrayList.add("第"+i+"周");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,arrayList);
        listView.setAdapter(adapter);
        view.measure(0,0);
        final PopupWindow pop = new PopupWindow(view,300,500,true);
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        int xOffSet=-(pop.getWidth()-v.getWidth())/2;
        pop.showAsDropDown(v,xOffSet,0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nowWeek=position+1;
                pop.dismiss();
                mChangeWeek.setText("第"+nowWeek+"周");
                remove();
                hand();
            }
        });

    }


//    private void titleInitViews(){
//        List<String> list = new ArrayList<>();
//        for (int i = 1; i <21 ; i++) {
//            list.add("第"+i+"周");
//        }
//        mChangeWeek.setAdapter(new ArrayAdapter<>(this,R.layout.activity_class,list));
//        mChangeWeek.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                AppCompatAutoCompleteTextView appCompatAutoCompleteTextView = (AppCompatAutoCompleteTextView) v;
//                if (hasFocus){
//                    appCompatAutoCompleteTextView.showDropDown();
//                }
//            }
//        });
//    }


}
