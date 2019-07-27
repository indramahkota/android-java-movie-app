package com.indramahkota.moviecatalogue.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.Constant;
import com.indramahkota.moviecatalogue.data.model.DiscoverMovie;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

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

        DiscoverMovie movieDetail = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if(movieDetail == null) return;

        String posterUrl = Constant.BASE_URL_POSTER + movieDetail.getPosterPath();
        Glide.with(this)
                .load(posterUrl)
                .error(R.drawable.poster_background_error)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .into(imgPoster);

        posterUrl = Constant.BASE_URL_BACKDROP_PATH + movieDetail.getBackdropPath();
        Glide.with(this)
                .load(posterUrl)
                .into(background);

        if(movieDetail.getTitle() != null && !movieDetail.getTitle().isEmpty()) {
            txtTitle.setText(movieDetail.getTitle());
        } else {
            txtTitle.setText(getResources().getString(R.string.no_title));
        }

        if(movieDetail.getLanguage() != null && !movieDetail.getLanguage().isEmpty()) {
            txtLanguage.setText(movieDetail.getLanguage());
        } else {
            txtLanguage.setText(getResources().getString(R.string.no_title));
        }

        if(movieDetail.getVoteAverage() != null) {
            txtRating.setText(String.valueOf(movieDetail.getVoteAverage()));
        } else {
            txtRating.setText(getResources().getString(R.string.no_rating));
        }

        if(movieDetail.getReleaseDate() != null && !movieDetail.getReleaseDate().isEmpty()) {
            txtRelease.setText(movieDetail.getReleaseDate());
        } else {
            txtRelease.setText(getResources().getString(R.string.no_release_date));
        }

        if(movieDetail.getOverview() != null && !movieDetail.getOverview().isEmpty()) {
            txtOverview.setText(movieDetail.getOverview());
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
