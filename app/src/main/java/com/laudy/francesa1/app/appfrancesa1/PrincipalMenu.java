package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PrincipalMenu  extends AppCompatActivity implements OnClickListener {

    //Declaro variables
    ///////////////////Button btnSalir;
    Button btnVerDossiers;
    Button btnVerLogros;
    Button btnVerPerfil;
    TextView lblBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);

        //Mapeo (Prepara btn para escuchar click)
        btnVerDossiers=(Button) findViewById(R.id.btnVerDossiers);
        btnVerLogros=(Button) findViewById(R.id.btnVerLogros);
        btnVerPerfil=(Button) findViewById(R.id.btnVerPerfil);
        lblBienvenido=(TextView) findViewById(R.id.lblBienvenido);

        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado

        if(extras != null){
            String dato = extras.getString(constantes.USUARIO);
            lblBienvenido.setText("Bienvenido " + dato + "!");
            Toast.makeText(getApplicationContext(), extras.getString(constantes.USUARIO), Toast.LENGTH_LONG).show();
        }

        //(Escucha click)
        btnVerDossiers.setOnClickListener(this);
        btnVerLogros.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){

            //Evento btn Ver Perfil
            case R.id.btnVerDossiers:
                Intent intentVerDossiers = new Intent(this, VerDossiers.class);
                startActivity(intentVerDossiers);
                break;
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
        }
    }
}