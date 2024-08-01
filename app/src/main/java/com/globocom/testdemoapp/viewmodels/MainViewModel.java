package com.globocom.testdemoapp.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.globocom.testdemoapp.R;
import com.globocom.testdemoapp.network.Constants;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Integer> buttonClicked = new MutableLiveData<>();

    public void onButtonClick(View view) {
        if(view.getId() == R.id.button1) {
            buttonClicked.setValue(Constants.BTN_GOOGLE_SIGN_IN);
        } else if(view.getId() == R.id.button2) {
            buttonClicked.setValue(Constants.BTN_CUSTOM_RAZORPAY);
        } else if(view.getId() == R.id.button3) {
            buttonClicked.setValue(Constants.BTN_MVVM_API_CALL);
        } else {

        }
    }

    public MutableLiveData<Integer> getButtonClicked() {
        return buttonClicked;
    }
}
