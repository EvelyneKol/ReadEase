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
    public String getUser() {
        return user;
    }
    public void setUser(String user){this.user = user;}

    public String getCoupontype() {
        return type;
    }
    public void setCoupontype(String type){
        this.type = type;
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
