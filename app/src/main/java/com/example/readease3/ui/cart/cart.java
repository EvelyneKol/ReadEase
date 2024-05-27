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
                    cartManager.purchaseItems(dbHandler, totalPrice, userId);

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
        float totalPrice = cartManager.getInstance().getTotalPrice();

        // Προσθήκη των τίτλων των αγγελιών στο layout
        for (Cart item : cartItems) {
            TextView textViewTitle = new TextView(getContext());
            textViewTitle.setText(item.getTitle());
            cartItemsLayout.addView(textViewTitle);
        }

        // Εμφάνιση του συνολικού ποσού
        totalPriceTextView.setText("Συνολικό ποσό: " + totalPrice);
        startPrice.setText("Αρχική Τιμή: " + totalPrice);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
