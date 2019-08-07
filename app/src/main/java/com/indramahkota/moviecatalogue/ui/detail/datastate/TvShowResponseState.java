package com.indramahkota.moviecatalogue.ui.detail.datastate;

import com.indramahkota.moviecatalogue.data.source.remote.response.base.BaseResponseState;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

public class TvShowResponseState extends BaseResponseState<TvShowEntity> {
    private TvShowResponseState(TvShowEntity data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static TvShowResponseState ERROR_STATE = new TvShowResponseState(null, State.ERROR.value, new Throwable());
    public static TvShowResponseState LOADING_STATE = new TvShowResponseState(null, State.LOADING.value, null);
    public static TvShowResponseState SUCCESS_STATE = new TvShowResponseState(new TvShowEntity(), State.SUCCESS.value, null);
}
