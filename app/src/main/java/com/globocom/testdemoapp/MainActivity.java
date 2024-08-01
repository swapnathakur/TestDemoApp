package com.globocom.testdemoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.globocom.testdemoapp.databinding.ActivityMainBinding;
import com.globocom.testdemoapp.network.Constants;
import com.globocom.testdemoapp.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getButtonClicked().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer button) {
                switch (button) {
                    case Constants.BTN_GOOGLE_SIGN_IN:
                        startActivityOnTop(GoogleSignInActivity.class,true);
                        break;
                    case Constants.BTN_CUSTOM_RAZORPAY:
                        startActivityOnTop(CustomRazorpayActivity.class,true);
                        break;
                    case Constants.BTN_MVVM_API_CALL:
                        //startActivityOnTop(PostActivity.class,true);
                        break;
                }
            }
        });
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void startActivityOnTop(Class<?> cls, boolean finishCurrent) {
        Intent intent = new Intent(MainActivity.this, cls);
        if (finishCurrent) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
        if (finishCurrent) {
            finish();
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}