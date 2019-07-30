package com.indramahkota.moviecatalogue.di.component;

import com.indramahkota.moviecatalogue.MovieCatalogueApp;
import com.indramahkota.moviecatalogue.di.module.ActivityBindingModule;
import com.indramahkota.moviecatalogue.di.module.ApiModule;
import com.indramahkota.moviecatalogue.di.module.ApplicationModule;
import com.indramahkota.moviecatalogue.di.module.RxModule;
import com.indramahkota.moviecatalogue.di.scope.AppScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        ApiModule.class, RxModule.class})
public interface ApplicationComponent extends AndroidInjector<MovieCatalogueApp> {
    void inject(MovieCatalogueApp application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MovieCatalogueApp application);
        ApplicationComponent build();
    }
}
