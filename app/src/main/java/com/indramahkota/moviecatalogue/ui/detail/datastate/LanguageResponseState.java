package com.indramahkota.moviecatalogue.ui.detail.datastate;

import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.base.BaseResponseState;

public class LanguageResponseState extends BaseResponseState<LanguageResponse> {
    private LanguageResponseState(LanguageResponse data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static LanguageResponseState ERROR_STATE = new LanguageResponseState(null, State.ERROR.value, new Throwable());
    public static LanguageResponseState LOADING_STATE = new LanguageResponseState(null, State.LOADING.value, null);
    public static LanguageResponseState SUCCESS_STATE = new LanguageResponseState(new LanguageResponse(), State.SUCCESS.value, null);
}
