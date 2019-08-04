package com.indramahkota.moviecatalogue.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.detail.adapter.CastAdapter;
import com.indramahkota.moviecatalogue.ui.detail.adapter.GenreAdapter;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.LanguageViewModel;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.MovieDetailsViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    @Inject
    ViewModelFactory viewModelFactory;

    private ImageView imgPoster;
    private TextView txtTitle;
    private TextView txtRating;
    private TextView txtRelease;
    private TextView txtOverview;
    private ImageView background;
    private TextView txtLanguage;

    private MovieResponse movieResponse;
    private LanguageResponse languageResponse;
    private ConstraintLayout detailsContainer;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_container);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        AndroidInjection.inject(this);

        Integer movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);

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

        MovieDetailsViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel.class);
        viewModel.getMovieViewState().observe(this, movieResponseState -> {
            switch (movieResponseState.getCurrentState()) {
                case 0:
                    //show loading
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    detailsContainer.setVisibility(View.GONE);
                    break;
                case 1:
                    //show data
                    movieResponse = movieResponseState.getData();
                    initializeUi(movieResponse);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    detailsContainer.setVisibility(View.VISIBLE);
                    break;
                case -1:
                    //show error
                    detailsContainer.setVisibility(View.VISIBLE);
                    break;
            }
        });

        LanguageViewModel languageViewModel = ViewModelProviders.of(this, viewModelFactory).get(LanguageViewModel.class);
        languageViewModel.getLanguageViewState().observe(this, languageResponseState -> {
            if(languageResponseState.getCurrentState() == 1) {
                languageResponse = languageResponseState.getData();
                if(movieResponse != null) {
                    setTxtLanguage();
                }
            }
        });

        if(languageResponse == null) {
            languageViewModel.loadLanguages();
        }

        if(movieResponse == null) {
            viewModel.loadMovieDetails(movieId);
        } else {
            initializeUi(movieResponse);
        }
    }

    private void initializeUi(MovieResponse response) {
        String posterUrl = ApiConstant.BASE_URL_POSTER + response.getPosterPath();
        Glide.with(this)
                .load(posterUrl)
                .error(R.drawable.poster_background_error)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .into(imgPoster);

        posterUrl = ApiConstant.BASE_URL_BACKDROP_PATH + response.getBackdropPath();
        Glide.with(this)
                .load(posterUrl)
                .into(background);

        if(response.getTitle() != null && !response.getTitle().isEmpty()) {
            txtTitle.setText(response.getTitle());
        } else {
            txtTitle.setText(getResources().getString(R.string.no_title));
        }

        if(response.getVoteAverage() != null) {
            txtRating.setText(String.valueOf(response.getVoteAverage()));
        } else {
            txtRating.setText(getResources().getString(R.string.no_rating));
        }

        if(response.getReleaseDate() != null && !response.getReleaseDate().isEmpty()) {
            txtRelease.setText(response.getReleaseDate());
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
            if(lang.getIso().equals(movieResponse.getOriginalLanguage())){
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
