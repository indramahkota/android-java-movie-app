package com.indramahkota.moviecatalogue.ui.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CategoryViewHolder> {
    private final Context mContext;
    private final List<Cast> castList;

    private List<Cast> getListCasts() {
        return castList;
    }

    public CastAdapter(List<Cast> listCasts, Context context) {
        this.castList = listCasts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.txtName.setText(getListCasts().get(position).getName());
        String posterUrl = ApiConstant.BASE_URL_POSTER + getListCasts().get(position).getProfilePath();
        Glide.with(mContext)
                .load(posterUrl)
                .apply(new RequestOptions().override(200, 300))
                .error(R.drawable.poster_background_error)
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return getListCasts().size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPoster;
        private final TextView txtName;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_cast);
            txtName = itemView.findViewById(R.id.txt_cast);
        }
    }
}
