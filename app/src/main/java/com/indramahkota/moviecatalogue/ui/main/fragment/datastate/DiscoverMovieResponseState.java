package com.indramahkota.moviecatalogue.ui.main.fragment.datastate;

import com.indramahkota.moviecatalogue.data.source.remote.base.BaseResponseState;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;

public class DiscoverMovieResponseState extends BaseResponseState<DiscoverMovieResponse> {
    private DiscoverMovieResponseState(DiscoverMovieResponse data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static DiscoverMovieResponseState ERROR_STATE = new DiscoverMovieResponseState(null, State.ERROR.value, new Throwable());
    public static DiscoverMovieResponseState LOADING_STATE = new DiscoverMovieResponseState(null, State.LOADING.value, null);
    public static DiscoverMovieResponseState SUCCESS_STATE = new DiscoverMovieResponseState(new DiscoverMovieResponse(), State.SUCCESS.value, null);
}
