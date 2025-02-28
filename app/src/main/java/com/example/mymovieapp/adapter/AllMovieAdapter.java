package com.example.mymovieapp.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovieapp.R;
import com.example.mymovieapp.Screen.MoviePageScreen;
import com.example.mymovieapp.models.MovieInfo;

import java.util.List;

public class AllMovieAdapter extends RecyclerView.Adapter<AllMovieAdapter.ViewHolder> {
    private List<MovieInfo> items;
    private Context context;

    public AllMovieAdapter(List<MovieInfo> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.all_movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieInfo item = items.get(position);
        holder.title.setText(item.title);
        holder.year.setText(item.release_date);
        holder.rating.setText(((Double) item.vote_average).toString());

        String url = "https://image.tmdb.org/t/p/w500" + item.poster_path;
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.no_img_avai)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoviePageScreen.class);
            Bundle sendBundle = new Bundle();
            sendBundle.putSerializable("movie", item);
            intent.putExtras(sendBundle);
            startActivity(context, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView year;
        private TextView rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_img);
            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
            rating = itemView.findViewById(R.id.movie_rating);
        }
    }


}
