package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DossierDos extends AppCompatActivity implements View.OnClickListener {

    //Declaro variables
    ////////////////Button btnSalir;
    Button btnVerLogros;
    Button btnVerDossiers;
    Button btnVerPerfil;
    //Button btnAct1;
    //Button btnAct2;
    //Button btnAct3;
    //Button btnAct4;
    //Button btnAct5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_dos);

        //Mapeo (Prepara btn para escuchar click)
        btnVerLogros=(Button) findViewById(R.id.btnVerLogros);
        btnVerDossiers=(Button) findViewById(R.id.btnVerDossiers);
        btnVerPerfil=(Button) findViewById(R.id.btnVerPerfil);
        //btnAct1=(Button) findViewById(R.id.btnAct1);
        //btnAct2=(Button) findViewById(R.id.btnAct2);
        //btnAct3=(Button) findViewById(R.id.btnAct3);
        //btnAct4=(Button) findViewById(R.id.btnAct4);
        //btnAct5=(Button) findViewById(R.id.btnAct5);

        //(Escucha click)
        btnVerLogros.setOnClickListener(this);
        btnVerDossiers.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);
        //btnAct1.setOnClickListener(this);
        //btnAct2.setOnClickListener(this);
        //btnAct3.setOnClickListener(this);
        //btnAct4.setOnClickListener(this);
        //btnAct5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){

            //Evento btn Ver dossiers
            case R.id.btnVerLogros:
                Intent intentVerLogros = new Intent(this, VerLogros.class);
                startActivity(intentVerLogros);
                break;
            //Evento btn Ver perfil
            case R.id.btnVerDossiers:
                Intent intentVerDossiers = new Intent(this, VerDossiers.class);
                startActivity(intentVerDossiers);
                break;
            //Evento btn Ver dossiers
            case R.id.btnVerPerfil:
                Intent intentVerPerfil = new Intent(this, VerPerfil.class);
                startActivity(intentVerPerfil);
                break;
        }
    }
}
