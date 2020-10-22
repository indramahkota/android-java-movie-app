package com.indramahkota.moviecatalogue.ui.main.fragment.pagedlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.ui.detail.MovieDetailsActivity;
import com.indramahkota.moviecatalogue.ui.utils.CustomDateFormat;
import com.indramahkota.moviecatalogue.ui.utils.CustomOnItemClickListener;

public class MoviePagedListAdapter extends PagedListAdapter<MovieEntity, MoviePagedListAdapter.MovieViewHolder> {
    private final Context mContext;

    public MoviePagedListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final MovieEntity movie = getItem(position);

        if (movie != null) {
            if (!movie.getTitle().isEmpty()) {
                holder.txtTitle.setText(movie.getTitle());
            } else {
                holder.txtTitle.setText(mContext.getResources().getString(R.string.no_title));
            }

            if (!movie.getReleaseDate().isEmpty()) {
                String date = movie.getReleaseDate();
                String newDate = CustomDateFormat.formatDateFromString(date);
                holder.txtRelease.setText(newDate);
            } else {
                holder.txtRelease.setText(mContext.getResources().getString(R.string.no_release_date));
            }

            if (movie.getVoteAverage() != null) {
                holder.txtRating.setText(String.valueOf(movie.getVoteAverage()));
            } else {
                holder.txtRating.setText(mContext.getResources().getString(R.string.no_rating));
            }

            if (!movie.getOverview().isEmpty())
                holder.txtOverview.setText(movie.getOverview());
            else {
                holder.txtOverview.setText(mContext.getResources().getString(R.string.availability_overview));
            }

            String posterUrl = ApiConstant.BASE_URL_POSTER + movie.getPosterPath();
            Glide.with(mContext)
                    .load(posterUrl)
                    .placeholder(R.drawable.poster_background_loading)
                    .apply(new RequestOptions().override(200, 300))
                    .error(R.drawable.poster_background_error)
                    .transform(new CenterCrop(), new RoundedCorners(8))
                    .into(holder.imgPoster);

            holder.addListener(position, movie.getId());
        }
    }

    public MovieEntity getItemById(int swipedPosition) {
        return getItem(swipedPosition);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPoster;
        private final TextView txtTitle;
        private final TextView txtRelease;
        private final TextView txtRating;
        private final TextView txtOverview;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRelease = itemView.findViewById(R.id.txt_release_date);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            ImageView imgFavorite = itemView.findViewById(R.id.img_bookmark);
            imgFavorite.setVisibility(View.VISIBLE);
        }

        void addListener(int position, Long id) {
            itemView.setOnClickListener(new CustomOnItemClickListener(position, position1 -> {
                Intent moveWithDataIntent = new Intent(mContext, MovieDetailsActivity.class);
                moveWithDataIntent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, id);
                mContext.startActivity(moveWithDataIntent);
            }));
        }
    }

    private static final DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieEntity>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(MovieEntity oldMovie, MovieEntity newMovie) {
                    return oldMovie.getTitle().equals(newMovie.getTitle());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(MovieEntity oldMovie, @NonNull MovieEntity newMovie) {
                    return oldMovie.equals(newMovie);
                }
            };
}
