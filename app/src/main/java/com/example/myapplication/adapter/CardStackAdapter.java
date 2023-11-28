package com.example.myapplication.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.MovieInfo;

import java.util.List;
import com.bumptech.glide.Glide;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<MovieInfo> items;

    public CardStackAdapter(List<MovieInfo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieInfo item = items.get(position);
        String imageUrl = "https://image.tmdb.org/t/p/w400" + item.poster_path;
        Uri uri = Uri.parse(imageUrl);
        Log.d("CardStackAdapter", "imageUrl: " + imageUrl);
        holder.imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // Assuming your item_card.xml has a TextView with this ID.

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view); // Make sure this ID exists in your item_card.xml layout.
        }
    }
}