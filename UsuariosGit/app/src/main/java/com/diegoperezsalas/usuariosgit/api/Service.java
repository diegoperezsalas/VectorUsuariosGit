package com.diegoperezsalas.usuariosgit.api;

import com.diegoperezsalas.usuariosgit.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Service {

    @GET("/search/users?q=language:java+location:vigo")
    Call<ItemResponse> getItems();


}
