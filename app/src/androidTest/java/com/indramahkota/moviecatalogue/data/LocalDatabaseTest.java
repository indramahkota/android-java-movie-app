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
import static com.indramahkota.moviecatalogue.utils.LiveDataTestUtil.getValue;
import static org.junit.Assert.assertEquals;

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

        List<MovieEntity> expectedCourses = getListMovie(true);
        db.appDao().insertMovies(expectedCourses);

        List<MovieEntity> actualCourses = getValue(db.appDao().getAllFavoriteMovie());

        assertEquals(expectedCourses.size(), actualCourses.size());
    }

    @Test
    public void insertAllTvShowEntity() {

        List<TvShowEntity> expectedCourses = getListTvShow(true);
        db.appDao().insertTvShows(expectedCourses);

        List<TvShowEntity> actualCourses = getValue(db.appDao().getAllFavoriteTvShow());

        assertEquals(expectedCourses.size(), actualCourses.size());
    }
}
