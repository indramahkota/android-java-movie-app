package com.indramahkota.moviecatalogue.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.MovieDetailsViewModel;

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
    //private TextView txtLanguage;

    private MovieResponse movieResponse;
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
        //txtLanguage = findViewById(R.id.txt_language);

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

        /*if(movieDetail.getLanguage() != null && !movieDetail.getLanguage().isEmpty()) {
            txtLanguage.setText(movieDetail.getLanguage());
        } else {
            txtLanguage.setText(getResources().getString(R.string.no_title));
        }*/

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
