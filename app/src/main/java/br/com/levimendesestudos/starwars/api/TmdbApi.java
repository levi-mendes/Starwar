package br.com.levimendesestudos.starwars.api;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by Levi on 04/02/2017.
 *
 * Themoviedb api
 */

public interface TmdbApi {

    String BASE_URL  = "https://api.themoviedb.org/";
    String API_KEY   = "38bbb298e8e249fab64461f12ada6c81";

    /*
    @Headers("Content-Type: application/json")
    @GET("/3/search/movie")
    Observable<Integer> search(@QueryMap Map<String, String> params);//vamos usar para pegar o id

    */


    @Headers("Content-Type: application/json")
    @GET(".")
    Observable<Integer> buscaId();


    //https://api.themoviedb.org/3/movie/11?api_key=38bbb298e8e249fab64461f12ada6c81
}