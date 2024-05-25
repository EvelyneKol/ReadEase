package com.example.readease3;

import java.util.ArrayList;
import java.util.List;

public class cartManager {
    private static cartManager instance;
    private List<Cart> cartItems;

    private cartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized cartManager getInstance() {
        if (instance == null) {
            instance = new cartManager();
        }
        return instance;
    }

    public void addToCart(Cart cartItem) {
        cartItems.add(cartItem);
    }

    public void removeFromCart(Cart cartItem) {
        cartItems.remove(cartItem);
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }

    public float calculateTotalPrice() {
        float totalPrice = 0;
        for (Cart item : cartItems) {
            totalPrice += item.getSellingPrice();
        }
        return totalPrice;
    }
}

