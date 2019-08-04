package com.indramahkota.moviecatalogue.data.source.remote.rxscheduler;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface ObservableSchedulers {
    ObservableSchedulers DEFAULT = new ObservableSchedulers() {
        @Override
        public <T> ObservableTransformer<T, T> applySchedulers() {
            return observable -> observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    ObservableSchedulers TEST_SCHEDULER = new ObservableSchedulers() {
        @Override
        public <T> ObservableTransformer<T, T> applySchedulers() {
            return observable -> observable
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }
    };

    <T> ObservableTransformer<T, T> applySchedulers();
}
