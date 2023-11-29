package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;

import java.util.List;
import java.util.zip.Inflater;

public class MoviesCardAdapter extends RecyclerView.Adapter<MoviesCardAdapter.ViewHolder> {
    private List<MovieInfo> items;
    private Context context;

    public MoviesCardAdapter(List<MovieInfo> items,Context context) {
        this.items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieInfo item = items.get(position);
        holder.title.setText(item.title);
        holder.year.setText(item.release_date);
        holder.imageView.setImageResource(DataManager.getInstance(context).getMovieImgSrc(position,items));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView year;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_img);
            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
        }
    }


}
