package com.indramahkota.moviecatalogue.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.indramahkota.moviecatalogue.data.source.locale.database.MyDatabase;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.indramahkota.moviecatalogue.utils.FakeData.getListMovie;
import static com.indramahkota.moviecatalogue.utils.FakeData.getListTvShow;
import static com.indramahkota.moviecatalogue.utils.FakeData.getMovieData;
import static com.indramahkota.moviecatalogue.utils.FakeData.getTvShowData;
import static com.indramahkota.moviecatalogue.utils.LiveDataTestUtil.getValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocalDatabaseTest {

    private MyDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                MyDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertAllMovieEntity() {

        List<MovieEntity> expectedData = getListMovie(true);
        db.appDao().insertMovies(expectedData);

        List<MovieEntity> actualData = getValue(db.appDao().getAllFavoriteMovie());

        assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    public void insertAllTvShowEntity() {

        List<TvShowEntity> expectedData = getListTvShow(true);
        db.appDao().insertTvShows(expectedData);

        List<TvShowEntity> actualData = getValue(db.appDao().getAllFavoriteTvShow());

        assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    public void updateMovieEntity() {
        MovieEntity expectedData = getMovieData(false);
        db.appDao().insertMovie(expectedData);

        expectedData.setFavorite(true);
        db.appDao().updateMovie(expectedData);

        MovieEntity actualModule = db.appDao().selectMovieById(expectedData.getId());

        assertNotNull(actualModule);
        assertEquals(expectedData.getFavorite(), actualModule.getFavorite());
    }

    @Test
    public void updateTvShowEntity() {
        TvShowEntity expectedData = getTvShowData(false);
        db.appDao().insertTvShow(expectedData);

        expectedData.setFavorite(true);
        db.appDao().updateTvShow(expectedData);

        TvShowEntity actualModule = db.appDao().selectTvShowById(expectedData.getId());

        assertNotNull(actualModule);
        assertEquals(expectedData.getFavorite(), actualModule.getFavorite());
    }
}
