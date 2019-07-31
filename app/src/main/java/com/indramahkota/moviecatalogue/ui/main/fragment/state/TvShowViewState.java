package com.indramahkota.moviecatalogue.ui.main.fragment.state;

import com.indramahkota.moviecatalogue.data.source.remote.base.BaseViewState;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

public class TvShowViewState extends BaseViewState<DiscoverTvShowResponse> {
    private TvShowViewState(DiscoverTvShowResponse data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static TvShowViewState ERROR_STATE = new TvShowViewState(null, State.ERROR.value, new Throwable());
    public static TvShowViewState LOADING_STATE = new TvShowViewState(null, State.LOADING.value, null);
    public static TvShowViewState SUCCESS_STATE = new TvShowViewState(new DiscoverTvShowResponse(), State.SUCCESS.value, null);
}
