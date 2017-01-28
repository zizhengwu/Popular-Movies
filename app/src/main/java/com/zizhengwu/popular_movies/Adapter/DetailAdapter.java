package com.zizhengwu.popular_movies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zizhengwu.popular_movies.Model.Movie;
import com.zizhengwu.popular_movies.Model.MovieReview;
import com.zizhengwu.popular_movies.Model.MovieTrailer;
import com.zizhengwu.popular_movies.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
        notifyDataSetChanged();
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
        notifyDataSetChanged();
    }

    private List<MovieTrailer> movieTrailers;
    private List<MovieReview> movieReviews;
    private Movie movie;
    private enum Type {
        HEADER(0), TRAILER(1), REVIEW(2);
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

    class ReviewItemViewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthorTextView;
        TextView reviewContentTextView;

        public ReviewItemViewHolder(View itemView) {
            super(itemView);

            reviewAuthorTextView = (TextView) itemView.findViewById(R.id.review_author);
            reviewContentTextView = (TextView) itemView.findViewById(R.id.review_content);
        }
    }

    public DetailAdapter(Context context, Movie movie, List<MovieTrailer> movieTrailers, List<MovieReview> movieReviews) {
        this.context = context;
        this.movie = movie;
        this.movieTrailers = movieTrailers;
        this.movieReviews = movieReviews;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type.HEADER.getValue();
        }
        else if (position <= movieTrailers.size()) {
            return Type.TRAILER.getValue();
        }
        else {
            return Type.REVIEW.getValue();
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
        else if (viewType == Type.TRAILER.getValue()) {
            View trailerView = inflater.inflate(R.layout.item_trailer, parent, false);

            return new TrailerItemViewHolder(trailerView);
        }
        else {
            View reviewView = inflater.inflate(R.layout.item_review, parent, false);

            return new ReviewItemViewHolder(reviewView);
        }
    }

    private MovieTrailer getTrailer(int position) {
        return movieTrailers.get(position-1);
    }

    private MovieReview getReview(int position) {
        return movieReviews.get(position-1-movieTrailers.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.title.setText(movie.getTitle());
            headerViewHolder.release_date.setText(movie.getRelease_date());
            headerViewHolder.overview.setText(movie.getOverview());
            headerViewHolder.vote_average.setText(movie.getVote_average());
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movie.getPoster_path()).into(headerViewHolder.poster);
        }
        else if (holder instanceof TrailerItemViewHolder) {
            TrailerItemViewHolder trailerItemViewHolder = (TrailerItemViewHolder) holder;
            MovieTrailer movieTrailer = getTrailer(position);
            trailerItemViewHolder.trailerNameTextView.setText(movieTrailer.getName());
        }
        else if (holder instanceof  ReviewItemViewHolder) {
            ReviewItemViewHolder reviewItemViewHolder = (ReviewItemViewHolder) holder;
            MovieReview movieReview = getReview(position);
            reviewItemViewHolder.reviewAuthorTextView.setText(movieReview.getAuthor());
            reviewItemViewHolder.reviewContentTextView.setText(movieReview.getContent());
        }
        else {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public int getItemCount() {
        return 1 + movieTrailers.size() + movieReviews.size();
    }
}
