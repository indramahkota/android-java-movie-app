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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.detail.adapter.CastAdapter;
import com.indramahkota.moviecatalogue.ui.detail.adapter.GenreAdapter;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.LanguageViewModel;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.TvShowDetailsViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.FavoriteTvShowViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TvShowDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW_ID = "extra_tv_show_id";
    public static final String STATE_TV_SHOW_RESPONSE = "state_tv_show_response";
    public static final String STATE_LANGUAGE_RESPONSE = "state_language_response";

    @Inject
    ViewModelFactory viewModelFactory;

    private Menu menu;
    private ImageView imgPoster;
    private TextView txtTitle;
    private TextView txtRating;
    private TextView txtRelease;
    private TextView txtOverview;
    private ImageView background;
    private TextView txtLanguage;

    private Integer tvShowId;
    private FavoriteTvShow favoriteTvShow;
    private TvShowResponse tvShowResponse;
    private LanguageResponse languageResponse;
    private ConstraintLayout detailsContainer;
    private ShimmerFrameLayout mShimmerViewContainer;

    private FavoriteTvShowViewModel favoriteTvShowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_container);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvShowId = getIntent().getIntExtra(EXTRA_TV_SHOW_ID, 0);

        detailsContainer = findViewById(R.id.layout_details);
        detailsContainer.setVisibility(View.GONE);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.VISIBLE);

        imgPoster = findViewById(R.id.img_poster);
        txtTitle = findViewById(R.id.txt_title);
        txtRating = findViewById(R.id.txt_rating);
        txtRelease = findViewById(R.id.txt_release_date);
        txtOverview = findViewById(R.id.txt_overview);
        background = findViewById(R.id.img_background);
        txtLanguage = findViewById(R.id.txt_language);

        favoriteTvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteTvShowViewModel.class);

        LanguageViewModel languageViewModel = ViewModelProviders.of(this, viewModelFactory).get(LanguageViewModel.class);
        languageViewModel.getLanguageViewState().observe(this, languageResponseState -> {
            if(languageResponseState.getCurrentState() == 1) {
                languageResponse = languageResponseState.getData();
                if(tvShowResponse != null) {
                    setTxtLanguage();
                }
            }
        });

        TvShowDetailsViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowDetailsViewModel.class);
        viewModel.getTvShowViewState().observe(this, tvShowResponseState -> {
            switch (tvShowResponseState.getCurrentState()) {
                case 0:
                    //show loading
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    detailsContainer.setVisibility(View.GONE);
                    break;
                case 1:
                    //show data
                    tvShowResponse = tvShowResponseState.getData();
                    initializeUi(tvShowResponse);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    detailsContainer.setVisibility(View.VISIBLE);
                    break;
                case -1:
                    //show error
                    detailsContainer.setVisibility(View.GONE);
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        if (savedInstanceState != null) {
            tvShowResponse = savedInstanceState.getParcelable(STATE_TV_SHOW_RESPONSE);
            languageResponse = savedInstanceState.getParcelable(STATE_LANGUAGE_RESPONSE);
        }

        if (languageResponse == null) {
            languageViewModel.loadLanguages();
        }

        if (tvShowResponse != null) {
            initializeUi(tvShowResponse);
        } else {
            viewModel.loadTvShowDetails(tvShowId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite_menu, menu);
        this.menu = menu;

        favoriteTvShowViewModel.getFavoriteTvShow((long)tvShowId).observe(this, favTvShow -> {
            favoriteTvShow = favTvShow;
            if(favoriteTvShow != null) {
                menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_pink_24dp);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        } else if(item.getItemId() == R.id.favorites && tvShowResponse != null) {
            if(favoriteTvShow != null) {
                favoriteTvShowViewModel.deleteFavoriteMovie((long)tvShowId);
                menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_border_white_24dp);
            } else {
                FavoriteTvShow favTvShow = new FavoriteTvShow();
                favTvShow.setItemId(tvShowResponse.getId());
                favTvShow.setName(tvShowResponse.getName());
                favTvShow.setOverview(tvShowResponse.getOverview());
                favTvShow.setPosterPath(tvShowResponse.getPosterPath());
                favTvShow.setFirstAirDate(tvShowResponse.getFirstAirDate());
                favTvShow.setVoteAverage(String.valueOf(tvShowResponse.getVoteAverage()));
                favoriteTvShowViewModel.insertFavoriteTvShow(favTvShow);
                menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_pink_24dp);
            }
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_LANGUAGE_RESPONSE, languageResponse);
        outState.putParcelable(STATE_TV_SHOW_RESPONSE, tvShowResponse);
    }

    private void initializeUi(@NonNull TvShowResponse response) {
        String posterUrl;
        if(response.getPosterPath() != null && !response.getPosterPath().isEmpty()){
            posterUrl = ApiConstant.BASE_URL_POSTER + response.getPosterPath();
            Glide.with(this)
                    .load(posterUrl)
                    .error(R.drawable.poster_background_error)
                    .transform(new CenterCrop(), new RoundedCorners(8))
                    .into(imgPoster);
        }

        if(response.getBackdropPath() != null && !response.getBackdropPath().isEmpty()) {
            posterUrl = ApiConstant.BASE_URL_BACKDROP_PATH + response.getBackdropPath();
            Glide.with(this)
                    .load(posterUrl)
                    .into(background);
        }

        if(response.getName() != null && !response.getName().isEmpty()) {
            txtTitle.setText(response.getName());
        } else {
            txtTitle.setText(getResources().getString(R.string.no_title));
        }

        if(response.getVoteAverage() != null) {
            txtRating.setText(String.valueOf(response.getVoteAverage()));
        } else {
            txtRating.setText(getResources().getString(R.string.no_rating));
        }

        if(response.getFirstAirDate() != null && !response.getFirstAirDate().isEmpty()) {
            txtRelease.setText(response.getFirstAirDate());
        } else {
            txtRelease.setText(getResources().getString(R.string.no_release_date));
        }

        if(response.getOverview() != null && !response.getOverview().isEmpty()) {
            txtOverview.setText(response.getOverview());
        } else {
            txtOverview.setText(getResources().getString(R.string.availability_overview));
        }

        if(languageResponse != null) {
            setTxtLanguage();
        }

        RecyclerView rvCasts = findViewById(R.id.rv_details_cast);
        rvCasts.setHasFixedSize(true);
        CastAdapter listCastAdapter = new CastAdapter(response.getCredits().getCast(), this);
        rvCasts.setAdapter(listCastAdapter);

        RecyclerView rvGenres = findViewById(R.id.rv_details_genres);
        rvGenres.setHasFixedSize(true);
        GenreAdapter genreAdapter = new GenreAdapter(response.getGenres(), this);
        rvGenres.setAdapter(genreAdapter);
    }

    private void setTxtLanguage() {
        List<Language> languages = languageResponse.getResults();
        int len = languages.size();

        for (int i = 0; i<len; ++i) {
            Language lang = languages.get(i);
            if(lang.getIso().equals(tvShowResponse.getOriginalLanguage())){
                if(lang.getIso() != null && !lang.getIso().isEmpty()) {
                    txtLanguage.setText(lang.getEnglishName());
                } else {
                    txtLanguage.setText(getResources().getString(R.string.no_language));
                }
                break;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
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
