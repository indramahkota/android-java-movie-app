package com.indramahkota.moviecatalogue.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.model.DiscoverTvShow;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;

import java.util.List;

public class TvShowFragment extends Fragment {

    public TvShowFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_tv_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView rvTvShows = view.findViewById(R.id.rv_fragment_category);
        rvTvShows.setLayoutManager(linearLayoutManager);
        rvTvShows.setHasFixedSize(true);

        TvShowFragmentViewModel viewModel = ViewModelProviders.of(this).get(TvShowFragmentViewModel.class);
        List<DiscoverTvShow> discoverTvShows = viewModel.getListTvShow();
        TvShowAdapter listTvShowAdapter = new TvShowAdapter(discoverTvShows, getContext());
        listTvShowAdapter.notifyDataSetChanged();
        rvTvShows.setAdapter(listTvShowAdapter);
    }
}
