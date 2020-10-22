package com.indramahkota.moviecatalogue.ui.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Genres;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.CategoryViewHolder> {
    private final Context mContext;
    private final List<Genres> genreList;

    private List<Genres> getListGenres() {
        return genreList;
    }

    public GenreAdapter(List<Genres> listGenres, Context context) {
        this.genreList = listGenres;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (getListGenres().get(position).getName() != null && !getListGenres().get(position).getName().isEmpty()) {
            holder.txtName.setText(getListGenres().get(position).getName());
        } else {
            holder.txtName.setText(mContext.getResources().getString(R.string.no_genre));
        }
    }

    @Override
    public int getItemCount() {
        return getListGenres().size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_genre);
        }
    }
}
