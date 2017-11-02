package com.eutechpro.movies.discovery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eutechpro.movies.GlideApp;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used for {@link RecyclerView} of Movies.
 */
@SuppressLint("LongLogTag")
class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MealsRecyclerViewAdapter";
    private final Context                 context;
    private final List<Movie>             dataSet;
    private       OnListItemClickListener onListItemClickListener;

    MoviesRecyclerViewAdapter(Context context) {
        this.dataSet = new ArrayList<>();
        this.context = context;
    }

    void setDataSet(List<Movie> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list_item, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Movie movie = dataSet.get(position);
        holder.name.setText(movie.getOriginalTitle());
        holder.rating.setText(String.valueOf(movie.getVoteAverage()));
        GlideApp.with(context)
                .load(movie.getVerticalImageUrl())
                .placeholder(R.drawable.movie_list_item_placeholder)
                .error(R.drawable.movie_list_item_transparent)
                .fallback(R.drawable.movie_list_item_transparent)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            if (onListItemClickListener == null){
                Log.e(TAG, "holder.itemView.setOnClickListener: Click will not work because you forgot to set OnItemClickListener! Look for a method setOnItemClickListener()!");
                return;
            }
            onListItemClickListener.onItemClicked(dataSet.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView  name;
        final ImageView image;
        final TextView  rating;

        ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            rating = view.findViewById(R.id.rating);
        }
    }

    void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    interface OnListItemClickListener {
        void onItemClicked(Movie movie);
    }
}
