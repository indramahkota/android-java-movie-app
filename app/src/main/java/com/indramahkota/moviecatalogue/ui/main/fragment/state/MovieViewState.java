package com.indramahkota.moviecatalogue.ui.main.fragment.state;

import com.indramahkota.moviecatalogue.data.source.remote.base.BaseViewState;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;

public class MovieViewState extends BaseViewState<DiscoverMovieResponse> {
    private MovieViewState(DiscoverMovieResponse data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static MovieViewState ERROR_STATE = new MovieViewState(null, State.ERROR.value, new Throwable());
    public static MovieViewState LOADING_STATE = new MovieViewState(null, State.LOADING.value, null);
    public static MovieViewState SUCCESS_STATE = new MovieViewState(new DiscoverMovieResponse(), State.SUCCESS.value, null);
}
