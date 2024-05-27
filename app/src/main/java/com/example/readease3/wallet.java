package com.example.readease3;

public class wallet {
    private int userId;
    private double money;

    public wallet(int userId, double money) {
        this.userId = userId;
        this.money = money;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
