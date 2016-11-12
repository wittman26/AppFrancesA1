package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.laudy.francesa1.app.appfrancesa1.DTO.Logros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class VerLogros extends AppCompatActivity implements View.OnClickListener, RespuestaAsincrona {

    //Declaro variables
    LinearLayout linLogros;
    Button btnVerDossiers;
    Button btnVerPerfil;
    Button btnSalirLogros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_logros);

        //Mapeo (Prepara btn para escuchar click)
        btnVerDossiers=(Button) findViewById(R.id.btnVerDossiers);
        btnVerPerfil=(Button) findViewById(R.id.btnVerPerfil);
        btnSalirLogros=(Button) findViewById(R.id.btnSalirLogros);
        linLogros = (LinearLayout)findViewById(R.id.linLogros);

        //(Escucha click)
        btnVerDossiers.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);
        btnSalirLogros.setOnClickListener(this);

        //Prepara tarea para traer datos de Logros
        try {
            TareaAsincrona tareaVerLogros = new TareaAsincrona(this);
            tareaVerLogros.delegar = this;

            //Prepara los parámetros de envío
            Map<String, String> parame = new TreeMap<>();
            parame.put("nombreusuario", Sesion.usuarioLogeado.getNombreUsuario());

            ParametrosURL params = new ParametrosURL(Constantes.LOGROS, parame);
            tareaVerLogros.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){
            //Evento btn Ver dossiers
            case R.id.btnVerDossiers:
                Intent intentVerDossiers = new Intent(this, VerDossiers.class);
                startActivity(intentVerDossiers);
                break;
            //Evento btn Ver perfil
            case R.id.btnVerPerfil:
                Intent intentVerPerfil = new Intent(this, VerPerfil.class);
                startActivity(intentVerPerfil);
                break;
            case R.id.btnSalirLogros:
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

                Logros logros = new Logros();
                //Leer array de JSON de acuerdo al título arrojado por el php
                JSONArray listaLogros = new JSONArray(objetoJSON.getString("logros"));

                for (int i=0; i < listaLogros.length(); i++) {
                    JSONObject objetoJSONLogros = listaLogros.getJSONObject(i);

                    logros.iniciarValores(objetoJSONLogros);

                    agregarVista(logros);
                }

            } else {
                Toast.makeText(this, "No hay datos para cargar", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void agregarVista(Logros logros){

        LinearLayout linListaLogros = new LinearLayout(this);
        linListaLogros.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        linListaLogros.setPadding(7,7,7,7); //izq,arr,der,aba
        linListaLogros.setOrientation(LinearLayout.VERTICAL);
        linListaLogros.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        ImageView imgDossier = new ImageView(this);
        imgDossier.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView lblDossier = new TextView(this);
        lblDossier.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView lblPuntajeAcum = new TextView(this);
        lblPuntajeAcum.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView lblPuntajeMax = new TextView(this);
        lblPuntajeMax.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //Se colocan propiedades de objetos adicionados
        imgDossier.setBackgroundResource(R.drawable.d0a1_1); //Imagen medalla

        lblDossier.setTextColor(Color.rgb(51,0,153));
        lblDossier.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);//Tipo de unidad y cantidad
        lblDossier.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        lblDossier.setPadding(10,10,10,10);
        final ViewGroup.MarginLayoutParams lpt =(ViewGroup.MarginLayoutParams)lblDossier.getLayoutParams();
        //izq,arriba,der,fondo
        lpt.setMargins(0,10,0,0);

        lblPuntajeAcum.setTextColor(Color.rgb(20, 155, 10));
        lblPuntajeAcum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);//Tipo de unidad y cantidad
        lblPuntajeAcum.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        final ViewGroup.MarginLayoutParams lpt2 =(ViewGroup.MarginLayoutParams)lblPuntajeAcum.getLayoutParams();
        //izq,arriba,der,fondo
        lpt2.setMargins(0,32,0,0);

        lblPuntajeMax.setTextColor(Color.rgb(20, 155, 10));
        lblPuntajeMax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);//Tipo de unidad y cantidad
        lblPuntajeMax.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        lblDossier.setText(logros.getNombredossier());
        lblPuntajeAcum.setText("Acumulado: " + logros.getPuntajeacumulado());
        lblPuntajeMax.setText("Máximo: " + logros.getPuntajemaximo());


        //Se agregan elementos en orden inverso a como aparecerán en pantalla
        linListaLogros.addView(lblPuntajeMax,0);
        linListaLogros.addView(lblPuntajeAcum,0);
        linListaLogros.addView(lblDossier,0);
        linListaLogros.addView(imgDossier,0);

        linLogros.addView(linListaLogros);
    }
}