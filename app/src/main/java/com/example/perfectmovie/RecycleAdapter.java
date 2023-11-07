package com.example.perfectmovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.ActivityAbout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Item> filmsList;

    public RecycleAdapter(Context context, ArrayList<Item> filmsList) {
        this.context = context;
        this.filmsList = filmsList;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.block_film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        Item film = filmsList.get(position);
        holder.url = film.posterUrl;
        holder.filmName.setText(film.nameRu);
        holder.filmDate.setText(Integer.toString(film.year));
        holder.id = film.kinopoiskId;


        Context context = holder.filmPoster.getContext();
        Picasso.with(context).load(film.posterUrl).into(holder.filmPoster);
    }

    @Override
    public int getItemCount() {
        return filmsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView filmName;
        TextView filmDate;
        String url;
        int id;
        ImageView filmPoster;
        public final static String EXTRA_ID = "id";
        public final static String EXTRA_IMAGE = "img";
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            filmName = itemView.findViewById(R.id.filmName);
            filmPoster = itemView.findViewById(R.id.filmImg);
            filmDate = itemView.findViewById(R.id.filmDate);

            itemView.setOnClickListener(new  View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent;
                    intent = new Intent(view.getContext(), ActivityAbout.class);
                    filmPoster.getDrawable();



                    intent.putExtra(EXTRA_ID, Integer.toString(id));
                    intent.putExtra(EXTRA_IMAGE, url);


                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);

                }
            });
        }
    }
}
