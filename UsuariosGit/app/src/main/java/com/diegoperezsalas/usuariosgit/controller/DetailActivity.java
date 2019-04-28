package com.diegoperezsalas.usuariosgit.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diegoperezsalas.usuariosgit.R;
import com.diegoperezsalas.usuariosgit.api.Client;
import com.diegoperezsalas.usuariosgit.api.Service;
import com.diegoperezsalas.usuariosgit.model.Item;
import com.diegoperezsalas.usuariosgit.model.ItemResponse;
import com.diegoperezsalas.usuariosgit.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


public class DetailActivity extends AppCompatActivity {
    /* Se declaran los objetos de la libreria

    */
    TextView Link, Username, Name, Company, Location;
    Toolbar mActionBarToolbar;
    ImageView imageView;

    public void onCreate(Bundle saveInstanceState){

    /*
     Aca declaramos que esta libraria trabajará con el layout activity_detail
    */
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_detail);
    /*
     En este paso colocamos el botón de regrasar en la barra de navegación
    */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Details Activity");


     /*
       Asignamos a nuestros objetos parte de nuestra vista o layout
     */
        imageView = (ImageView) findViewById(R.id.user_image_header);
        Username = (TextView) findViewById(R.id.username);
        Link = (TextView) findViewById(R.id.link);
        Name = (TextView) findViewById(R.id.fullname);
        Company = (TextView) findViewById(R.id.company);
        Location = (TextView) findViewById(R.id.location);
     /*
      Recibimos del Main Activity las siguientes variables de texto String: login, avatar_url y html_url
     */

        String username = getIntent().getExtras().getString("login");
        String avatarURL = getIntent().getExtras().getString("avatar_url");
        String link = getIntent().getExtras().getString("html_url");
        String github_user = getIntent().getExtras().getString("github_user");
    /*
    /*
     Asignamos a nuestros objetos de TexView un texto String
     y hacemos que nustro texto Link sea navegable utilizando Linkify
    */


        getUser(username);


        Link.setText(link);
        Linkify.addLinks(Link, Linkify.WEB_URLS);
        Username.setText(username);


     /*
      Usamos libreria Glide para asignar nuestro url avatar a el objeto del detale de la imagen.
     */
        Glide.with(this).load(avatarURL).placeholder(R.drawable.load).into(imageView);

    }

   /*
    Este preceso activa propiedades y metodos a nuestro Navigation Bar
   */
    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    /*
     Asignamos un username y link para compartir
     @return el delagate hacia compartir del Sistema Operativo.
    */
   private Intent createShareForcastIntent(){
       String username = getIntent().getExtras().getString("login");
       String link = getIntent().getExtras().getString("link");
       Intent shareIntent = ShareCompat.IntentBuilder.from(this)
       .setType("text/plain").setText("\n" + "Echa un vistazo a este increíble desarrollador @" + username + ", " + link)
       .getIntent();
       return shareIntent;
   }

   /*
     Acá asignamos a la barra de navegación o al botón de opciones del hardware la posibilidad de compartir el usuario GitHub con otras applicaciones.
     @return booleano
   */

    public boolean onCreateOptionMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForcastIntent());
        return true;


   }

   private void getUser(String usuario){
       Client Client = new Client();
       Service apiService = Client.getClient().create(Service.class);
       Call<Post> call = apiService.getInfoUser(usuario);
       call.enqueue(new Callback<Post>() {
           @Override
           public void onResponse(Call<Post> call, Response<Post> response) {


               Log.d("api_contenido","Nuevo Avance ");
               Name.setText(response.body().getName());
               Company.setText(response.body().getCompany());
               Location.setText(response.body().getLocation());
           }

           @Override
           public void onFailure(Call<Post> call, Throwable t) {
               Log.e("Error","Usuario no encontrado");
               Log.d("Error", t.getMessage());

           }
       });
   }
}
