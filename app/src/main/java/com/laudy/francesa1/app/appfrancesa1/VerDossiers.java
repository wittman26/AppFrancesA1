package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.laudy.francesa1.app.appfrancesa1.DTO.Dossier;
import com.laudy.francesa1.app.appfrancesa1.DTO.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class VerDossiers extends AppCompatActivity implements View.OnClickListener, respuestaAsincrona {

    //Declaro variables
    ////////////////Button btnSalir;
    LinearLayout linBotonesDossier;
    Button btnVerLogros;
    Button btnVerPerfil;
    Dossier dossierAux = new Dossier();
    private Map<Integer, Dossier> auxiliar = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_dossiers);

        //Mapeo (Prepara btn para escuchar click)
        linBotonesDossier = (LinearLayout) findViewById(R.id.linBotonesDossier);
        btnVerLogros =(Button) findViewById(R.id.btnVerLogros);
        btnVerPerfil =(Button) findViewById(R.id.btnVerPerfil);

        //(Escucha click)
        btnVerLogros.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);

            try {
                tareaAsincrona tareaDossier = new tareaAsincrona(this);
                tareaDossier.delegar = this;

                //Prepara los parámetros de envío
                Map<String, String> parame = new TreeMap<>();

                parametrosURL params = new parametrosURL(constantes.DOSSIERS, parame);
                tareaDossier.execute(params);
            } catch (Exception e) {
                Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
            }
    }
    @Override
    public void traerResultados(String resultado) {
        try {
            JSONObject objetoJSON = new JSONObject(resultado);
            String exito = objetoJSON.getString("success");

            //Si se ha traido datos
            if(exito.equals("1")){

//                Dossier dossiers = new Dossier();
                //Leer array de JSON de acuerdo al título arrojado por el php
                JSONArray listaDossiers = new JSONArray(objetoJSON.getString("dossiers"));

                for (int i=0; i < listaDossiers.length(); i++) {
                    JSONObject objetoJSONDossier = listaDossiers.getJSONObject(i);

                    Dossier dossiers = new Dossier();

                    dossiers.setIddossier(Integer.parseInt(objetoJSONDossier.getString("iddossier")));
                    dossiers.setNombredossier(objetoJSONDossier.getString("nombredossier"));

                    //Adiciona datos a mapa auxiliar
                    auxiliar.put(dossiers.getIddossier(),dossiers );
                    agregarBotonesDossier(dossiers);
                }

            } else {
                Toast.makeText(this, "Ocurrió un error al obtener datos", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void agregarBotonesDossier(Dossier dossier){
        Button boton = new Button(this);
        boton.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        //Se adiciona boton antes de dar estilos
        linBotonesDossier.addView(boton);

        boton.setTextColor(Color.parseColor("#330099"));

        //También se puede Color.WHITE
        boton.setTextColor(Color.rgb(51,0,153));
        boton.setBackgroundColor(Color.rgb(136,136,216));
        boton.setTextSize(2,25);

        //Define las márgenes de los BotonesDossier
        final ViewGroup.MarginLayoutParams lpt =(ViewGroup.MarginLayoutParams)boton.getLayoutParams();
        //izq,arriba,der,fondo
        lpt.setMargins(200,10,200,lpt.bottomMargin);

        boton.setText(dossier.getNombredossier());
        boton.setId(dossier.getIddossier());

        boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Toast.makeText(getApplicationContext(), " Listener botón " + v.getId(), Toast.LENGTH_SHORT).show();
                Intent actividades = new Intent(v.getContext(), DossierUno.class);
                actividades.putExtra(constantes.IDDOSSIER, v.getId());
                //Envía datos extra
                actividades.putExtra("prueba", auxiliar.get(v.getId()).getNombredossier());
                startActivity(actividades);
            }
        });
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

        }
    }


}
