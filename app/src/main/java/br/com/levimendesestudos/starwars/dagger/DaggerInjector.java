package br.com.levimendesestudos.starwars.dagger;

import br.com.levimendesestudos.starwars.dagger.components.AppComponent;
import br.com.levimendesestudos.starwars.dagger.components.DaggerAppComponent;
import br.com.levimendesestudos.starwars.dagger.modules.AppModule;
import br.com.levimendesestudos.starwars.dagger.modules.PersonagemDBModule;

public class DaggerInjector {

    private static AppComponent appComponent = DaggerAppComponent.builder().personagemDBModule(new PersonagemDBModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
