package com.example.m213_tp_15_sharedpref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {


    TextView txt;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt = findViewById(R.id.user);

        btn = findViewById(R.id.button2);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String n = sh.getString("nom", "");
        String p = sh.getString("prenom", "");

        txt.setText(n+" "+p);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();

                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String n = sh.getString("nom", "");
        if(n.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Login please", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }

    }
}