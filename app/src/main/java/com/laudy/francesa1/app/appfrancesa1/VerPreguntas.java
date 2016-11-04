package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.laudy.francesa1.app.appfrancesa1.DTO.ActAprend;
import com.laudy.francesa1.app.appfrancesa1.DTO.Pregunta;
import com.laudy.francesa1.app.appfrancesa1.DTO.Respuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VerPreguntas extends AppCompatActivity  implements View.OnClickListener, respuestaAsincronaArchivos,respuestaAsincrona{


    private ImageView imgImagen;
    private Button btnRespuesta1;
    private Button btnRespuesta2;
    private Button btnRespuesta3;
    private Button btnRespuesta4;
    private TextView lblInfo;

    private Bitmap loadedImage;
    private String iddossier ="";
    private String idactaprend ="";
    private List<Pregunta> preguntasActuales = new ArrayList<Pregunta>();
    private Pregunta preguntaActual = new Pregunta();
    private int correcta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_preguntas);

        //Mapeo (Prepara btn para escuchar click)
        imgImagen=(ImageView) findViewById(R.id.imgImagen);
        btnRespuesta1 =(Button) findViewById(R.id.btnRespuesta1);
        btnRespuesta2 =(Button) findViewById(R.id.btnRespuesta2);
        btnRespuesta3 =(Button) findViewById(R.id.btnRespuesta3);
        btnRespuesta4 =(Button) findViewById(R.id.btnRespuesta4);
        lblInfo = (TextView) findViewById(R.id.lblInfo);

        btnRespuesta1.setOnClickListener(this);
        btnRespuesta2.setOnClickListener(this);
        btnRespuesta3.setOnClickListener(this);
        btnRespuesta4.setOnClickListener(this);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado

        if(extras != null){
//            iddossier = String.valueOf(extras.getInt(constantes.IDDOSSIER));
            idactaprend = String.valueOf(extras.getInt(constantes.IDACTAPREND));
//            Toast.makeText(getApplicationContext(), extras.getString(constantes.IDDOSSIER), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Id Actividad: " + idactaprend, Toast.LENGTH_LONG).show();
        }

        //Obtiene datos de la URL
        try {
            tareaAsincrona tareaPreguntas = new tareaAsincrona(this);
            tareaPreguntas.delegar = this;

            //Prepara los parámetros de envío
            Map<String, String> parame = new TreeMap<>();
//            parame.put("iddossier", iddossier);
            parame.put("idactaprend", idactaprend);

            parametrosURL params = new parametrosURL(constantes.PREGUNTAS, parame);
            tareaPreguntas.execute(params);

        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
    }

    public void definirVista(){
        Respuesta respuestaAuxiliar = new Respuesta();

        respuestaAuxiliar = preguntaActual.getRespuestas().get(0);
        btnRespuesta1.setText(respuestaAuxiliar.getDescripcionr());

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta1.getId();
        }

        respuestaAuxiliar = preguntaActual.getRespuestas().get(1);
        btnRespuesta2.setText(respuestaAuxiliar.getDescripcionr());

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta2.getId();
        }

        respuestaAuxiliar = preguntaActual.getRespuestas().get(2);
        btnRespuesta3.setText(respuestaAuxiliar.getDescripcionr());

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta3.getId();
        }

        respuestaAuxiliar = preguntaActual.getRespuestas().get(3);
        btnRespuesta4.setText(respuestaAuxiliar.getDescripcionr());

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta4.getId();
        }
    }

    @Override
    public void onClick(View v) {
        if(correcta==v.getId()){
//            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
            lblInfo.setText("Muy Bieeeeen!");
            lblInfo.setTextColor(Color.GREEN);
        } else {
            lblInfo.setText("Muy mal!");
            lblInfo.setTextColor(Color.RED);
//            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void traerResultadosArchivos(Bitmap resultado) {
        imgImagen.setImageBitmap(resultado);
    }

    @Override
    public void traerResultados(String resultado) {
        try {
            JSONObject objetoJSON = new JSONObject(resultado);
            String exito = objetoJSON.getString("success");

            //Si se ha traido datos
            if(exito.equals("1")){

                //Leer array de JSON de acuerdo al título arrojado por el php (preguntas)
                JSONArray listaPreguntas = new JSONArray(objetoJSON.getString("preguntas"));

                for (int i=0; i < listaPreguntas.length(); i++) {
                    Pregunta pregunta = new Pregunta();
                    List<Respuesta> listaResp = new ArrayList<Respuesta>();;

                    JSONObject objetoJSONPreguntas = listaPreguntas.getJSONObject(i);

                    pregunta.setIdpregunta(Integer.parseInt(objetoJSONPreguntas.getString("idpregunta")));
                    pregunta.setDescripcionp(objetoJSONPreguntas.getString("descripcionp"));
                    pregunta.setPuntaje(Integer.parseInt(objetoJSONPreguntas.getString("puntaje")));
                    pregunta.setIdactaprend(Integer.parseInt(objetoJSONPreguntas.getString("idactaprend")));

                    //Leer array de JSON de respuestas de acuerdo al título arrojado (respuestas)
                    JSONArray listaRespuestas = new JSONArray(objetoJSONPreguntas.getString("respuestas"));
                    for (int j=0;j < listaRespuestas.length(); j++){
                        Respuesta respuesta = new Respuesta ();
                        JSONObject objetoJSONRespuestas = listaRespuestas.getJSONObject(j);

                        respuesta.setIdrespuesta(Integer.parseInt(objetoJSONRespuestas.getString("idrespuesta")));
                        respuesta.setDescripcionr(objetoJSONRespuestas.getString("descripcionr"));
                        respuesta.setRcorrecta(objetoJSONRespuestas.getString("rcorrecta"));

                        //Adiciona a listado de respuestas
                        listaResp.add(respuesta);
                    }

                    if(!listaResp.isEmpty()) {
                        pregunta.setRespuestas(listaResp);
                    }else{
                        pregunta.setRespuestas(null);
                    }

                    //Adiciona la pregunta con sus respuestas a la lista
                    preguntasActuales.add(pregunta);
                }

                //Hay colocarla global e incrementarla en botón siguiente
                int cont = 0;

                preguntaActual = preguntasActuales.get(cont);

                cargarImagen(preguntaActual.getDescripcionp());
                definirVista();

            } else {
                Toast.makeText(this, "Ocurrió un error al obtener datos", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cargarImagen(String nombreArchivo){
        try {
            tareaAsincronaArchivos tareaImagen = new tareaAsincronaArchivos(this);
            tareaImagen.delegar = this;

            parametrosURL params = new parametrosURL(constantes.IMAGENES + nombreArchivo);
            tareaImagen.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
    }

}
