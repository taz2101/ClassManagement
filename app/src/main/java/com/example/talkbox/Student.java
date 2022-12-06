package com.example.talkbox;

import java.io.Serializable;

public class Student implements Serializable {

    private String studentName;
    private String mssv;

    private boolean absent;

    public Student(String studentName, String mssv)  {
        this.studentName = studentName;
        this.mssv = mssv;
        this.absent = false;
    }

    public Student(String studentName, String mssv, boolean absent)  {
        this.studentName= studentName;
        this.mssv = mssv;
        this.absent= absent;
    }
    public String getStudentName(String studentName){
        return  studentName;
    }
    public void setStudentName(String studentName){
        this.studentName=studentName;
    }
    public String getMssv(String mssv){
        return mssv;
    }
    public void setMssv(String mssv){
        this.mssv = mssv;
    }
    public boolean isAbsent() {
        return absent;
    }

    public void setActive(boolean absent) {
        this.absent = absent;
    }
    @Override
    public String toString() {
        return this.studentName +" ("+ this.mssv+")";
    }



}
