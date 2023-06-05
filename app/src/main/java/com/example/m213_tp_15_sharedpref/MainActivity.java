package com.example.m213_tp_15_sharedpref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText mail,pass;
    Button btn;
    List<Compte> comptes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        btn = findViewById(R.id.button);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String n = sh.getString("nom", "");

        if(!n.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Already Logged in", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), Home.class));

        }

        else {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://13.48.3.250/Rest_Login/")

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CompteApi cApi = retrofit.create(CompteApi.class);

            Call<List<Compte>> call = cApi.getComptes();

            call.enqueue(new Callback<List<Compte>>() {

                @Override
                public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {

                    comptes = response.body();

                    Toast.makeText(getApplicationContext(), "Success API connexion", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<List<Compte>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error contacting API", Toast.LENGTH_LONG).show();
                }
            });

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();


                    int state = 0;

                    for (Compte c : comptes) {

                        if ((c.getEmail().equals(mail.getText().toString())) && (c.getPass().equals(pass.getText().toString()))) {
                            state++;
                            myEdit.putString("nom", c.getNom());
                            myEdit.putString("prenom", c.getPrenom());
                            myEdit.apply();
                        }

                    }

                    if (state > 0) {


                        // write all the data entered by the user in SharedPreference and apply


                        startActivity(new Intent(getApplicationContext(), Home.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}