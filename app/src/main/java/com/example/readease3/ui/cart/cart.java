package com.example.readease3.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.widget.EditText;
import android.widget.Switch;

import com.example.readease3.R;
import com.example.readease3.databinding.CartBinding;

public class cart extends Fragment {

    private CartBinding binding;

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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}