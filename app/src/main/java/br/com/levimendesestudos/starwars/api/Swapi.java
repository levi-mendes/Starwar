package br.com.levimendesestudos.starwars.api;

import br.com.levimendesestudos.starwars.model.Personagem;
import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by 809778 on 03/02/2017.
 */

public interface Swapi {

    /**
     * Content type json
     */
    String CT_APP_JSON = "Content-Type: application/json";

    /**
     * @return Retorna o personagem
     */
    @Headers(CT_APP_JSON)
    @GET(".")// indica que o campo baseUrl sera o unico parametro
    Observable<Personagem> people();

}
