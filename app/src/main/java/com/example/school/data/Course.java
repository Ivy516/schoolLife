package com.example.school.data;

import android.util.Log;

import java.util.ArrayList;

public class Course {
    private String stuNo;      //学号
    private String classType;   //课程类型
    private String className;   //课程名
    private String required;    //课程种类（选修、必修、实验）
    private String teacher;     //课程老师
    private String classDate;   //课程时间
    private String site;        //上课地点
    private ArrayList<Integer> courseWeekNumber = new ArrayList<>();    //周数
    private int courseDay=0;     //星期几上课
    private int courseLow=0;     //第几排
    private int Section=0;      //延续几节课
    private String courseln;    //单双周，1(单),2(双),3(单双)





    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassName() {
        String[] temp;
        temp=className.split("-",2);
        return temp[1];
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    //返回星期几上课
    public int getClassDayDate() {
        int day=Integer.parseInt(String.valueOf(classDate.charAt(2)));
        return day;
    }

    //返回最小节数
    public int getMin(){
        if ((classDate.indexOf("第")!=-1)&&(classDate.indexOf("-")!=-1)){
            String time = classDate.substring(classDate.indexOf("第")+1,classDate.indexOf("-"));
            return Integer.parseInt(time);
        }
        else{
            return 0;
        }
    }

    //返回最大节数
    public int getMax(){
        if ((classDate.indexOf("-")!=-1)&&(classDate.indexOf("节")!=-1)){
            String time = classDate.substring(classDate.indexOf("-")+1,classDate.indexOf("节"));
            return Integer.parseInt(time);
        }
        else{
            return 0;
        }
    }
//返回最大周数
    public int getMaxWeek(){
        String week;
        if(classDate.lastIndexOf("-")!=-1&&classDate.lastIndexOf("周")!=-1)
        {
            if (classDate.indexOf("单")!=-1||classDate.indexOf("双")!=-1)
            {
                week=classDate.substring(classDate.lastIndexOf("-")+1,classDate.indexOf("周"));
                return Integer.parseInt(week);
            }
            else if (classDate.indexOf("-",classDate.indexOf("-")+1)!=-1){
                week=classDate.substring(classDate.lastIndexOf("-")+1,classDate.lastIndexOf("周"));
                return Integer.parseInt(week);
            }
            else{
                week=classDate.substring(classDate.lastIndexOf(",")+1,classDate.lastIndexOf("周"));
                return Integer.parseInt(week);
            }
        }
        else{
            return 0;
        }
    }
    //返回最小周数
    public int getMinWeek(){
        if((classDate.indexOf("节")+1)!=-1&&classDate.indexOf("-",classDate.indexOf("节"))!=-1)
        {
            String week=classDate.substring(classDate.indexOf("节")+1,classDate.indexOf("-",classDate.indexOf("节")));
            return Integer.parseInt(week);
        }
        else if ((classDate.indexOf("节")+1)!=-1&&classDate.indexOf("周")!=-1){
            String week=classDate.substring(classDate.indexOf("节")+1,classDate.indexOf("周"));
            return Integer.parseInt(week);
        }
        else{
            return 0;
        }

    }
//判断是否是单周
    public Boolean isSingleWeek(){
        if (classDate.indexOf("单")!=-1){
            return true;
        }
        else {
            return false;
        }
    }
//判断是否是单周
    public Boolean isDoubleWeek(){
        if (classDate.indexOf("双")!=-1){
            return true;
        }
        else {
            return false;
        }
    }
//判断是否单双一起上
    public Boolean isAllWeek(){
        if (!isDoubleWeek()&&!isSingleWeek()){
            return true;
        }
        else{
            return false;
        }
    }


//    public String getClassWeekDate(){
//        String week=classDate.substring(classDate.indexOf("节")+1);
//        return week;
//    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}