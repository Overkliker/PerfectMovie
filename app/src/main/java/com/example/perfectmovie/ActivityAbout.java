package com.example.perfectmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAbout extends AppCompatActivity {
    String trailer;
    WebView wv;
    ImageView poster;
    ApiInt apiInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboute);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String img = intent.getStringExtra("img");

        apiInt = ServiceBuilder.buildRequestFilms().create(ApiInt.class);
        assert id != null;
        Call<RootTrailer> getTrailer = apiInt.getTrailer(id);
        getTrailer.enqueue(new Callback<RootTrailer>() {
            @Override
            public void onResponse(Call<RootTrailer> call, Response<RootTrailer> response) {
                ArrayList<ItemTrailer> items = response.body().items;
                if (items.size() > 0){
                    trailer = response.body().items.get(0).url;

                    wv = findViewById(R.id.wv);
                    poster = findViewById(R.id.poster);

                    Picasso.with(getApplicationContext()).load(img).into(poster);

                    wv.loadUrl(trailer);
                    wv.getSettings().setJavaScriptEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<RootTrailer> call, Throwable t) {

            }
        });


    }
}