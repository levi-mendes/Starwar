package br.com.levimendesestudos.starwars.dagger.modules;

import br.com.levimendesestudos.starwars.ApplicationStarWarApp;
import br.com.levimendesestudos.starwars.model.db.PersonagemDB;
import dagger.Module;
import dagger.Provides;

/**
 * Created by 809778 on 06/07/2016.
 */
@Module
public class PersonagemDBModule {
    
    @Provides
    PersonagemDB providesPersonagemDB() {
        return new PersonagemDB(ApplicationStarWarApp.getAppContext());
    }
}
