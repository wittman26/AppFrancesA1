package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerDossiers extends AppCompatActivity implements View.OnClickListener {

    //Declaro variables
    ////////////////Button btnSalir;
    Button btnVerLogros;
    Button btnVerPerfil;
    Button btnDossier1;
    Button btnDossier2;
    Button btnDossier3;
    Button btnDossier4;
    Button btnDossier5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_dossiers);

        //Mapeo (Prepara btn para escuchar click)
        btnVerLogros=(Button) findViewById(R.id.btnVerLogros);
        btnVerPerfil=(Button) findViewById(R.id.btnVerPerfil);
        btnDossier1=(Button) findViewById(R.id.btnDossier1);
        btnDossier2=(Button) findViewById(R.id.btnDossier2);
        btnDossier3=(Button) findViewById(R.id.btnDossier3);
        btnDossier4=(Button) findViewById(R.id.btnDossier4);
        btnDossier5=(Button) findViewById(R.id.btnDossier5);

        //(Escucha click)
        btnVerLogros.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);
        btnDossier1.setOnClickListener(this);
        btnDossier2.setOnClickListener(this);
        btnDossier3.setOnClickListener(this);
        btnDossier4.setOnClickListener(this);
        btnDossier5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){

            //Evento btn Ver logros
            case R.id.btnVerLogros:
                Intent intentVerLogros = new Intent(this, VerLogros.class);
                startActivity(intentVerLogros);
                break;
            //Evento btn Ver perfil
            case R.id.btnVerPerfil:
                Intent intentVerPerfil = new Intent(this, VerPerfil.class);
                startActivity(intentVerPerfil);
                break;
            //Evento btn Ver dossiers
            case R.id.btnDossier1:
                Intent intentDossierUno = new Intent(this, DossierUno.class);
                startActivity(intentDossierUno);
                break;
            //Evento btn Ver dossiers
            case R.id.btnDossier2:
                Intent intentDossierDos = new Intent(this, DossierDos.class);
                startActivity(intentDossierDos);
                break;
            //Evento btn Ver dossiers
            case R.id.btnDossier3:
                Intent intentDossierTres = new Intent(this, DossierTres.class);
                startActivity(intentDossierTres);
                break;
            //Evento btn Ver dossiers
            case R.id.btnDossier4:
                Intent intentDossierCuatro = new Intent(this, DossierCuatro.class);
                startActivity(intentDossierCuatro);
                break;
            //Evento btn Ver dossiers
            case R.id.btnDossier5:
                Intent intentDossierCinco = new Intent(this, DossierCinco.class);
                startActivity(intentDossierCinco);
                break;

        }
    }
}
