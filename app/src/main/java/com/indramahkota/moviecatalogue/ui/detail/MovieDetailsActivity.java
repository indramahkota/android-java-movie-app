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
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.ui.detail.adapter.CastAdapter;
import com.indramahkota.moviecatalogue.ui.detail.adapter.GenreAdapter;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.LanguageViewModel;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.MovieDetailsViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.FavoriteMovieViewModel;
import com.indramahkota.moviecatalogue.ui.utils.CustomDateFormat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MovieDetailsActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String STATE_MOVIE_RESPONSE = "state_movie_response";
    public static final String STATE_LANGUAGE_RESPONSE = "state_language_response";

    private Menu menu;
    private ImageView imgPoster;
    private TextView txtTitle;
    private TextView txtRating;
    private TextView txtRelease;
    private TextView txtOverview;
    private ImageView background;
    private TextView txtLanguage;

    private MovieEntity movieEntity;
    private List<LanguageEntity> languages;
    private ConstraintLayout detailsContainer;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoriteMovieViewModel favoriteMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_container);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Long movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, 0);

        detailsContainer = findViewById(R.id.layout_details);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        imgPoster = findViewById(R.id.img_poster);
        txtTitle = findViewById(R.id.txt_title);
        txtRating = findViewById(R.id.txt_rating);
        txtRelease = findViewById(R.id.txt_release_date);
        txtOverview = findViewById(R.id.txt_overview);
        background = findViewById(R.id.img_background);
        txtLanguage = findViewById(R.id.txt_language);

        if (savedInstanceState != null) {
            movieEntity = savedInstanceState.getParcelable(STATE_MOVIE_RESPONSE);
            languages = savedInstanceState.getParcelableArrayList(STATE_LANGUAGE_RESPONSE);
        }

        favoriteMovieViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoriteMovieViewModel.class);

        if (movieEntity != null && languages != null)
            setTxtLanguage();
        else {
            LanguageViewModel languageViewModel = new ViewModelProvider(this, viewModelFactory).get(LanguageViewModel.class);
            languageViewModel.getLanguages().observe(this, languageResponseState -> {
                if (languageResponseState.isSuccess()) {
                    languages = languageResponseState.data;
                    if (movieEntity != null) {
                        setTxtLanguage();
                    }
                }
            });
        }

        MovieDetailsViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel.class);
        viewModel.movieDetail.observe(this, movieResponseState -> {
            switch (movieResponseState.status) {
                case LOADING:
                    //show loading
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    detailsContainer.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    //show data
                    movieEntity = movieResponseState.data;
                    if (movieEntity != null) {
                        initializeUi(movieEntity);
                        mShimmerViewContainer.setVisibility(View.GONE);
                        detailsContainer.setVisibility(View.VISIBLE);
                    }
                    break;
                case ERROR:
                    //show error
                    movieEntity = movieResponseState.data;
                    if (movieEntity != null) {
                        initializeUi(movieEntity);
                        mShimmerViewContainer.setVisibility(View.GONE);
                        detailsContainer.setVisibility(View.VISIBLE);
                    } else {
                        detailsContainer.setVisibility(View.GONE);
                    }
                    Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        if (movieEntity != null) {
            initializeUi(movieEntity);
        } else {
            viewModel.setMovieId(movieId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite_menu, menu);
        this.menu = menu;

        if (movieEntity != null && movieEntity.getFavorite()) {
            menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_pink_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.favorites && movieEntity != null) {
            if (movieEntity.getFavorite()) {
                movieEntity.setFavorite(false);
                favoriteMovieViewModel.updateMovie(movieEntity);
                menu.findItem(R.id.favorites).setIcon(R.drawable.ic_favorite_border_white_24dp);
            } else {
                movieEntity.setFavorite(true);
                favoriteMovieViewModel.updateMovie(movieEntity);
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

        if (movieEntity != null) {
            outState.putParcelable(STATE_MOVIE_RESPONSE, movieEntity);
        }
    }

    private void initializeUi(@NonNull MovieEntity response) {
        if (menu != null && response.getFavorite()) {
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

        if (response.getTitle() != null && !response.getTitle().isEmpty()) {
            txtTitle.setText(response.getTitle());
        } else {
            txtTitle.setText(getResources().getString(R.string.no_title));
        }

        if (response.getVoteAverage() != null) {
            txtRating.setText(String.valueOf(response.getVoteAverage()));
        } else {
            txtRating.setText(getResources().getString(R.string.no_rating));
        }

        if (response.getReleaseDate() != null && !response.getReleaseDate().isEmpty()) {
            String date = response.getReleaseDate();
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
            if (lang.getIso().equals(movieEntity.getOriginalLanguage())) {
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
