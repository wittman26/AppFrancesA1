package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.laudy.francesa1.app.appfrancesa1.DTO.Dossier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class VerDossiers extends AppCompatActivity implements View.OnClickListener, RespuestaAsincrona {

    //Declaro variables
    LinearLayout linBotonesDossier;
    Button btnVerLogros;
    Button btnVerPerfil;
    Button btnSalirDossier;

    private Map<Integer, Dossier> dossierMapa = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_dossiers);

        //Mapeo (Prepara btn para escuchar click)
        linBotonesDossier = (LinearLayout) findViewById(R.id.linBotonesDossier);
        btnVerLogros =(Button) findViewById(R.id.btnVerLogros);
        btnVerPerfil =(Button) findViewById(R.id.btnVerPerfil);
        btnSalirDossier =(Button) findViewById(R.id.btnSalirDossier);

        //(Escucha click)
        btnVerLogros.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);
        btnSalirDossier.setOnClickListener(this);

        //Prepara tarea para traer datos de Dossiers
        try {
            TareaAsincrona tareaDossier = new TareaAsincrona(this);
            tareaDossier.delegar = this;

            //Prepara los parámetros de envío (En este caso no hay)
            Map<String, String> parame = new TreeMap<>();

            ParametrosURL params = new ParametrosURL(Constantes.DOSSIERS, parame);
            tareaDossier.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
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
            //Evento btn Salir
            case R.id.btnSalirDossier:
                Sesion.usuarioLogeado = null;
                Intent intentSalir = new Intent(this, Ingresar.class);
                intentSalir.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSalir);
                break;

        }
    }

    @Override
    public void traerResultados(String resultado) {
        try {
            JSONObject objetoJSON = new JSONObject(resultado);
            String exito = objetoJSON.getString("success");

            //Si se ha traido datos
            if(exito.equals("1")){

                //Leer array de JSON de acuerdo al título arrojado por el php
                JSONArray listaDossiers = new JSONArray(objetoJSON.getString("dossiers"));

                for (int i=0; i < listaDossiers.length(); i++) {
                    JSONObject objetoJSONDossier = listaDossiers.getJSONObject(i);

                    Dossier dossiers = new Dossier();

                    dossiers.iniciarValores(objetoJSONDossier);

                    //Adiciona datos a mapa dossierMapa//
                    dossierMapa.put(dossiers.getIddossier(),dossiers );

                    agregarBotonesDossier(dossiers);
                }

            } else {
                Toast.makeText(this, "Error al obtener datos: " + objetoJSON.getString("message"), Toast.LENGTH_LONG).show();
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

        //También se puede Color.WHITE
        boton.setTextColor(Color.rgb(51,0,153));
        boton.setBackgroundColor(Color.rgb(180, 180, 232));
        //boton.setTextSize(2,25);
        boton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);//Tipo de unidad y cantidad

        //Define las márgenes de los BotonesDossier
        final ViewGroup.MarginLayoutParams lpt =(ViewGroup.MarginLayoutParams)boton.getLayoutParams();
        //izq,arriba,der,fondo
        lpt.setMargins(300,10,300,lpt.bottomMargin);

        //Se coloca texto e Id al boton
        boton.setText(dossier.getNombredossier());
        boton.setId(dossier.getIddossier());

        boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent actividades = new Intent(v.getContext(), VerActAprend.class);
                //Envía datos extra
                actividades.putExtra(Constantes.IDDOSSIER, v.getId());
//                actividades.putExtra(Constantes.IDDOSSIER, dossierMapa.get(v.getId()).getIddossier());
                actividades.putExtra(Constantes.NOMBREDOSSIER, dossierMapa.get(v.getId()).getNombredossier());

                startActivity(actividades);
            }
        });
    }

}
