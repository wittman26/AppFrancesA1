package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    //Declaro variables
    Button btnIniciarSesion;
    Button btnCrearPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapeo (Prepara btn para escuchar click)
        btnIniciarSesion=(Button) findViewById(R.id.btnIniciarSesionMain);
        btnCrearPerfil=(Button) findViewById(R.id.btnCrearPerfilMain);

        //(Escucha click)
        btnIniciarSesion.setOnClickListener(this);
        btnCrearPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){

            //Evento btn Ingresar
            case R.id.btnIniciarSesionMain:
                Intent intentIngresar = new Intent(this, Ingresar.class);
                startActivity(intentIngresar);
                break;
            //Evento btn Registrar
            case R.id.btnCrearPerfilMain:
                Intent intentRegistrar = new Intent(this, Registrar.class);
                startActivity(intentRegistrar);
                break;
        }
    }
}
