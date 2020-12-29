package com.example.school.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable{
    private int no;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    private String id;
    private String userName;
    private String password;
    private String tel;
    private String stuNo;
    private Student student;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(id);
//        dest.writeString(tel);
//        dest.writeString(userName);
//        dest.writeString(password);
//        dest.writeString(stuNo);
//        dest.writeValue(student);
//    }
//
//    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
//
//        @Override
//        public User createFromParcel(Parcel source) {
//            User user = new User();
//            user.id=source.readString();
//            user.tel=source.readString();
//            user.userName=source.readString();
//            user.password=source.readString();
//            user.stuNo=source.readString();
//            user.student= (Student) source.readValue(new ClassLoader(){});
//            return user;
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
}
