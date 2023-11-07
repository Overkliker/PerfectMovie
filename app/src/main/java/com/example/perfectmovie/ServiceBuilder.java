package com.example.perfectmovie;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    private static String URL = "https://kinopoiskapiunofficial.tech/";


    private static Retrofit retrofit = null;

    static Retrofit buildRequestFilms(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder().
                baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClient).build();

        return retrofit;
    }
}
