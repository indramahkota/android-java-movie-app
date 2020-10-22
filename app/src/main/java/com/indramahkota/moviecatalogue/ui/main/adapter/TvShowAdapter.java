package com.indramahkota.moviecatalogue.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.CategoryViewHolder> {
    private final Context mContext;
    private final List<TvShowEntity> listTvShows;

    private List<TvShowEntity> getListTvShows() {
        return listTvShows;
    }

    public TvShowAdapter(List<TvShowEntity> listTvShows, Context context) {
        this.listTvShows = listTvShows;
        this.mContext = context;
    }

    public void add(TvShowEntity response) {
        listTvShows.add(response);
        notifyItemInserted(listTvShows.size() - 1);
    }

    public void addAll(List<TvShowEntity> postItems) {
        for (TvShowEntity response : postItems) {
            add(response);
        }
    }

    private void remove(TvShowEntity postItems) {
        int position = listTvShows.indexOf(postItems);
        if (position > -1) {
            listTvShows.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private TvShowEntity getItem() {
        return listTvShows.get(0);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (getListTvShows().get(position).getName() != null && !getListTvShows().get(position).getName().isEmpty()) {
            holder.txtTitle.setText(getListTvShows().get(position).getName());
        } else {
            holder.txtTitle.setText(mContext.getResources().getString(R.string.no_title));
        }

        if (getListTvShows().get(position).getFirstAirDate() != null && !getListTvShows().get(position).getFirstAirDate().isEmpty()) {
            String date = getListTvShows().get(position).getFirstAirDate();
            String newDate = CustomDateFormat.formatDateFromString(date);
            holder.txtRelease.setText(newDate);
        } else {
            holder.txtRelease.setText(mContext.getResources().getString(R.string.no_release_date));
        }

        if (getListTvShows().get(position).getVoteAverage() != null) {
            holder.txtRating.setText(String.valueOf(getListTvShows().get(position).getVoteAverage()));
        } else {
            holder.txtRating.setText(mContext.getResources().getString(R.string.no_rating));
        }

        if (getListTvShows().get(position).getOverview() != null && !getListTvShows().get(position).getOverview().isEmpty())
            holder.txtOverview.setText(getListTvShows().get(position).getOverview());
        else {
            holder.txtOverview.setText(mContext.getResources().getString(R.string.availability_overview));
        }

        if (getListTvShows().get(position).getFavorite() != null && !getListTvShows().get(position).getFavorite())
            holder.imgFavorite.setVisibility(View.GONE);
        else {
            holder.imgFavorite.setVisibility(View.VISIBLE);
        }

        String posterUrl = ApiConstant.BASE_URL_POSTER + getListTvShows().get(position).getPosterPath();
        Glide.with(mContext)
                .load(posterUrl)
                .placeholder(R.drawable.poster_background_loading)
                .apply(new RequestOptions().override(200, 300))
                .error(R.drawable.poster_background_error)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .into(holder.imgPoster);

        holder.addListener(position);
    }

    @Override
    public int getItemCount() {
        return getListTvShows().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPoster;
        private final TextView txtTitle;
        private final TextView txtRelease;
        private final TextView txtOverview;
        private final TextView txtRating;
        private final ImageView imgFavorite;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRelease = itemView.findViewById(R.id.txt_release_date);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            txtRating = itemView.findViewById(R.id.txt_rating);
            imgFavorite = itemView.findViewById(R.id.img_bookmark);
        }

        void addListener(int position) {
            itemView.setOnClickListener(new CustomOnItemClickListener(position, listPosition -> {
                Long tvShowId = getListTvShows().get(listPosition).getId();
                Intent moveWithDataIntent = new Intent(mContext, TvShowDetailsActivity.class);
                moveWithDataIntent.putExtra(TvShowDetailsActivity.EXTRA_TV_SHOW_ID, tvShowId);
                mContext.startActivity(moveWithDataIntent);
            }));
        }
    }
}
