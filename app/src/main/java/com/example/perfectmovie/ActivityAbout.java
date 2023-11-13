package com.example.perfectmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAbout extends AppCompatActivity {
    String trailer;
    WebView wv;
    ImageView poster;
    ListView listStaff;
    ApiInt apiIntFilms;
    ApiInt apiIntStaff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboute);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String img = intent.getStringExtra("img");

        apiIntFilms = ServiceBuilder.buildRequestFilms().create(ApiInt.class);
        assert id != null;
        Call<RootTrailer> getTrailer = apiIntFilms.getTrailer(id);
        getTrailer.enqueue(new Callback<RootTrailer>() {
            @Override
            public void onResponse(Call<RootTrailer> call, Response<RootTrailer> response) {
                ArrayList<ItemTrailer> items = response.body().items;
                poster = findViewById(R.id.poster);

                Picasso.with(getApplicationContext()).load(img).into(poster);
                if (items.size() > 0){
                    trailer = response.body().items.get(0).url;

                    wv = findViewById(R.id.wv);


                    wv.loadUrl(trailer);
                    wv.getSettings().setJavaScriptEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<RootTrailer> call, Throwable t) {

            }
        });

        apiIntStaff = ServiceBuilder.buildRequestStaff(id).create(ApiInt.class);
        Call<ArrayList<RootStaff>> staff = apiIntStaff.getStaff();
        listStaff = findViewById(R.id.actors);

    staff.enqueue(new Callback<ArrayList<RootStaff>>() {
        @Override
        public void onResponse(Call<ArrayList<RootStaff>> call, Response<ArrayList<RootStaff>> response) {
            ArrayList<RootStaff> responseStaff = response.body();

            String[] actors = new String[responseStaff.size()];

            for (int i = 0; i < responseStaff.size(); i++){
                actors[i] = responseStaff.get(i).nameRu;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.item_colored, actors);

            listStaff.setAdapter(adapter);

        }

        @Override
        public void onFailure(Call<ArrayList<RootStaff>> call, Throwable t) {

        }
    });


    }
}