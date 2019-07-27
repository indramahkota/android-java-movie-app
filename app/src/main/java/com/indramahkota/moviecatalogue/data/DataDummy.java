package com.indramahkota.moviecatalogue.data;

import com.indramahkota.moviecatalogue.data.model.DiscoverMovie;
import com.indramahkota.moviecatalogue.data.model.DiscoverTvShow;

import java.util.ArrayList;

public class DataDummy {

    public static ArrayList<DiscoverMovie> generateDummyMovies() {
        ArrayList<DiscoverMovie> discoverMovies = new ArrayList<>();
        discoverMovies.add(new DiscoverMovie("/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg",
                "/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg", 420818, "The Lion King",
                "Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.",
                "2019-07-12", 7.3, "English"));

        discoverMovies.add(new DiscoverMovie("/86Y6qM8zTn3PFVfCm9J98Ph7JEB.jpg",
                "/wf6VDSi4aFCZfuD8sX8JAKLfJ5m.jpg", 566555, "Detective Conan: The Fist of Blue Sapphire",
                "23rd movie in the \"Detective Conan\" franchise.",
                "2019-04-12", 5.2, "Japanese"));

        discoverMovies.add(new DiscoverMovie("/rjbNpRMoVvqHmhmksbokcyCr7wn.jpg",
                "/dihW2yTsvQlust7mSuAqJDtqW7k.jpg", 429617, "Spider-Man: Far from Home",
                "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
                "2019-06-28", 7.8, "English"));

        discoverMovies.add(new DiscoverMovie("/xRWht48C2V8XNfzvPehyClOvDni.jpg",
                "/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg", 399579, "Alita: Battle Angel",
                "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                "2019-01-31", 6.8, "English"));

        discoverMovies.add(new DiscoverMovie("/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                "/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg", 299534, "Avengers: Endgame",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
                "2019-04-24", 8.4, "English"));

        discoverMovies.add(new DiscoverMovie("/wgQ7APnFpf1TuviKHXeEe3KnsTV.jpg",
                "/nDP33LmQwNsnPv29GQazz59HjJI.jpg", 447404, "Pokémon Detective Pikachu",
                "In a world where people collect pocket-size monsters (Pokémon) to do battle, a boy comes across an intelligent monster who seeks to be a detective.",
                "2019-07-12", 7.3, "English"));

        discoverMovies.add(new DiscoverMovie("/mKxpYRIrCZLxZjNqpocJ2RdQW8v.jpg",
                "/2cAce3oD0Hh7f5XxuXmNXa1WiuZ.jpg", 420818, "Crawl",
                "While struggling to save her father during a Category 5 hurricane, a young woman finds herself trapped inside a flooding house and fighting for her life against Florida’s most savage and feared predators.",
                "2019-07-11", 6.0, "English"));

        discoverMovies.add(new DiscoverMovie("/w9kR8qbmQ01HwnvK4alvnQ2ca0L.jpg",
                "/m67smI1IIMmYzCl9axvKNULVKLr.jpg", 301528, "Toy Story 4",
                "Woody has always been confident about his place in the world and that his priority is taking care of his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to her room, a road trip adventure alongside old and new friends will show Woody how big the world can be for a toy.",
                "2019-06-19", 7.7, "English"));

        discoverMovies.add(new DiscoverMovie("/xQccIXfq9J4tgbvdSSPPLLYZGRD.jpg",
                "/4uhVMTAgGt36h68SCTOQKCB4z50.jpg", 486589, "Red Shoes and the Seven Dwarfs",
                "Princes who have been turned into Dwarfs seek the red shoes of a lady in order to break the spell, although it will not be easy.",
                "2019-07-12", 9.0, "English"));

        discoverMovies.add(new DiscoverMovie("/jpfkzbIXgKZqCZAkEkFH2VYF63s.jpg",
                "/a1MlbLBk5Sy6YvMbSuKfwGlDVlb.jpg", 920, "Cars",
                "Lightning McQueen, a hotshot rookie race car driven to succeed, discovers that life is about the journey, not the finish line, when he finds himself unexpectedly detoured in the sleepy Route 66 town of Radiator Springs. On route across the country to the big Piston Cup Championship in California to compete against two seasoned pros, McQueen gets to know the town's offbeat characters.",
                "2019-07-25", 6.7, "English"));

        return discoverMovies;
    }

    public static DiscoverMovie getMovie(Integer id) {
        for (int i = 0; i < generateDummyMovies().size(); i++) {
            DiscoverMovie movie = generateDummyMovies().get(i);
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    public static ArrayList<DiscoverTvShow> generateDummyTvShows() {
        ArrayList<DiscoverTvShow> discoverTvShows = new ArrayList<>();
        discoverTvShows.add(new DiscoverTvShow("/fki3kBlwJzFp8QohL43g9ReV455.jpg",
                "/jC1KqsFx8ZyqJyQa2Ohi7xgL7XC.jpg", 60735, "The Flash",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "2014-10-07", 6.7, "English"));

        discoverTvShows.add(new DiscoverTvShow("/vT0Zsbm4GWd7llNjgWEtwY0CqOv.jpg",
                "/87eP7ITTrOWvkA4EqCuoRdyjzLy.jpg", 67195, "Legion",
                "David Haller, AKA Legion, is a troubled young man who may be more than human. Diagnosed as schizophrenic, David has been in and out of psychiatric hospitals for years. But after a strange encounter with a fellow patient, he’s confronted with the possibility that the voices he hears and the visions he sees might be real.",
                "2017-02-08", 7.6, "English"));

        discoverTvShows.add(new DiscoverTvShow("/dLlnzbDCblBXcJqFLXyvN43NIwp.jpg",
                "/1Ep6YHL5QcrNC1JN6RYalWRPopi.jpg", 86031, "Dr. Stone",
                "The science-fiction adventure follows two boys struggle to revive humanity after a mysterious crisis has left everyone in the world turned to stone for several millennia.",
                "2019-07-12", 7.3, "Japanese"));

        discoverTvShows.add(new DiscoverTvShow("/MoEKaPFHABtA1xKoOteirGaHl1.jpg",
                "/piuRhGiQBYWgW668eSNJ2ug5uAO.jpg", 71446, "Money Heist",
                "To carry out the biggest heist in history, a mysterious man called The Professor recruits a band of eight robbers who have a single characteristic: none of them has anything to lose. Five months of seclusion - memorizing every step, every detail, every probability - culminate in eleven days locked up in the National Coinage and Stamp Factory of Spain, surrounded by police forces and with dozens of hostages in their power, to find out whether their suicide wager will lead to everything or nothing.",
                "2019-07-05", 6.6, "English"));

        discoverTvShows.add(new DiscoverTvShow("/mo0FP1GxOFZT4UDde7RFDz5APXF.jpg",
                "/dKxkwAJfGuznW8Hu0mhaDJtna0n.jpg", 1412, "Arrow",
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                "2012-10-10", 5.8, "English"));

        discoverTvShows.add(new DiscoverTvShow("/x2LSRK2Cm7MZhjluni1msVJ3wDF.jpg",
                "/56v2KjBlU4XaOv9rVYEQypROD7P.jpg", 66732, "Stranger Things",
                "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces, and one strange little girl.",
                "2016-07-15", 8.3, "English"));

        discoverTvShows.add(new DiscoverTvShow("/tPsvhL45f1AjES5rycFIxnbaH8v.jpg",
                "/dUn5B2B5BaM2de21Z3fZP79NK4m.jpg", 90670, "Pandora",
                "In the year 2199, a young woman who has lost everything finds a new life at Earth's Space Training Academy where she learns to defend the galaxy from intergalactic threats.",
                "2019-07-16", 6.3, "English"));

        discoverTvShows.add(new DiscoverTvShow("/b71BaRjp9TwxUZodLGgSRIlkfL8.jpg",
                "/7AKhSfJHnVi0zXQS4eJirHDs22p.jpg", 11634, "See No Evil: The Moors Murders",
                "The dramatisation of one of the most notorious killing sprees in British history.",
                "2006-05-14", 4.8, "English"));

        discoverTvShows.add(new DiscoverTvShow("/wBzNjurA8ijJPF21Ggs9nbviIzi.jpg",
                "/qYTIuJJ7fIehicAt3bl0vW70Sq6.jpg", 48866, "The 100",
                "100 years in the future, when the Earth has been abandoned due to radioactivity, the last surviving humans live on an ark orbiting the planet — but the ark won't last forever. So the repressive regime picks 100 expendable juvenile delinquents to send down to Earth to see if the planet is still habitable.",
                "2014-03-19", 6.5, "English"));

        discoverTvShows.add(new DiscoverTvShow("/3wx3EAMtqnbSLhGG8NrqXriCUIQ.jpg",
                "/iflq7ZJfso6WC7gk9l1tD3unWK.jpg", 12609, "Dragon Ball",
                "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the dragon balls brought her to Goku's home. Together, they set off to find all seven dragon balls in an adventure.",
                "1986-02-26", 7.1, "Japanese"));

        return discoverTvShows;
    }

    public static DiscoverTvShow getTvShow(Integer id) {
        for (int i = 0; i < generateDummyTvShows().size(); i++) {
            DiscoverTvShow tvShow = generateDummyTvShows().get(i);
            if (tvShow.getId().equals(id)) {
                return tvShow;
            }
        }
        return null;
    }
}
