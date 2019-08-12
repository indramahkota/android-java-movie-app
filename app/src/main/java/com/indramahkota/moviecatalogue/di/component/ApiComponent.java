package com.indramahkota.moviecatalogue.di.component;

import android.app.Application;

import com.indramahkota.moviecatalogue.MovieCatalogueApp;
import com.indramahkota.moviecatalogue.di.module.ActivityModule;
import com.indramahkota.moviecatalogue.di.module.ApiModule;
import com.indramahkota.moviecatalogue.di.module.AppModule;
import com.indramahkota.moviecatalogue.di.module.DbModule;
import com.indramahkota.moviecatalogue.di.module.ExecutorModule;
import com.indramahkota.moviecatalogue.di.module.FragmentModule;
import com.indramahkota.moviecatalogue.di.module.RepositoryModule;
import com.indramahkota.moviecatalogue.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
    modules = {
            AppModule.class,
            ApiModule.class,
            DbModule.class,
            RepositoryModule.class,
            ViewModelModule.class,
            AndroidSupportInjectionModule.class,
            ActivityModule.class,
            FragmentModule.class,
            ExecutorModule.class
    }
)

@Singleton
public interface ApiComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        @BindsInstance
        Builder dbModule(DbModule dbModule);

        ApiComponent build();
    }

    void inject(MovieCatalogueApp movieCatalogueApp);
}
