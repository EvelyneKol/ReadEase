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

    public float getTotalPrice() {
        float total = 0;
        for (Cart item : cartItems) {
            total += item.getSellingPrice();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void purchaseItems(DBHandler dbHandler, float totalPrice, int buyerId) {
        float walletBalance = dbHandler.getUserWalletBalance(buyerId);
        int pointsToAdd = (int) (totalPrice / 10) * 10;// 10 πόντοι για κάθε 10 ευρώ

        if (walletBalance >= totalPrice) {
            // Update buyer's wallet balance
            float newBalance = walletBalance - totalPrice;
            dbHandler.updateUserWalletBalance(buyerId, newBalance);

            // Add points to buyer's points balance
            dbHandler.addUserPoints(buyerId, pointsToAdd);

            // Distribute the funds to sellers
            for (Cart item : cartItems) {
                int publisherId = item.getSellingPublisher();
                float itemPrice = item.getSellingPrice();
                float sellerWalletBalance = dbHandler.getUserWalletBalance(publisherId);
                dbHandler.updateUserWalletBalance(publisherId, sellerWalletBalance + itemPrice);

                // Save purchase in purchase table with price
                dbHandler.insertPurchase(buyerId, "Book", item.getSellingAdId(), itemPrice);

                // Delete item from selling_ad table
                dbHandler.deleteSellingAd(item.getSellingAdId());
            }

            // Clear cart after purchase
            clearCart();
        } else {
            throw new RuntimeException("Το υπόλοιπο δεν επαρκεί για την αγορά.");
        }
    }

}
