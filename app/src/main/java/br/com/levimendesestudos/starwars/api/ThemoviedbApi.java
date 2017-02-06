package br.com.levimendesestudos.starwars.api;

import java.util.Map;

import br.com.levimendesestudos.starwars.model.Filme;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by Levi on 04/02/2017.
 */

public interface ThemoviedbApi {
    //api key
    //38bbb298e8e249fab64461f12ada6c81
    //https://api.themoviedb.org/3/movie/550?api_key=38bbb298e8e249fab64461f12ada6c81



    //https://api.themoviedb.org/3/search/movie?api_key=38bbb298e8e249fab64461f12ada6c81&query=starwars


    //C:\Users\Levi\AppData\Local\Android\sdk\platform-tools

    @Headers("Content-Type: application/json")
    //@GET("/search/movie?api_key=38bbb298e8e249fab64461f12ada6c81&query=starwars")
    @GET("/search/movie")
    Observable<Filme> search(@QueryMap Map<String, String> params);
}