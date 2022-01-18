package com.example.karunakitchen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Order extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    Button whatsapp;
    Button call;
    EditText name;
    EditText email;
    EditText phone;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

//        Initializing Variables
        whatsapp = findViewById(R.id.button2);
        call = findViewById(R.id.button3);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.order);
        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        address = findViewById(R.id.editTextTextPostalAddress);

//        Checking whether the textFields are empty or not
        name.addTextChangedListener(whatsAppTextWatcher);
        email.addTextChangedListener(whatsAppTextWatcher);
        phone.addTextChangedListener(whatsAppTextWatcher);
        address.addTextChangedListener(whatsAppTextWatcher);
        whatsapp.setEnabled(false);

//        Number to call and whatsApp:
        String number = "+91 96584 81481";

//        Calling:
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  Asking for phone permission:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        String s = "tel:" + number;
                        Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse(s));
                        startActivity(call);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                } else {
                    Toast.makeText(Order.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    String s = "tel:" + number;
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse(s));
                    startActivity(call);
                }
            }
        });

//      Sending message on WhatsApp:
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean installed = isAppInstalled("com.whatsapp");

                if (installed = false) {
                    Toast.makeText(Order.this, "Please install WhatsApp!", Toast.LENGTH_SHORT).show();
                } else {

                    Intent text = new Intent(Intent.ACTION_VIEW);
                    String nameText = name.getText().toString().trim();
                    String emailText = email.getText().toString().trim();
                    String phoneText = phone.getText().toString().trim();
                    String addressText = address.getText().toString().trim();
                    String message = "Hi, my name is " + nameText + ".\nI'd like to know more about your menu and services.\nYou may contact me at " + phoneText + " or " + emailText + "\nYou can deliver my order at " + addressText + ".";
                    text.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + number + "&text=" + message));
                    startActivity(text);
                }
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.order:
                        return true;
                    case R.id.kitchen:
                        startActivity(new Intent(getApplicationContext(), Kitchen.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }


    //    TextWatcher to check whether the textFields are empty or not
    private TextWatcher whatsAppTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = name.getText().toString().trim();
            String emailInput = email.getText().toString().trim();
            String phoneInput = phone.getText().toString().trim();
            String addressInput = address.getText().toString().trim();
            whatsapp.setEnabled(!nameInput.isEmpty() && !emailInput.isEmpty() && !phoneInput.isEmpty() && !addressInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    //    Requesting Permission for calling
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Order.this, "Permission Granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Order.this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //  Checking whether WhatsApp is installed or not
    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean is_installed;
        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }

    //    Coding the back button action
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press
        startActivity(new Intent(getApplicationContext(), Kitchen.class));
        overridePendingTransition(0, 0);
    }
}