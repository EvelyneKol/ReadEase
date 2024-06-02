package com.example.readease3.ui.writer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.readease3.R;

public class writer_home extends Fragment {

    private WriterViewModel mViewModel;
    private Button ebookButton;

    public static writer_home newInstance() {
        return new writer_home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.writer, container, false);

        // Initialize ebookButton
        ebookButton = rootView.findViewById(R.id.ebookbutton);

        showEbookForm();


        return rootView;
    }

    private void showEbookForm(){
        ebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AdOptionsActivity
                Navigation.findNavController(v).navigate(R.id.action_writer_home_to_ebook);
            }
        });
    }
}