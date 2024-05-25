package com.example.readease3;
public class coupons {
    private String user;
    private String type;
    private String expiredate;
    private String used;

    public coupons(String user, String type, String expiredate, String used) {
        this.user = user;
        this.type = type;
        this.expiredate = expiredate;
        this.used = used;
    }

    public String getCoupontype() {
        return type;
    }



}
