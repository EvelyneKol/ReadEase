package com.example.readease3;
public class coupons {
    private String user;
    private String coupontype;
    private String expiredate;
    private String used;

    public coupons(String user, String coupontype, String expiredate, String used) {
        this.user = user;
        this.coupontype = coupontype;
        this.expiredate = expiredate;
        this.used = used;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user){this.user = user;}

    public String getCoupontype() {
        return coupontype;
    }
    public void setCoupontype(String coupontype){
        this.coupontype = coupontype;
    }

    public String getExpiredate() {
        return expiredate;
    }
    public void setExpiredate(String expiredate){
        this.expiredate = expiredate;
    }

    public String getUsed() {
        return used;
    }
    public void setUsed(String used){
        this.used = used;
    }


}
