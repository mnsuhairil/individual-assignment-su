package com.example.bmiassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class About extends AppCompatActivity {
    TextView githup,youtube,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.about);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.Exit:
                        //moveTaskToBack(true); //debug
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        return true;
                }

                return false;
            }
        });

        githup = findViewById(R.id.gitLink);
        githup.setMovementMethod(LinkMovementMethod.getInstance());
        youtube = findViewById(R.id.tubeLink);
        youtube.setMovementMethod(LinkMovementMethod.getInstance());
        email = findViewById(R.id.mailLink);
        email.setMovementMethod(LinkMovementMethod.getInstance());


    }
}