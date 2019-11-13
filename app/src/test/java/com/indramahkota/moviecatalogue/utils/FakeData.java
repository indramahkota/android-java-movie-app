package com.indramahkota.moviecatalogue.utils;

import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;

import java.util.ArrayList;
import java.util.List;

public class FakeData {
    private static ArrayList<String[]> generateStringData() {
        ArrayList<String[]> data = new ArrayList<>();

        String[] data1 = {"/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg",
                "/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg", "420818", "The Lion King",
                "Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.",
                "2019-07-12", "7.1", "English"};
        data.add(data1);

        String[] data2 = {"/86Y6qM8zTn3PFVfCm9J98Ph7JEB.jpg",
                "/wf6VDSi4aFCZfuD8sX8JAKLfJ5m.jpg", "566555", "Detective Conan: The Fist of Blue Sapphire",
                "23rd movie in the \"Detective Conan\" franchise.",
                "2019-04-12", "5.2", "Japanese"};
        data.add(data2);

        String[] data3 = {"/rjbNpRMoVvqHmhmksbokcyCr7wn.jpg",
                "/dihW2yTsvQlust7mSuAqJDtqW7k.jpg", "429617", "Spider-Man: Far from Home",
                "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
                "2019-06-28", "7.8", "English"};
        data.add(data3);

        String[] data4 = {"/xRWht48C2V8XNfzvPehyClOvDni.jpg",
                "/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg", "399579", "Alita: Battle Angel",
                "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                "2019-01-31", "6.8", "English"};
        data.add(data4);

        String[] data5 = {"/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                "/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg", "299534", "Avengers: Endgame",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
                "2019-04-24", "8.4", "English"};
        data.add(data5);

        String[] data6 = {"/wgQ7APnFpf1TuviKHXeEe3KnsTV.jpg",
                "/nDP33LmQwNsnPv29GQazz59HjJI.jpg", "447404", "Pokémon Detective Pikachu",
                "In a world where people collect pocket-size monsters (Pokémon) to do battle, a boy comes across an intelligent monster who seeks to be a detective.",
                "2019-07-12", "7.3", "English"};
        data.add(data6);

        String[] data7 = {"/mKxpYRIrCZLxZjNqpocJ2RdQW8v.jpg",
                "/2cAce3oD0Hh7f5XxuXmNXa1WiuZ.jpg", "420818", "Crawl",
                "While struggling to save her father during a Category 5 hurricane, a young woman finds herself trapped inside a flooding house and fighting for her life against Florida’s most savage and feared predators.",
                "2019-07-11", "6.0", "English"};
        data.add(data7);

        String[] data8 = {"/w9kR8qbmQ01HwnvK4alvnQ2ca0L.jpg",
                "/m67smI1IIMmYzCl9axvKNULVKLr.jpg", "301528", "Toy Story 4",
                "Woody has always been confident about his place in the world and that his priority is taking care of his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to her room, a road trip adventure alongside old and new friends will show Woody how big the world can be for a toy.",
                "2019-06-19", "7.7", "English"};
        data.add(data8);

        String[] data9 = {"/xQccIXfq9J4tgbvdSSPPLLYZGRD.jpg",
                "/4uhVMTAgGt36h68SCTOQKCB4z50.jpg", "486589", "Red Shoes and the Seven Dwarfs",
                "Princes who have been turned into Dwarfs seek the red shoes of a lady in order to break the spell, although it will not be easy.",
                "2019-07-12", "9.0", "English"};
        data.add(data9);

        String[] data10 = {"/jpfkzbIXgKZqCZAkEkFH2VYF63s.jpg",
                "/a1MlbLBk5Sy6YvMbSuKfwGlDVlb.jpg", "920", "Cars",
                "Lightning McQueen, a hotshot rookie race car driven to succeed, discovers that life is about the journey, not the finish line, when he finds himself unexpectedly detoured in the sleepy Route 66 town of Radiator Springs. On route across the country to the big Piston Cup Championship in California to compete against two seasoned pros, McQueen gets to know the town's offbeat characters.",
                "2019-07-25", "6.7", "English"};
        data.add(data10);
        return data;
    }

    public static List<MovieEntity> getListMovie() {
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
            movie.setOriginalLanguage(arrayData.get(i)[7]);
            movie.setPage(1L);
            movieEntities.add(movie);
        }
        return movieEntities;
    }

    public static Resource<MovieResponse> getResourceListMovie() {
        MovieResponse discoverMovie = new MovieResponse();
        discoverMovie.setPage(1L);
        discoverMovie.setTotalPages(1L);
        discoverMovie.setResults(getListMovie());
        return Resource.success(discoverMovie);
    }

    public static List<TvShowEntity> getListTvShow() {
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
            tvShow.setOriginalLanguage(arrayData.get(i)[7]);
            tvShow.setPage(1L);
            tvShowEntities.add(tvShow);
        }
        return tvShowEntities;
    }

    public static Resource<TvShowResponse> getResourceListTvShow() {
        TvShowResponse discoverTvShow = new TvShowResponse();
        discoverTvShow.setPage(1L);
        discoverTvShow.setTotalPages(1L);
        discoverTvShow.setResults(getListTvShow());
        return Resource.success(discoverTvShow);
    }

    public static MovieEntity getMovie() {
        ArrayList<String[]> arrayData = generateStringData();
        MovieEntity movie = new MovieEntity();
        movie.setPosterPath(arrayData.get(0)[0]);
        movie.setBackdropPath(arrayData.get(0)[1]);
        movie.setId(Long.valueOf(arrayData.get(0)[2]));
        movie.setTitle(arrayData.get(0)[3]);
        movie.setOverview(arrayData.get(0)[4]);
        movie.setReleaseDate(arrayData.get(0)[5]);
        movie.setVoteAverage(Double.valueOf(arrayData.get(0)[6]));
        movie.setOriginalLanguage(arrayData.get(0)[7]);
        return movie;
    }

    public static Resource<MovieEntity> getResourceMovie() {
        return Resource.success(getMovie());
    }

    public static TvShowEntity getTvShow() {
        ArrayList<String[]> arrayData = generateStringData();
        TvShowEntity tvShow = new TvShowEntity();
        tvShow.setPosterPath(arrayData.get(0)[0]);
        tvShow.setBackdropPath(arrayData.get(0)[1]);
        tvShow.setId(Long.valueOf(arrayData.get(0)[2]));
        tvShow.setName(arrayData.get(0)[3]);
        tvShow.setOverview(arrayData.get(0)[4]);
        tvShow.setFirstAirDate(arrayData.get(0)[5]);
        tvShow.setVoteAverage(Double.valueOf(arrayData.get(0)[6]));
        tvShow.setOriginalLanguage(arrayData.get(0)[7]);
        return tvShow;
    }

    public static Resource<TvShowEntity> getResourceTvShow() {
        return Resource.success(getTvShow());
    }

    public static List<LanguageEntity> getLanguages() {
        ArrayList<LanguageEntity> data = new ArrayList<>();
        LanguageEntity lang1 = new LanguageEntity();
        lang1.setEnglishName("English");
        data.add(lang1);

        LanguageEntity lang2 = new LanguageEntity();
        lang2.setEnglishName("Indonesian");
        data.add(lang2);

        LanguageEntity lang3 = new LanguageEntity();
        lang3.setEnglishName("Malaysian");
        data.add(lang3);

        LanguageEntity lang4 = new LanguageEntity();
        lang4.setEnglishName("Japanese");
        data.add(lang4);

        LanguageEntity lang5 = new LanguageEntity();
        lang5.setEnglishName("Javanese");
        data.add(lang5);
        return data;
    }

    public static Resource<List<LanguageEntity>> getResourceLanguages() {
        return Resource.success(getLanguages());
    }
}