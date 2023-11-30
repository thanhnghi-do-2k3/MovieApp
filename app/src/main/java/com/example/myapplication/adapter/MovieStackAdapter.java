package com.example.myapplication.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Screen.MoviePageScreen;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;

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

        Integer src = DataManager.getInstance(holder.itemView.getContext()).getMovieImgSrc(movie);
        if (src != 0) {
            holder.img.setImageResource(src);
        } else {
            holder.img.setImageResource(R.drawable.no_img_avai);
        }

        holder.img.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoviePageScreen.class);
            Bundle sendBundle = new Bundle();
            sendBundle.putSerializable("movie", movie);
            intent.putExtras(sendBundle);
            startActivity(context,intent,null);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.movie_stack_image);
        }
    }
}
