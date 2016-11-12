package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.laudy.francesa1.app.appfrancesa1.DTO.ActAprend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class VerActAprend extends AppCompatActivity implements View.OnClickListener, RespuestaAsincrona {

    //Declaro variables
    LinearLayout linBotonesActivi;
    Button btnVerLogros;
    Button btnVerDossiers;
    Button btnVerPerfil;
    Button btnSalirAct;
    TextView lblDescripcionDossier;

    private Map<Integer, ActAprend> actaprendMapa = new TreeMap<>();

    private String iddossier ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_act_aprend);

        //Mapeo (Prepara btn para escuchar click)
        btnVerLogros=(Button) findViewById(R.id.btnVerLogros);
        btnVerDossiers=(Button) findViewById(R.id.btnVerDossiers);
        btnVerPerfil=(Button) findViewById(R.id.btnVerPerfil);
        btnSalirAct=(Button) findViewById(R.id.btnSalirAct);
        linBotonesActivi = (LinearLayout)findViewById(R.id.linBotonesActivi);
        lblDescripcionDossier = (TextView)findViewById(R.id.lblDescripcionDossier);

        //(Escucha click)
        btnVerLogros.setOnClickListener(this);
        btnVerDossiers.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);
        btnSalirAct.setOnClickListener(this);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado

        if(extras != null){
            iddossier = String.valueOf(extras.getInt(Constantes.IDDOSSIER));
            lblDescripcionDossier.setText(extras.getString(Constantes.NOMBREDOSSIER));
        }

        //Prepara tarea para traer datos de Actividades
        try {
            TareaAsincrona tareaActAprend = new TareaAsincrona(this);
            tareaActAprend.delegar = this;

            //Prepara los parámetros de envío
            Map<String, String> parame = new TreeMap<>();
            parame.put("iddossier", iddossier);

            ParametrosURL params = new ParametrosURL(Constantes.ACTAPREND, parame);
            tareaActAprend.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }

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
            //Evento btn Salir
            case R.id.btnSalirAct:
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

                ActAprend actAprend = new ActAprend();
                //Leer array de JSON de acuerdo al título arrojado por el php
                JSONArray listaActAprend = new JSONArray(objetoJSON.getString("actividades"));

                for (int i=0; i < listaActAprend.length(); i++) {
                    JSONObject objetoJSONActAprend = listaActAprend.getJSONObject(i);

                    actAprend.iniciarValores(objetoJSONActAprend);

                    //Adiciona datos a mapa auxiliar//
                    actaprendMapa.put(actAprend.getIdactaprend(),actAprend );

                    agregarBotonesActivi(actAprend);
                }

            } else {
                Toast.makeText(this, "Error al obtener datos: " + objetoJSON.getString("message"), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void agregarBotonesActivi(ActAprend actAprend){
        Button boton = new Button(this);
        boton.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        //Se adiciona boton antes de dar estilos
        linBotonesActivi.addView(boton);

        //También se puede Color.WHITE
        boton.setTextColor(Color.rgb(51,0,153));
        boton.setBackgroundColor(Color.rgb(144,216,135));
        boton.setTextSize(2,22);

        //Define las márgenes de los BotonesDossier
        final ViewGroup.MarginLayoutParams lpt =(ViewGroup.MarginLayoutParams)boton.getLayoutParams();
        //izq,arriba,der,fondo
        lpt.setMargins(200,10,200,lpt.bottomMargin);

        //Se coloca texto e Id al boton
        boton.setText(actAprend.getNombreact());
        boton.setId(actAprend.getIdactaprend());

        boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentVerPreguntas = new Intent(v.getContext(), VerPreguntas.class);
//                intentVerPreguntas.putExtra(Constantes.IDACTAPREND, actaprendMapa.get(v.getId()).getIdactaprend());
                intentVerPreguntas.putExtra(Constantes.IDACTAPREND, v.getId());
                intentVerPreguntas.putExtra(Constantes.IDDOSSIER, iddossier);
                intentVerPreguntas.putExtra(Constantes.NOMBREACT, actaprendMapa.get(v.getId()).getNombreact());

                startActivity(intentVerPreguntas);
            }
        });
    }
}
