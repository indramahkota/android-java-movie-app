package com.indramahkota.moviecatalogue.ui.main.fragment.datastate;

import com.indramahkota.moviecatalogue.data.source.remote.base.BaseResponseState;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

public class DiscoverTvShowResponseState extends BaseResponseState<DiscoverTvShowResponse> {
    private DiscoverTvShowResponseState(DiscoverTvShowResponse data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static DiscoverTvShowResponseState ERROR_STATE = new DiscoverTvShowResponseState(null, State.ERROR.value, new Throwable());
    public static DiscoverTvShowResponseState LOADING_STATE = new DiscoverTvShowResponseState(null, State.LOADING.value, null);
    public static DiscoverTvShowResponseState SUCCESS_STATE = new DiscoverTvShowResponseState(new DiscoverTvShowResponse(), State.SUCCESS.value, null);
}
