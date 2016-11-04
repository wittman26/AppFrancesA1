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

import com.laudy.francesa1.app.appfrancesa1.DTO.ActAprend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class VerActAprend extends AppCompatActivity implements View.OnClickListener, respuestaAsincrona {

    //Declaro variables
    ////////////////Button btnSalir;
    LinearLayout linBotonesActivi;
    Button btnVerLogros;
    Button btnVerDossiers;
    Button btnVerPerfil;

    private String iddossier ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_act_aprend);

        //Mapeo (Prepara btn para escuchar click)
        btnVerLogros=(Button) findViewById(R.id.btnVerLogros);
        btnVerDossiers=(Button) findViewById(R.id.btnVerDossiers);
        btnVerPerfil=(Button) findViewById(R.id.btnVerPerfil);
        linBotonesActivi = (LinearLayout)findViewById(R.id.linBotonesActivi);

        //(Escucha click)
        btnVerLogros.setOnClickListener(this);
        btnVerDossiers.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado
//        String iddossier ="";

        if(extras != null){
            iddossier = String.valueOf(extras.getInt(constantes.IDDOSSIER));
//            Toast.makeText(getApplicationContext(), extras.getString(constantes.IDDOSSIER), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Recibió: " + extras.getString("prueba"), Toast.LENGTH_LONG).show();
        }

        //Obtiene datos de la URL
        try {
            tareaAsincrona tareaActAprend = new tareaAsincrona(this);
            tareaActAprend.delegar = this;

            //Prepara los parámetros de envío
            Map<String, String> parame = new TreeMap<>();
            parame.put("iddossier", iddossier);

            parametrosURL params = new parametrosURL(constantes.ACTAPREND, parame);
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
            case R.id.btnAct1:
                Intent intentActiv1 = new Intent(this, VerPreguntas.class);
                startActivity(intentActiv1);
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

                    actAprend.setIdactaprend(Integer.parseInt(objetoJSONActAprend.getString("idactaprend")));
                    actAprend.setNombreact(objetoJSONActAprend.getString("nombreact"));
                    actAprend.setTipoactaprend(objetoJSONActAprend.getString("tipoactaprend"));

                    agregarBotonesActivi(actAprend);
                }

            } else {
                Toast.makeText(this, "Ocurrió un error al obtener datos", Toast.LENGTH_LONG).show();
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
//        boton.setText(actAprend.getNombreact());
        linBotonesActivi.addView(boton);

        boton.setTextColor(Color.parseColor("#330099"));

        //También se puede Color.WHITE
        boton.setTextColor(Color.rgb(51,0,153));
        boton.setBackgroundColor(Color.rgb(144,216,135));
        boton.setTextSize(2,22);

        //Define las márgenes de los BotonesDossier
        final ViewGroup.MarginLayoutParams lpt =(ViewGroup.MarginLayoutParams)boton.getLayoutParams();
        //izq,arriba,der,fondo
        lpt.setMargins(200,10,200,lpt.bottomMargin);

        boton.setText(actAprend.getNombreact());
        boton.setId(actAprend.getIdactaprend());

        boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Toast.makeText(getApplicationContext(), " Listener botón " + v.getId(), Toast.LENGTH_SHORT).show();
                Intent intentVerPreguntas = new Intent(v.getContext(), VerPreguntas.class);
                intentVerPreguntas.putExtra(constantes.IDACTAPREND, v.getId());
                intentVerPreguntas.putExtra(constantes.IDDOSSIER, iddossier);
//                intentVerPreguntas.putExtra(constantes.USUARIO, usuarioLogeado.getNombreUsuario());
                startActivity(intentVerPreguntas);
            }
        });
    }
}
