package com.indramahkota.moviecatalogue.data.source;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private Observable<Resource<ResultType>> result;

    @MainThread
    NetworkBoundResource() {
        Observable<Resource<ResultType>> source;
        if (shouldFetch()) {
            source = createCall()
                    .subscribeOn(Schedulers.io())
                    .doOnNext(apiResponse -> saveCallResult(processResponse(apiResponse)))
                    .flatMap(apiResponse -> loadFromDb().toObservable().map(Resource::success))
                    .doOnError(t -> onFetchFailed())
                    .onErrorResumeNext(t -> {
                        return loadFromDb()
                                .toObservable()
                                .map(data -> Resource.error(t.getMessage(), data));
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            source = loadFromDb()
                    .toObservable()
                    .map(Resource::success);
        }

        result = Observable.concat(
                loadFromDb()
                        .toObservable()
                        .map(Resource::loading)
                        .take(1),
                source
        );
    }

    @NonNull
    @MainThread
    protected abstract Observable<Resource<RequestType>> createCall();

    private void onFetchFailed() {}

    @WorkerThread
    private RequestType processResponse(@NonNull Resource<RequestType> response) {return response.data;}

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(ResultType data);

    @NonNull
    @MainThread
    protected abstract Flowable<ResultType> loadFromDb();

    Observable<Resource<ResultType>> getAsObservable() {return result;}
}