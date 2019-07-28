package com.indramahkota.moviecatalogue.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.remote.Constant;
import com.indramahkota.moviecatalogue.data.remote.model.DiscoverTvShow;

public class TvShowDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView imgPoster = findViewById(R.id.img_poster);
        TextView txtTitle = findViewById(R.id.txt_title);
        TextView txtRating = findViewById(R.id.txt_rating);
        TextView txtRelease = findViewById(R.id.txt_release_date);
        TextView txtOverview = findViewById(R.id.txt_overview);
        ImageView background = findViewById(R.id.img_background);
        TextView txtLanguage = findViewById(R.id.txt_language);

        DiscoverTvShow tvShowDetail = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        if(tvShowDetail == null) return;

        String posterUrl = Constant.BASE_URL_POSTER + tvShowDetail.getPosterPath();
        Glide.with(this)
                .load(posterUrl)
                .error(R.drawable.poster_background_error)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .into(imgPoster);

        posterUrl = Constant.BASE_URL_BACKDROP_PATH + tvShowDetail.getBackdropPath();
        Glide.with(this)
                .load(posterUrl)
                .apply(new RequestOptions().override(200, 300))
                .into(background);

        if(tvShowDetail.getName() != null && !tvShowDetail.getName().isEmpty()) {
            txtTitle.setText(tvShowDetail.getName());
        } else {
            txtTitle.setText(getResources().getString(R.string.no_title));
        }

        if(tvShowDetail.getLanguage() != null && !tvShowDetail.getLanguage().isEmpty()) {
            txtLanguage.setText(tvShowDetail.getLanguage());
        } else {
            txtLanguage.setText(getResources().getString(R.string.no_title));
        }

        if(tvShowDetail.getVoteAverage() != null) {
            txtRating.setText(String.valueOf(tvShowDetail.getVoteAverage()));
        } else {
            txtRating.setText(getResources().getString(R.string.no_rating));
        }

        if(tvShowDetail.getFirstAirDate() != null && !tvShowDetail.getFirstAirDate().isEmpty()) {
            txtRelease.setText(tvShowDetail.getFirstAirDate());
        } else {
            txtRelease.setText(getResources().getString(R.string.no_release_date));
        }

        if(tvShowDetail.getOverview() != null && !tvShowDetail.getOverview().isEmpty()) {
            txtOverview.setText(tvShowDetail.getOverview());
        } else {
            txtOverview.setText(getResources().getString(R.string.availability_overview));
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
