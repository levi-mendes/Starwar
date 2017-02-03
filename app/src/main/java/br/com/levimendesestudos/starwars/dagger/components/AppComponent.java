package br.com.levimendesestudos.starwars.dagger.components;

import javax.inject.Singleton;

import br.com.levimendesestudos.starwars.dagger.modules.AppModule;
import br.com.levimendesestudos.starwars.dagger.modules.PersonagemDBModule;
import br.com.levimendesestudos.starwars.mvp.presenter.MainPresenter;
import dagger.Component;

@Component(modules = {
        PersonagemDBModule.class
})
@Singleton
public interface AppComponent {

    void inject(MainPresenter presenter);
}