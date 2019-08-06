package com.indramahkota.moviecatalogue.di.component;

import com.indramahkota.moviecatalogue.MovieCatalogueApp;
import com.indramahkota.moviecatalogue.di.module.ActivityModule;
import com.indramahkota.moviecatalogue.di.module.ApiModule;
import com.indramahkota.moviecatalogue.di.module.AppModule;
import com.indramahkota.moviecatalogue.di.module.DbModule;
import com.indramahkota.moviecatalogue.di.module.FragmentModule;
import com.indramahkota.moviecatalogue.di.module.RxModule;
import com.indramahkota.moviecatalogue.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
    modules = {
            AppModule.class,
            ApiModule.class,
            DbModule.class,
            ViewModelModule.class,
            AndroidSupportInjectionModule.class,
            ActivityModule.class,
            FragmentModule.class,
            RxModule.class
    }
)

@Singleton
public interface AppComponent extends AndroidInjector<MovieCatalogueApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MovieCatalogueApp movieCatalogueApp);
        AppComponent build();
    }

    void inject(MovieCatalogueApp movieCatalogueApp);
}
