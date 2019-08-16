package com.indramahkota.moviecatalogue.utils;

import com.indramahkota.moviecatalogue.ui.utils.AppExecutors;

import java.util.concurrent.Executor;

public class InstantAppExecutors extends AppExecutors {
    private static Executor instant = Runnable::run;

    public InstantAppExecutors() {
        super(instant, instant, instant);
    }
}