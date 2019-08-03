package com.indramahkota.moviecatalogue.di.component;

import android.app.Application;

import com.indramahkota.moviecatalogue.MovieCatalogueApp;
import com.indramahkota.moviecatalogue.di.module.ActivityModule;
import com.indramahkota.moviecatalogue.di.module.ApiModule;
import com.indramahkota.moviecatalogue.di.module.FragmentModule;
import com.indramahkota.moviecatalogue.di.module.RxModule;
import com.indramahkota.moviecatalogue.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
    modules = {
            ApiModule.class,
            ViewModelModule.class,
            AndroidSupportInjectionModule.class,
            ActivityModule.class,
            FragmentModule.class,
            RxModule.class
    }
)

@Singleton
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        AppComponent build();
    }

    void inject(MovieCatalogueApp movieCatalogueApp);
}
