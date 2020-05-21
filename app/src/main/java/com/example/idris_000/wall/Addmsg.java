package com.example.idris_000.wall;

class Addmsg {
    String msg;

    public Addmsg(String msg) {
        this.msg = msg;
    }
    public Addmsg(){}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String toString()
    {
       return this.msg;
    }
}
