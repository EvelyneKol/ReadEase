package com.example.readease3.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.readease3.R;
import com.example.readease3.databinding.ProfileBinding;

public class profile_fragment extends Fragment {

    private ProfileBinding binding;




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_home, container, false);
        profile_ViewModel profile_ViewModel = new ViewModelProvider(this).get(profile_ViewModel.class);
        binding = ProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textNotifications;
        profile_ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}