package com.diegoperezsalas.usuariosgit.controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.diegoperezsalas.usuariosgit.ItemAdapter;
import com.diegoperezsalas.usuariosgit.R;
import com.diegoperezsalas.usuariosgit.api.Client;
import com.diegoperezsalas.usuariosgit.api.Service;
import com.diegoperezsalas.usuariosgit.model.Item;
import com.diegoperezsalas.usuariosgit.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /*
    Utilizaremos RecyclerView que es un contenedor en forma de lista muy parecido a la clase ListView.
    Este nuevo elemento permite reciclar los ítems que ya son visibles por el usuario.

    Estamos declarando un objeto TextView que llamaremos Disconnected y lo vamos enlazar con nuetra vista.

    Igualmente declararemos dos objetos mas un ProgressDialog que es una barra de progreso y el SwipeRefresh
    que nos permite refrescar los items del RecyclerView cada vez que lo deslizamos hacia abajo o hacia arriba
    en cada end.

     */

    private RecyclerView recyclerView;
    TextView Disconnected;
    private Item item;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;

    /*
     Creamos nuestra vista principal

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(MainActivity.this,"Cargando Usuarios Github en Vigo", Toast.LENGTH_SHORT).show();

            }
        });
    }


    /*
      Procedimiento donde inicializamos nuestro recyclerView y mostramos nuestra barra de progreso
      y procedemos a llamar el metodo donde cargamos la data de Json.
     */
    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Mostrando usuarios GitHub en Vigo");
        pd.setCancelable(false);
        pd.show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();


    }

    /*
      En este metodo llamamos a nuestras api y cargamos los items
     */

    private void loadJSON(){
        //Asignamos un a nuestro objeto TextView una vista
        Disconnected = (TextView) findViewById(R.id.disconnected);


        try{
            //creamos un objeto de nuestra clase Client
            Client Client = new Client();

            //Creamos un objeto llamado apiServices desde nuestra clase Service y le asignamos el metodo de Retrofit getClient

            Service apiService = Client.getClient().create(Service.class);

            //Call Retrofit2 < utlizamos el modelo de datos model/ItemResponse.java >

            Call<ItemResponse> call = apiService.getItems();

            // Consultamos el api
            call.enqueue(new Callback<ItemResponse>() {


                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {


                       List<Item> items = response.body().getItems();
                       recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items));
                       recyclerView.smoothScrollToPosition(0);
                       swipeContainer.setRefreshing(false);
                       pd.hide();

                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error mostrando Datos!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();

                }
            });
        }catch (Exception e){Log.d("Error", e.getMessage());
         Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

}





