package com.example.mymovieapp.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovieapp.R;
import com.example.mymovieapp.Screen.MoviePageScreen;
import com.example.mymovieapp.models.MovieInfo;

import java.util.List;

public class MovieStackAdapter extends RecyclerView.Adapter<MovieStackAdapter.ViewHolder> {
    private List<MovieInfo> items;
    private Context context;

    public MovieStackAdapter(List<MovieInfo> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MovieStackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_stack_card, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieStackAdapter.ViewHolder holder, int position) {
        MovieInfo movie = items.get(position);

        String url = "https://image.tmdb.org/t/p/w500" + movie.poster_path;
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.no_img_avai)
                .into(holder.img);

        holder.img.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoviePageScreen.class);
            Bundle sendBundle = new Bundle();
            sendBundle.putSerializable("movie", movie);
            intent.putExtras(sendBundle);
            startActivity(context, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MovieInfo> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.movie_stack_image);
        }
    }
}
