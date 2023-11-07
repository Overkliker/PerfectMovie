package com.example.perfectmovie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInt {
    @Headers({
            "X-API-KEY: 3ddb1209-e9b2-41f7-b7e3-e09b0667d294"
    })
    @GET("/api/v2.2/films")
    Call<Root> getFilms();

    @Headers({
            "X-API-KEY: 3ddb1209-e9b2-41f7-b7e3-e09b0667d294"
    })
    @GET("/api/v2.2/films/{id}/videos")
    Call<RootTrailer> getTrailer(@Path("id") String id);
}
