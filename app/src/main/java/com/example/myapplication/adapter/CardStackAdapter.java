package com.example.myapplication.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Screen.MoviePageScreen;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<MovieInfo> items;
    private Context context;

    public CardStackAdapter(List<MovieInfo> items,Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_stack_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieInfo item = items.get(position);
        String imageUrl = "https://image.tmdb.org/t/p/w200" + item.poster_path;
        Log.d("CardStackAdapter", "imageUrl: " + imageUrl);
        Integer resId = DataManager.getInstance(context).getMovieImgSrc(position,items);
        if (resId != 0) {
            holder.imageView.setImageResource(resId);
        } else {
            holder.imageView.setImageResource(R.drawable.no_img_avai);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoviePageScreen.class);
            Bundle sendBundle = new Bundle();
            sendBundle.putSerializable("movie", items.get(position));
            intent.putExtras(sendBundle);
            startActivity(context,intent,null);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MovieInfo> items) {
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView; // Assuming your item_card.xml has a TextView with this ID.

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.movie_stack_image); // Make sure this ID exists in your item_card.xml layout.
        }
    }

}