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
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.ui.detail.TvShowDetailsActivity;
import com.indramahkota.moviecatalogue.ui.utils.CustomDateFormat;
import com.indramahkota.moviecatalogue.ui.utils.CustomOnItemClickListener;

public class TvShowPagedListAdapter extends PagedListAdapter<TvShowEntity, TvShowPagedListAdapter.TvShowViewHolder> {
    private final Context mContext;

    public TvShowPagedListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        final TvShowEntity movie = getItem(position);

        if (movie != null) {
            if (!movie.getName().isEmpty()) {
                holder.txtTitle.setText(movie.getName());
            } else {
                holder.txtTitle.setText(mContext.getResources().getString(R.string.no_title));
            }

            if (!movie.getFirstAirDate().isEmpty()) {
                String date = movie.getFirstAirDate();
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

    public TvShowEntity getItemById(int swipedPosition) {
        return getItem(swipedPosition);
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPoster;
        private final TextView txtTitle;
        private final TextView txtRelease;
        private final TextView txtRating;
        private final TextView txtOverview;

        TvShowViewHolder(@NonNull View itemView) {
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
                Intent moveWithDataIntent = new Intent(mContext, TvShowDetailsActivity.class);
                moveWithDataIntent.putExtra(TvShowDetailsActivity.EXTRA_TV_SHOW_ID, id);
                mContext.startActivity(moveWithDataIntent);
            }));
        }
    }

    private static final DiffUtil.ItemCallback<TvShowEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TvShowEntity>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(TvShowEntity oldTvShow, TvShowEntity newTvShow) {
                    return oldTvShow.getName().equals(newTvShow.getName());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(TvShowEntity oldTvShow, @NonNull TvShowEntity newTvShow) {
                    return oldTvShow.equals(newTvShow);
                }
            };
}
