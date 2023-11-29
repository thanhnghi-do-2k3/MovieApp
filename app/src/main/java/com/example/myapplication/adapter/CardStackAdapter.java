package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.myapplication.R;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;

import java.util.List;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cards, parent, false);
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
            imageView = view.findViewById(R.id.image_view); // Make sure this ID exists in your item_card.xml layout.
        }
    }

}