package br.com.levimendesestudos.starwars.api;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.ResponseBody;

import java.util.Map;

import br.com.levimendesestudos.starwars.model.Filme;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import retrofit.http.Streaming;
import retrofit.http.Url;
import rx.Observable;

/**
 * Created by Levi on 04/02/2017.
 *
 * Themoviedb api
 */

public interface TmdbApi {

    String API_KEY   = "38bbb298e8e249fab64461f12ada6c81";
    String BASE_URL  = "https://api.themoviedb.org/";
    String BASE_URL_DOWNLOAD_IAMGE = "https://image.tmdb.org/";

    //api key
    //38bbb298e8e249fab64461f12ada6c81
    //https://api.themoviedb.org/3/movie/550?api_key=38bbb298e8e249fab64461f12ada6c81



    //https://api.themoviedb.org/3/search/movie?api_key=38bbb298e8e249fab64461f12ada6c81&query=starwars


    //C:\Users\Levi\AppData\Local\Android\sdk\platform-tools

    /*

    https://api.themoviedb.org
    /3/search/movie
    ?api_key=38bbb298e8e249fab64461f12ada6c81
    &language=en-US
    &query=a%20new%20hope
    &page=1
    &include_adult=false
    &year=1977

     */

    @Headers("Content-Type: application/json")
    //@GET("/search/movie?api_key=38bbb298e8e249fab64461f12ada6c81&query=starwars")
    @GET("/3/search/movie")
    Observable<Filme> search(@QueryMap Map<String, String> params);

    @Streaming
    //@GET("t/p/w500")
    @GET("/t/p/w500/{image}")
    //Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);
    //https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Path("image") String image);
}