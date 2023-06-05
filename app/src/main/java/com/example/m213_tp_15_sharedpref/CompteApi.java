package com.example.m213_tp_15_sharedpref;

import android.graphics.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CompteApi {

    @GET("login.php")
    Call<List<Compte>> getComptes();
}
