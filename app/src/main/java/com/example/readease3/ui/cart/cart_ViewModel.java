package com.example.readease3.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class cart_ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public cart_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}