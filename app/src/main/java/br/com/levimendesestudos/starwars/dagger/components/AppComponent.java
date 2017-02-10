package br.com.levimendesestudos.starwars.dagger.components;

import javax.inject.Singleton;
import br.com.levimendesestudos.starwars.dagger.modules.FilmeDBModule;
import br.com.levimendesestudos.starwars.dagger.modules.PersonagemDBModule;
import br.com.levimendesestudos.starwars.dagger.modules.TmdbApiModule;
import br.com.levimendesestudos.starwars.dagger.modules.TmdbImageApiModule;
import br.com.levimendesestudos.starwars.mvp.presenter.MainPresenter;
import dagger.Component;

@Component(modules = {
        PersonagemDBModule.class,
        FilmeDBModule.class,
        TmdbApiModule.class,
        TmdbImageApiModule.class
})
@Singleton
public interface AppComponent {

    void inject(MainPresenter presenter);
}