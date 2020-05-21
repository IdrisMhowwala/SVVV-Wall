package com.example.idris_000.wall;

public class Adddata {
    String Name;
    String Msg;
    String Enroll;

    public Adddata(String name,String msg,String enroll)
    {
        Name = name;
        Msg = msg;
        Enroll = enroll;
    }
    public Adddata()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getEnroll() {
        return Enroll;
    }

    public void setEnroll(String enroll) {
        Enroll = enroll;
    }

    public String toString()
    {
        return this.Name+":- "+this.Msg;
    }
}
