package com.indramahkota.moviecatalogue.ui.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.ui.detail.adapter.CastAdapter;
import com.indramahkota.moviecatalogue.ui.detail.adapter.GenreAdapter;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.LanguageViewModel;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.TvShowDetailsViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.FavoriteTvShowViewModel;
import com.indramahkota.moviecatalogue.ui.utils.CustomDateFormat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class TvShowDetailsActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_TV_SHOW_ID = "extra_tv_show_id";
    public static final String STATE_TV_SHOW_RESPONSE = "state_tv_show_response";
    public static final String STATE_LANGUAGE_RESPONSE = "state_language_response";

    private Menu menu;
    private ImageView imgPoster;
    private TextView txtTitle;
    private TextView txtRating;
    private TextView txtRelease;
    private TextView txtOverview;
    private ImageView background;
    private TextView txtLanguage;

    private TvShowEntity tvShowEntity;
    private List<LanguageEntity> languages;
    private ConstraintLayout detailsContainer;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoriteTvShowViewModel favoriteTvShowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_container);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Long tvShowId = getIntent().getLongExtra(EXTRA_TV_SHOW_ID, 0);

        detailsContainer = findViewById(R.id.layout_details);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        imgPoster = findViewById(R.id.img_poster);
        txtTitle = findViewById(R.id.txt_title);
        txtRating = findViewById(R.id.txt_rating);
        txtRelease = findViewById(R.id.txt_release_date);
        txtOverview = findViewById(R.id.txt_overview);
        background = findViewById(R.id.img_background);
        txtLanguage = findViewById(R.id.txt_language);
        txtLanguage.setText(getResources().getString(R.string.no_language));

        if (savedInstanceState != null) {
            tvShowEntity = savedInstanceState.getParcelable(STATE_TV_SHOW_RESPONSE);
            languages = savedInstanceState.getParcelableArrayList(STATE_LANGUAGE_RESPONSE);
        }

        favoriteTvShowViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoriteTvShowViewModel.class);

        if (tvShowEntity != null && languages != null)
            setTxtLanguage();
        else {
            LanguageViewModel languageViewModel = new ViewModelProvider(this, viewModelFactory).get(LanguageViewModel.class);
            languageViewModel.getLanguages().observe(this, languageResponseState -> {
                if (languageResponseState.isSuccess()) {
                    languages = languageResponseState.data;
                    if (tvShowEntity != null) {
                        setTxtLanguage();
                    }
                }
            });
        }

        TvShowDetailsViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(TvShowDetailsViewModel.class);
        viewModel.tvShowDetail.observe(this, tvShowResponseState -> {
            switch (tvShowResponseState.status) {
                case LOADING:
                    //show loading
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    detailsContainer.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    //show data
                    tvShowEntity = tvShowResponseState.data;
                    if (tvShowEntity != null) {
                        initializeUi(tvShowEntity);
                        mShimmerViewContainer.setVisibility(View.GONE);
                        detailsContainer.setVisibility(View.VISIBLE);
                    }
                    break;
                case ERROR:
                    //show error
                    tvShowEntity = tvShowResponseState.data;
                    if (tvShowEntity != null) {
                        initializeUi(tvShowEntity);
                        mShimmerViewContainer.setVisibility(View.GONE);
                        detailsContainer.setVisibility(View.VISIBLE);
                    } else {
                        detailsContainer.setVisibility(View.GONE);
                    }
                    Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        if (tvShowEntity != null) {
            initializeUi(tvShowEntity);
        } else {
            viewModel.setTvShowId(tvShowId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite_menu, menu);
        this.menu = menu;

        if (tvShowEntity != null && tvShowEntity.getFavorite()) {
            menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_pink_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.favorites && tvShowEntity != null) {
            if (tvShowEntity.getFavorite()) {
                tvShowEntity.setFavorite(false);
                favoriteTvShowViewModel.updateTvShow(tvShowEntity);
                menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_border_white_24dp);
            } else {
                tvShowEntity.setFavorite(true);
                favoriteTvShowViewModel.updateTvShow(tvShowEntity);
                menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_pink_24dp);
            }
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (languages != null) {
            ArrayList<LanguageEntity> helper = new ArrayList<>();
            int len = languages.size();
            for (int i = 0; i < len; ++i) {
                helper.add(languages.get(i));
            }
            outState.putParcelableArrayList(STATE_LANGUAGE_RESPONSE, helper);
        }

        if (tvShowEntity != null) {
            outState.putParcelable(STATE_TV_SHOW_RESPONSE, tvShowEntity);
        }
    }

    private void initializeUi(@NonNull TvShowEntity response) {
        if (menu != null && tvShowEntity.getFavorite()) {
            menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_pink_24dp);
        }

        String posterUrl;
        if (response.getPosterPath() != null && !response.getPosterPath().isEmpty()) {
            posterUrl = ApiConstant.BASE_URL_POSTER + response.getPosterPath();
            Glide.with(this)
                    .load(posterUrl)
                    .error(R.drawable.poster_background_error)
                    .transform(new CenterCrop(), new RoundedCorners(8))
                    .into(imgPoster);
        }

        if (response.getBackdropPath() != null && !response.getBackdropPath().isEmpty()) {
            posterUrl = ApiConstant.BASE_URL_BACKDROP_PATH + response.getBackdropPath();
            Glide.with(this)
                    .load(posterUrl)
                    .into(background);
        }

        if (response.getName() != null && !response.getName().isEmpty()) {
            txtTitle.setText(response.getName());
        } else {
            txtTitle.setText(getResources().getString(R.string.no_title));
        }

        if (response.getVoteAverage() != null) {
            txtRating.setText(String.valueOf(response.getVoteAverage()));
        } else {
            txtRating.setText(getResources().getString(R.string.no_rating));
        }

        if (response.getFirstAirDate() != null && !response.getFirstAirDate().isEmpty()) {
            String date = response.getFirstAirDate();
            String newDate = CustomDateFormat.formatDateFromString(date);
            txtRelease.setText(newDate);
        } else {
            txtRelease.setText(getResources().getString(R.string.no_release_date));
        }

        if (response.getOverview() != null && !response.getOverview().isEmpty()) {
            txtOverview.setText(response.getOverview());
        } else {
            txtOverview.setText(getResources().getString(R.string.availability_overview));
        }

        if (languages != null) {
            setTxtLanguage();
        }

        if (response.getCredits() != null && response.getCredits().getCast() != null) {
            RecyclerView rvCasts = findViewById(R.id.rv_details_cast);
            rvCasts.setHasFixedSize(true);
            CastAdapter listCastAdapter = new CastAdapter(response.getCredits().getCast(), this);
            rvCasts.setAdapter(listCastAdapter);
        }

        if (response.getGenres() != null) {
            RecyclerView rvGenres = findViewById(R.id.rv_details_genres);
            rvGenres.setHasFixedSize(true);
            GenreAdapter genreAdapter = new GenreAdapter(response.getGenres(), this);
            rvGenres.setAdapter(genreAdapter);
        }
    }

    private void setTxtLanguage() {
        int len = languages.size();
        for (int i = 0; i < len; ++i) {
            LanguageEntity lang = languages.get(i);
            if (lang.getIso().equals(tvShowEntity.getOriginalLanguage())) {
                txtLanguage.setText(lang.getEnglishName());
                break;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmer();
    }
}
