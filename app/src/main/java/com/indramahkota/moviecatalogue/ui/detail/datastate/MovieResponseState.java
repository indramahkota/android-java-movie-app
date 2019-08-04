package com.indramahkota.moviecatalogue.ui.detail.datastate;

import com.indramahkota.moviecatalogue.data.source.remote.response.base.BaseResponseState;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;

public class MovieResponseState extends BaseResponseState<MovieResponse> {
    private MovieResponseState(MovieResponse data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static MovieResponseState ERROR_STATE = new MovieResponseState(null, State.ERROR.value, new Throwable());
    public static MovieResponseState LOADING_STATE = new MovieResponseState(null, State.LOADING.value, null);
    public static MovieResponseState SUCCESS_STATE = new MovieResponseState(new MovieResponse(), State.SUCCESS.value, null);
}
