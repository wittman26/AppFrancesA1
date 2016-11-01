package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerPerfil extends AppCompatActivity implements View.OnClickListener {

    //Declaro variables
    ////////////////Button btnSalir;
    ////////////////Button btnEliminarPerfil;
    Button btnAtras;
    Button btnModificarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        //Mapeo (Prepara btn para escuchar click)
        btnAtras=(Button) findViewById(R.id.btnAtras);
        btnModificarPerfil=(Button) findViewById(R.id.btnModificarPerfil);

        //(Escucha click)
        btnAtras.setOnClickListener(this);
        btnModificarPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){

            //Evento btn Atras
            case R.id.btnAtras:
                Intent intentPrincipalMenu = new Intent(this, PrincipalMenu.class);
                startActivity(intentPrincipalMenu);
                break;
            //Evento btn Ver perfil
            case R.id.btnModificarPerfil:
                Intent intentRegistrar = new Intent(this, Registrar.class);
                startActivity(intentRegistrar);
                break;
        }
    }
}