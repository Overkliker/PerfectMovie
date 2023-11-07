package com.example.perfectmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomFilms;
    RecyclerView listFilms;
    ApiInt apiInt;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomFilms = findViewById(R.id.filmDirectory);
        listFilms = findViewById(R.id.listFilms);
        pb = findViewById(R.id.loading);

        apiInt = ServiceBuilder.buildRequestFilms().create(ApiInt.class);
        nonSort(apiInt);

        bottomFilms.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                pb.setVisibility(View.VISIBLE);
                if (item.getItemId() == R.id.waitFilms){
                    nonSort(apiInt);
                    try {
                        TimeUnit.SECONDS.sleep((long)1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    pb.setVisibility(View.GONE);
                    return true;
                }
                else if (item.getItemId() == R.id.topFilms){
                    sort(apiInt);
                    try {
                        TimeUnit.SECONDS.sleep((long)1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    pb.setVisibility(View.GONE);
                    return true;
                }
                pb.setVisibility(View.GONE);
                return false;
            }
        });
    }



    public void nonSort(ApiInt apiInt){
        Call<Root> getFilms = apiInt.getFilms();
        getFilms.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                listFilms.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listFilms.setHasFixedSize(true);

                ArrayList<Item> films = response.body().getItems();
                System.out.println(films);
                RecycleAdapter adapter = new RecycleAdapter(getApplicationContext(), films);
                listFilms.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    public void sort(ApiInt apiInt){
        Call<Root> getFilms = apiInt.getFilms();
        getFilms.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                listFilms.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listFilms.setHasFixedSize(true);

                ArrayList<Item> films = response.body().getItems();
                ArrayList<Item> top = new ArrayList<Item>();

                for (int i = 0; i < films.size(); i++){
                    if (films.get(i).ratingKinopoisk > 9.3){
                        top.add(films.get(i));
                    }
                }
                System.out.println(films);
                RecycleAdapter adapter = new RecycleAdapter(getApplicationContext(), top);
                listFilms.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }
}