package com.example.karunakitchen;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class About extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final int TIME_DELAY = 3000;
    private static long back_pressed;
    TextView website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.about);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about:

                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(), Order.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.kitchen:
                        startActivity(new Intent(getApplicationContext(), Kitchen.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        website  = findViewById(R.id.textView5);
        website.setMovementMethod(LinkMovementMethod.getInstance());
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press
        startActivity(new Intent(getApplicationContext(),Kitchen.class));
        overridePendingTransition(0,0);
    }
}