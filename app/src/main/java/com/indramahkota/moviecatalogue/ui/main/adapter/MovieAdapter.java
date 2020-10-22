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
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.ui.detail.MovieDetailsActivity;
import com.indramahkota.moviecatalogue.ui.utils.CustomDateFormat;
import com.indramahkota.moviecatalogue.ui.utils.CustomOnItemClickListener;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CategoryViewHolder> {
    private final Context mContext;
    private final List<MovieEntity> listMovies;

    private List<MovieEntity> getListMovies() {
        return listMovies;
    }

    public MovieAdapter(List<MovieEntity> listMovies, Context context) {
        this.listMovies = listMovies;
        this.mContext = context;
    }

    public void add(MovieEntity response) {
        listMovies.add(response);
        notifyItemInserted(listMovies.size() - 1);
    }

    public void addAll(List<MovieEntity> postItems) {
        for (MovieEntity response : postItems) {
            add(response);
        }
    }

    private void remove(MovieEntity postItems) {
        int position = listMovies.indexOf(postItems);
        if (position > -1) {
            listMovies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private MovieEntity getItem() {
        return listMovies.get(0);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {
        if (getListMovies().get(position).getTitle() != null && !getListMovies().get(position).getTitle().isEmpty()) {
            holder.txtTitle.setText(getListMovies().get(position).getTitle());
        } else {
            holder.txtTitle.setText(mContext.getResources().getString(R.string.no_title));
        }

        if (getListMovies().get(position).getReleaseDate() != null && !getListMovies().get(position).getReleaseDate().isEmpty()) {
            String date = getListMovies().get(position).getReleaseDate();
            String newDate = CustomDateFormat.formatDateFromString(date);
            holder.txtRelease.setText(newDate);
        } else {
            holder.txtRelease.setText(mContext.getResources().getString(R.string.no_release_date));
        }

        if (getListMovies().get(position).getVoteAverage() != null) {
            holder.txtRating.setText(String.valueOf(getListMovies().get(position).getVoteAverage()));
        } else {
            holder.txtRating.setText(mContext.getResources().getString(R.string.no_rating));
        }

        if (getListMovies().get(position).getOverview() != null && !getListMovies().get(position).getOverview().isEmpty())
            holder.txtOverview.setText(getListMovies().get(position).getOverview());
        else {
            holder.txtOverview.setText(mContext.getResources().getString(R.string.availability_overview));
        }

        if (getListMovies().get(position).getFavorite() != null && !getListMovies().get(position).getFavorite())
            holder.imgFavorite.setVisibility(View.GONE);
        else {
            holder.imgFavorite.setVisibility(View.VISIBLE);
        }

        String posterUrl = ApiConstant.BASE_URL_POSTER + getListMovies().get(position).getPosterPath();
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
        return getListMovies().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPoster;
        private final TextView txtTitle;
        private final TextView txtRelease;
        private final TextView txtRating;
        private final TextView txtOverview;
        private final ImageView imgFavorite;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRelease = itemView.findViewById(R.id.txt_release_date);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            imgFavorite = itemView.findViewById(R.id.img_bookmark);
        }

        void addListener(int position) {
            itemView.setOnClickListener(new CustomOnItemClickListener(position, listPosition -> {
                Long movieId = getListMovies().get(listPosition).getId();
                Intent moveWithDataIntent = new Intent(mContext, MovieDetailsActivity.class);
                moveWithDataIntent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movieId);
                mContext.startActivity(moveWithDataIntent);
            }));
        }
    }
}
