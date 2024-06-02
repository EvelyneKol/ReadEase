package com.example.readease3.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.readease3.Cart;
import com.example.readease3.DBHandler;
import com.example.readease3.cartManager;
import com.example.readease3.R;
import com.example.readease3.databinding.CartBinding;

import java.util.List;

public class cart extends Fragment {

    private CartBinding binding;

    private DBHandler dbHandler;
    private cartManager cartManager;
    private Button purchaseButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cart_ViewModel cartViewModel =
                new ViewModelProvider(this).get(cart_ViewModel.class);

        binding = CartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the Switch and EditText in the binding
        Switch switch1 = binding.getRoot().findViewById(R.id.switch1);
        EditText codeEditText = binding.getRoot().findViewById(R.id.codeEditText);

        // Set up the Switch listener
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                codeEditText.setVisibility(View.VISIBLE);
            } else {
                codeEditText.setVisibility(View.GONE);
            }
        });

        dbHandler = new DBHandler(getContext());
        cartManager = cartManager.getInstance();
        purchaseButton = root.findViewById(R.id.button7);

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the total price from totalPriceTextView
                TextView totalPriceTextView = root.findViewById(R.id.totalPriceTextView);
                float totalPrice = Float.parseFloat(totalPriceTextView.getText().toString().replace("Συνολικό ποσό: ", ""));

                int userId = 1; // Example buyer ID

                try {
                    // Attempt to purchase items in the cart
                    checkBalance(dbHandler, totalPrice, userId);

                    // Show success message
                    Toast.makeText(getContext(), "Επιτυχής αγορά!", Toast.LENGTH_SHORT).show();
                } catch (RuntimeException e) {
                    // Show insufficient funds message
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Εμφάνιση δεδομένων από το CartManager
        LinearLayout cartItemsLayout = root.findViewById(R.id.cartItemsLayout);
        TextView totalPriceTextView = root.findViewById(R.id.totalPriceTextView);
        TextView startPrice = root.findViewById(R.id.startprice);
        List<Cart> cartItems = cartManager.getInstance().getCartItems();

        // Προσθήκη των τίτλων των αγγελιών στο layout
        for (Cart item : cartItems) {
            TextView textViewTitle = new TextView(getContext());
            textViewTitle.setText(item.getTitle());
            cartItemsLayout.addView(textViewTitle);
        }

        // Υπολογισμός και εμφάνιση του συνολικού ποσού
        calculatePrice(totalPriceTextView, startPrice);

        return root;
    }

    private void calculatePrice(TextView totalPriceTextView, TextView startPrice) {
        float totalPrice = cartManager.getInstance().getTotalPrice();
        totalPriceTextView.setText("Συνολικό ποσό: " + totalPrice);
        startPrice.setText("Αρχική Τιμή: " + totalPrice);
    }

    private void checkBalance(DBHandler dbHandler, float totalPrice, int buyerId) {
        float walletBalance = dbHandler.getUserWalletBalance(buyerId);
        int pointsToAdd = (int) (totalPrice / 10) * 10; // 10 πόντοι για κάθε 10 ευρώ

        if (walletBalance >= totalPrice) {
            // Update buyer's wallet balance
            removePaymentAmount(dbHandler, buyerId, totalPrice);

            // Add points to buyer's points balance
            addUserPoints(dbHandler, buyerId, pointsToAdd);

            // Distribute the funds to sellers
            for (Cart item : cartManager.getInstance().getCartItems()) {
                int publisherId = item.getSellingPublisher();
                float itemPrice = item.getSellingPrice();
                addPaymentAmount(dbHandler, publisherId, itemPrice);

                // Save purchase in purchase table with price
                insertPurchase(dbHandler, buyerId, item.getSellingAdId(), itemPrice);

                // Delete item from selling_ad table
                deleteAd(dbHandler, item.getSellingAdId());
            }

            // Clear cart after purchase
            cartManager.getInstance().clearCart();
        } else {
            showNotEnoughMoney();
        }
    }

    private void addUserPoints(DBHandler dbHandler, int buyerId, int pointsToAdd) {
        dbHandler.addUserPoints(buyerId, pointsToAdd);
    }

    private void insertPurchase(DBHandler dbHandler, int buyerId, int sellingAdId, float itemPrice) {
        dbHandler.insertPurchase(buyerId, "Book", sellingAdId, itemPrice);
    }

    private void deleteAd(DBHandler dbHandler, int sellingAdId) {
        dbHandler.deleteSellingAd(sellingAdId);
    }

    private void addPaymentAmount(DBHandler dbHandler, int publisherId, float itemPrice) {
        float sellerWalletBalance = dbHandler.getUserWalletBalance(publisherId);
        dbHandler.updateUserWalletBalance(publisherId, sellerWalletBalance + itemPrice);
    }

    private void removePaymentAmount(DBHandler dbHandler, int buyerId, float totalPrice) {
        float walletBalance = dbHandler.getUserWalletBalance(buyerId);
        float newBalance = walletBalance - totalPrice;
        dbHandler.updateUserWalletBalance(buyerId, newBalance);
    }

    private void showNotEnoughMoney() {
        throw new RuntimeException("Το υπόλοιπο δεν επαρκεί για την αγορά.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
