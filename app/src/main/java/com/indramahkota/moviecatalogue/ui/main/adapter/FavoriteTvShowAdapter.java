package com.indramahkota.moviecatalogue.ui.main.adapter;

import android.content.Context;
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
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.ui.utils.CustomOnItemClickListener;

import java.util.List;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.CategoryViewHolder> {
    private final Context mContext;
    private final List<FavoriteTvShow> listTvShows;

    private List<FavoriteTvShow> getListTvShows() {
        return listTvShows;
    }

    public FavoriteTvShowAdapter(List<FavoriteTvShow> listTvShow, Context context) {
        this.listTvShows = listTvShow;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if(getListTvShows().get(position).getName() != null && !getListTvShows().get(position).getName().isEmpty()) {
            holder.txtTitle.setText(getListTvShows().get(position).getName());
        } else {
            holder.txtTitle.setText(mContext.getResources().getString(R.string.no_title));
        }

        if(getListTvShows().get(position).getFirstAirDate() != null && !getListTvShows().get(position).getFirstAirDate().isEmpty()) {
            holder.txtRelease.setText(getListTvShows().get(position).getFirstAirDate());
        } else {
            holder.txtRelease.setText(mContext.getResources().getString(R.string.no_release_date));
        }

        if(getListTvShows().get(position).getVoteAverage() != null) {
            holder.txtRating.setText(String.valueOf(getListTvShows().get(position).getVoteAverage()));
        } else {
            holder.txtRating.setText(mContext.getResources().getString(R.string.no_rating));
        }

        if(getListTvShows().get(position).getOverview() != null && !getListTvShows().get(position).getOverview().isEmpty())
            holder.txtOverview.setText(getListTvShows().get(position).getOverview());
        else {
            holder.txtOverview.setText(mContext.getResources().getString(R.string.availability_overview));
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
        private final TextView txtRating;
        private final TextView txtOverview;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRelease = itemView.findViewById(R.id.txt_release_date);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtOverview = itemView.findViewById(R.id.txt_overview);
        }

        void addListener(int position) {
            itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
                @Override
                public void onItemClicked(int position) {
                    /*int id = (int) (long)getListTvShows().get(position).getItemId();
                    Intent moveWithDataIntent = new Intent(mContext, TvShowDetailsActivity.class);
                    moveWithDataIntent.putExtra(TvShowDetailsActivity.EXTRA_TV_SHOW_ID, id);
                    mContext.startActivity(moveWithDataIntent);*/
                }
            }));
        }
    }
}
