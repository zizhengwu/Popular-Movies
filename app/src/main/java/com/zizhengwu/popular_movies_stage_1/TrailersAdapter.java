package com.zizhengwu.popular_movies_stage_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
        notifyDataSetChanged();
    }

    private List<MovieTrailer> movieTrailers;
    private Movie movie;
    private enum Type {
        HEADER(0), ITEM(1);
        private final int value;
        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    };

    private Context context;

    private Context getContext() {
        return context;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView release_date;
        TextView vote_average;
        TextView overview;
        ImageView poster;

        public HeaderViewHolder(View itemView) {

            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            release_date = (TextView) itemView.findViewById(R.id.release_date);
            vote_average = (TextView) itemView.findViewById(R.id.vote_average);
            overview = (TextView) itemView.findViewById(R.id.overview);
            poster = (ImageView) itemView.findViewById(R.id.poster);
        }
    }

    class TrailerItemViewHolder extends RecyclerView.ViewHolder {
        TextView trailerNameTextView;

        public TrailerItemViewHolder(View itemView) {
            super(itemView);

            trailerNameTextView = (TextView) itemView.findViewById(R.id.trailer_name);
        }
    }


    public TrailersAdapter(Context context, Movie movie, List<MovieTrailer> movieTrailers) {
        this.context = context;
        this.movie = movie;
        this.movieTrailers = movieTrailers;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type.HEADER.getValue();
        }
        else {
            return Type.ITEM.getValue();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == Type.HEADER.getValue()) {
            View headerView = inflater.inflate(R.layout.header_detail_activity, parent, false);

            return new HeaderViewHolder(headerView);
        }
        else {
            View trailersView = inflater.inflate(R.layout.item_trailer, parent, false);

            return new TrailerItemViewHolder(trailersView);
        }
    }

    private MovieTrailer getItem(int position) {
        return movieTrailers.get(position-1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.title.setText(movie.title);
            headerViewHolder.release_date.setText(movie.release_date);
            headerViewHolder.overview.setText(movie.overview);
            headerViewHolder.vote_average.setText(movie.vote_average);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movie.poster_path).into(headerViewHolder.poster);
        }
        else if (holder instanceof TrailerItemViewHolder) {
            TrailerItemViewHolder trailerViewHolder = (TrailerItemViewHolder) holder;
            MovieTrailer movieTrailer = getItem(position);
            trailerViewHolder.trailerNameTextView.setText(movieTrailer.getName());
        }
        else {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public int getItemCount() {
        return movieTrailers.size() + 1;
    }
}
