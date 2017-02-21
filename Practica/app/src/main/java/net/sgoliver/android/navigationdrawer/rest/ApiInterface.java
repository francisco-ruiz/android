package net.sgoliver.android.navigationdrawer.rest;

import net.sgoliver.android.navigationdrawer.model.MoviesResponse;
import net.sgoliver.android.navigationdrawer.model.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/now_playing")
    Call<MoviesResponse> getMoviesNowPlaying(@Query("api_key") String apiKey, @Query("language") String Language);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("language") String Language);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String Language);

    @GET("tv/top_rated")
    Call<TVResponse> getTopRatedTV(@Query("api_key") String apiKey, @Query("language") String Language);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String Language);

    @GET("tv/{id}")
    Call<TVResponse> getTVDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String Language);
}
