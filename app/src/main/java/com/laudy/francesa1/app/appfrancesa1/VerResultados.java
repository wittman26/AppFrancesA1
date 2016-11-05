package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.laudy.francesa1.app.appfrancesa1.DTO.Pregunta;
import com.laudy.francesa1.app.appfrancesa1.DTO.Respuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VerResultados extends AppCompatActivity implements View.OnClickListener,RespuestaAsincrona{

    TextView lblResultados;
    TextView lblPuntajeTotal;
    TextView lblMotiva;
    Button btnVerDossiersResu;
    Button btnVerActAprendResu;
    RatingBar rabResultado;

    private String iddossier ="";
    private String idactaprend ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resultados);

        //Mapeo (Prepara btn para escuchar click)
        lblResultados = (TextView) findViewById(R.id.lblResultados);
        lblPuntajeTotal = (TextView) findViewById(R.id.lblPuntajeTotal);
        lblMotiva = (TextView) findViewById(R.id.lblMotiva);
        btnVerDossiersResu = (Button)findViewById(R.id.btnVerDossiersResu);
        btnVerActAprendResu = (Button)findViewById(R.id.btnVerActAprendResu );
        rabResultado = (RatingBar)findViewById(R.id.rabResultado);

        btnVerDossiersResu.setOnClickListener(this);
        btnVerActAprendResu.setOnClickListener(this);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado
        int puntajeTotal=0;
        int puntajeMaximo=0;

        if(extras != null){
            idactaprend = extras.getString(Constantes.IDACTAPREND);
            iddossier = extras.getString(Constantes.IDDOSSIER);
            puntajeTotal = extras.getInt(Constantes.PUNTAJETOTAL);
            puntajeMaximo = extras.getInt(Constantes.PUNTAJEMAXIMO);
        }

        //Prepara tarea para guardar datos de Puntaje
        try {
            TareaAsincrona tareaGuardar = new TareaAsincrona(this);
            tareaGuardar.delegar = this;

            //Prepara los parámetros de envío
            Map<String, String> parame = new TreeMap<>();
            parame.put("nombreusuario", Sesion.usuarioLogeado.getNombreUsuario());
            parame.put("idactaprend", idactaprend);
            parame.put("puntajeacumulado", String.valueOf(puntajeTotal));

            ParametrosURL params = new ParametrosURL(Constantes.GUARDAR, parame);
            tareaGuardar.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }

        //Mostrar datos en pantalla
        float resultado = ((float)  puntajeTotal/ (float) puntajeMaximo ) * 6f;
        rabResultado.setRating(resultado);

        String usuario = Sesion.usuarioLogeado.getNombreUsuario();
        lblResultados.setText(usuario + ". Estos son tus resultados");
        lblPuntajeTotal.setText("Obtuviste " + puntajeTotal + "pts. de " + puntajeMaximo + " posibles");

        if(resultado<3f)
            lblMotiva.setText("Debes seguir repasando");
        else
            lblMotiva.setText("Bien hecho!");
        if(puntajeTotal==puntajeMaximo)
            lblMotiva.setText("Muy bien! sigue así");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerDossiersResu:
                Intent intentVerDossiers = new Intent(this, VerDossiers.class);
                startActivity(intentVerDossiers);
                finish();
                break;
            case R.id.btnVerActAprendResu:
                finish();
                break;
        }
    }

    @Override
    public void traerResultados(String resultado) {
        try {
            JSONObject objetoJSON = new JSONObject(resultado);
            String exito = objetoJSON.getString("success");
            String mensaje = objetoJSON.getString("message");

            //Si no ha traido datos
            if(!exito.equals("1")){
                Toast.makeText(this, "Error" + mensaje, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
