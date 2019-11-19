package com.indramahkota.moviecatalogue.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.indramahkota.moviecatalogue.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String STATE_HANDLER = "state_handler";

    private Boolean stateHandler = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            stateHandler = savedInstanceState.getBoolean(STATE_HANDLER);
        }

        if(!stateHandler) {
            stateHandler = true;
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }, 2000);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_HANDLER, stateHandler);
    }
}
