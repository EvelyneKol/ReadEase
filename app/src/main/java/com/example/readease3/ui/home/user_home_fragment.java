package com.example.readease3.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.readease3.R;
import com.example.readease3.create_add;
import com.example.readease3.databinding.UserHomeBinding;


public class user_home_fragment extends Fragment {

    private UserHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        user_home_ViewModel userhomeViewModel =
                new ViewModelProvider(this).get(user_home_ViewModel.class);

        binding = UserHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the button by its ID
        Button CreateaddButton = root.findViewById(R.id.create_add);

        // Set OnClickListener for button_buy_books
        CreateaddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the Buy_Books activity
                Intent intent = new Intent(getActivity(), create_add.class);
                startActivity(intent);
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