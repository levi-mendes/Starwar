package br.com.levimendesestudos.starwars.api;

import com.squareup.okhttp.ResponseBody;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Streaming;

/**
 * Created by Levi on 04/02/2017.
 *
 * Themoviedb api
 */

public interface TmdbImageApi {

    //https://api.themoviedb.org/3/search/movie?api_key=38bbb298e8e249fab64461f12ada6c81&language=en-US&query=a%20new%20hope&page=1&include_adult=false&year=1977

    //https://api.themoviedb.org/3/movie/11?api_key=38bbb298e8e249fab64461f12ada6c81&language=en-US

    String BASE_URL_DOWNLOAD_IAMGE = "https://image.tmdb.org/";

    @Streaming
    @GET("/t/p/w500/{image}")
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Path("image") String image);
}