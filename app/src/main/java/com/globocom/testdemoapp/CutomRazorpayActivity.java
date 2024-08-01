package com.globocom.testdemoapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.globocom.testdemoapp.databinding.ActivityCutomRazorpayBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class CutomRazorpayActivity extends AppCompatActivity implements PaymentResultListener {
    private ActivityCutomRazorpayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCutomRazorpayBinding.inflate(getLayoutInflater());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        // Initialize Razorpay Checkout
        Checkout checkout = new Checkout();

        // Set your key ID here
        checkout.setKeyID("rzp_live_Ro5Hh9ADagIWnE");

        // Initialize the payment options
        try {
            JSONObject options = new JSONObject();

            // Set the merchant name
            options.put("name", "Merchant Name");

            // Set the payment description
            options.put("description", "Test Payment");

            // Set the order ID received from Razorpay backend
            options.put("order_id", "order_OZBxmoVOn1q2HV");

            // Set the currency and amount (in paisa)
            options.put("currency", "INR");
            options.put("amount", "100");

            // Open the Razorpay checkout form
            checkout.open(this, options);
        } catch (Exception e) {
            showAlertBox("Error in starting Razorpay Checkout");
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        // Handle the payment success
        showAlertBox("Payment Successful: " + razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int code, String response) {
        // Handle the payment failure
        showAlertBox("Payment failed: " + code + " " + response);
    }

    /**
     * Show Alert Dialog to show message
     * @param message
     */
    private void showAlertBox(String message) {
        AlertDialog dialog = new AlertDialog.Builder(CutomRazorpayActivity.this).create();
        dialog.setMessage(message);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}