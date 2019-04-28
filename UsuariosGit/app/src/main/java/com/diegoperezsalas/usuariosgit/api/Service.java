package com.diegoperezsalas.usuariosgit.api;

import com.diegoperezsalas.usuariosgit.model.ItemResponse;
import com.diegoperezsalas.usuariosgit.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Service {


    @GET(value = "/search/users?q=language:java+location:vigo")
    Call<ItemResponse> getItems();

    @GET("/users/{user}")
    Call<Post> getInfoUser(@Path("user") String usuario);


}
