package com.indramahkota.moviecatalogue.utils;

import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.ArrayList;
import java.util.List;

public class FakeData {
    private static ArrayList<String[]> generateStringData() {
        ArrayList<String[]> data = new ArrayList<>();
        String[] data1 = {"/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg",
                "/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg", "420818", "The Lion King",
                "Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.",
                "12, Jul 2019", "7.2"};
        data.add(data1);

        String[] data2 = {"/fki3kBlwJzFp8QohL43g9ReV455.jpg",
                "/jC1KqsFx8ZyqJyQa2Ohi7xgL7XC.jpg", "60735", "The Flash",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "07, Okt 2014", "6.6"};
        data.add(data2);
        return data;
    }

    public static List<MovieEntity> getListMovie(boolean favorite) {
        List<MovieEntity> movieEntities = new ArrayList<>();
        ArrayList<String[]> arrayData = generateStringData();
        int len = arrayData.size();

        for (int i = 0; i < len; ++i) {
            MovieEntity movie = new MovieEntity();
            movie.setPosterPath(arrayData.get(i)[0]);
            movie.setBackdropPath(arrayData.get(i)[1]);
            movie.setId(Long.valueOf(arrayData.get(i)[2]));
            movie.setTitle(arrayData.get(i)[3]);
            movie.setOverview(arrayData.get(i)[4]);
            movie.setReleaseDate(arrayData.get(i)[5]);
            movie.setVoteAverage(Double.valueOf(arrayData.get(i)[6]));
            movie.setFavorite(favorite);
            movieEntities.add(movie);
        }

        return movieEntities;
    }

    public static List<TvShowEntity> getListTvShow(boolean favorite) {
        List<TvShowEntity> tvShowEntities = new ArrayList<>();
        ArrayList<String[]> arrayData = generateStringData();
        int len = arrayData.size();

        for (int i = 0; i < len; ++i) {
            TvShowEntity tvShow = new TvShowEntity();
            tvShow.setPosterPath(arrayData.get(i)[0]);
            tvShow.setBackdropPath(arrayData.get(i)[1]);
            tvShow.setId(Long.valueOf(arrayData.get(i)[2]));
            tvShow.setName(arrayData.get(i)[3]);
            tvShow.setOverview(arrayData.get(i)[4]);
            tvShow.setFirstAirDate(arrayData.get(i)[5]);
            tvShow.setVoteAverage(Double.valueOf(arrayData.get(i)[6]));
            tvShow.setFavorite(favorite);
            tvShowEntities.add(tvShow);
        }

        return tvShowEntities;
    }

    public static MovieEntity getMovieData(boolean favorite) {
        ArrayList<String[]> arrayData = generateStringData();
        MovieEntity movie = new MovieEntity();
        movie.setPosterPath(arrayData.get(0)[0]);
        movie.setBackdropPath(arrayData.get(0)[1]);
        movie.setId(Long.valueOf(arrayData.get(0)[2]));
        movie.setTitle(arrayData.get(0)[3]);
        movie.setOverview(arrayData.get(0)[4]);
        movie.setReleaseDate(arrayData.get(0)[5]);
        movie.setVoteAverage(Double.valueOf(arrayData.get(0)[6]));
        movie.setFavorite(favorite);
        return movie;
    }

    public static TvShowEntity getTvShowData(boolean favorite) {
        ArrayList<String[]> arrayData = generateStringData();
        TvShowEntity tvShow = new TvShowEntity();
        tvShow.setPosterPath(arrayData.get(1)[0]);
        tvShow.setBackdropPath(arrayData.get(1)[1]);
        tvShow.setId(Long.valueOf(arrayData.get(1)[2]));
        tvShow.setName(arrayData.get(1)[3]);
        tvShow.setOverview(arrayData.get(1)[4]);
        tvShow.setFirstAirDate(arrayData.get(1)[5]);
        tvShow.setVoteAverage(Double.valueOf(arrayData.get(1)[6]));
        tvShow.setFavorite(favorite);
        return tvShow;
    }
}