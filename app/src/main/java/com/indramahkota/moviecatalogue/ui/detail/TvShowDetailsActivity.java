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
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.TvShowDetailsViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TvShowDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW_ID = "extra_tv_show_id";

    @Inject
    ViewModelFactory viewModelFactory;

    private ImageView imgPoster;
    private TextView txtTitle;
    private TextView txtRating;
    private TextView txtRelease;
    private TextView txtOverview;
    private ImageView background;
    //private TextView txtLanguage;

    private TvShowResponse tvShowResponse;
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

        Integer tvShowId = getIntent().getIntExtra(EXTRA_TV_SHOW_ID, 0);

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
                    detailsContainer.setVisibility(View.VISIBLE);
                    break;
            }
        });

        if(tvShowResponse == null) {
            viewModel.loadTvShowDetails(tvShowId);
        } else {
            initializeUi(tvShowResponse);
        }
    }

    private void initializeUi(TvShowResponse response) {
        String posterUrl = ApiConstant.BASE_URL_POSTER + response.getPosterPath();
        Glide.with(this)
                .load(posterUrl)
                .error(R.drawable.poster_background_error)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .into(imgPoster);

        posterUrl = ApiConstant.BASE_URL_BACKDROP_PATH + response.getBackdropPath();
        Glide.with(this)
                .load(posterUrl)
                .apply(new RequestOptions().override(200, 300))
                .into(background);

        if(response.getName() != null && !response.getName().isEmpty()) {
            txtTitle.setText(response.getName());
        } else {
            txtTitle.setText(getResources().getString(R.string.no_title));
        }

        /*if(response.getLanguage() != null && !response.getLanguage().isEmpty()) {
            txtLanguage.setText(response.getLanguage());
        } else {
            txtLanguage.setText(getResources().getString(R.string.no_title));
        }*/

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
