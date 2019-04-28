package com.diegoperezsalas.usuariosgit.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Client {
    public static final String BASE_URL = "https://api.github.com";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
       //     GithubServise githubServise = retrofit.create(GithubServise.class);
       //     Call<ResponseBody> call = githubServise.getGithub();
        }
        return retrofit;
    }

   /* public interface GithubServise {
        @GET("/users/diegoperezsalas/repos")
        Call<ResponseBody> getGithub();
    }*/
}
