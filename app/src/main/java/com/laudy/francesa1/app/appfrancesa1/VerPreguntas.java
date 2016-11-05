package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class VerPreguntas extends AppCompatActivity  implements View.OnClickListener, RespuestaAsincronaArchivos,RespuestaAsincrona {


    private ImageView imgImagen;
    private Button btnRespuesta1;
    private Button btnRespuesta2;
    private Button btnRespuesta3;
    private Button btnRespuesta4;
    private TextView lblInfo;
    private ImageButton imgSiguiente;

    private List<Pregunta> preguntasActuales = new ArrayList<Pregunta>();
    private Pregunta preguntaActual = new Pregunta();
    private int correcta = 0;
    int cont = 0; //Contador de preguntas
    int puntajeTotal = 0;
    int puntajeMaximo = 0;
    private String iddossier="";
    private String idactaprend ="";

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
        imgSiguiente = (ImageButton)findViewById(R.id.imgSiguiente);

        //(Escucha click)
        btnRespuesta1.setOnClickListener(this);
        btnRespuesta2.setOnClickListener(this);
        btnRespuesta3.setOnClickListener(this);
        btnRespuesta4.setOnClickListener(this);
        imgSiguiente.setOnClickListener(this);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado

        if(extras != null){
            idactaprend = String.valueOf(extras.getInt(Constantes.IDACTAPREND));
            iddossier = extras.getString(Constantes.IDDOSSIER);
        }

        //Prepara tarea para traer datos de Preguntas
        try {
            TareaAsincrona tareaPreguntas = new TareaAsincrona(this);
            tareaPreguntas.delegar = this;

            //Prepara los parámetros de envío
            Map<String, String> parame = new TreeMap<>();
            parame.put("idactaprend", idactaprend);

            ParametrosURL params = new ParametrosURL(Constantes.PREGUNTAS, parame);
            tareaPreguntas.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        Button aux;
        Button aux2;
        if(v.getId()==R.id.imgSiguiente){
            if(cont < preguntasActuales.size()-1) {
                cargarPregunta(++cont);
            } else {
                Intent intentVerResultados = new Intent(v.getContext(), VerResultados.class);
                intentVerResultados.putExtra(Constantes.IDACTAPREND, idactaprend);
                intentVerResultados.putExtra(Constantes.IDDOSSIER, iddossier);
                intentVerResultados.putExtra(Constantes.PUNTAJETOTAL, puntajeTotal);
                intentVerResultados.putExtra(Constantes.PUNTAJEMAXIMO, puntajeMaximo);

                startActivity(intentVerResultados);
                finish();
            }
        } else {
            aux = (Button) findViewById(v.getId());
            aux2 = (Button) findViewById(correcta);
            puntajeMaximo = puntajeMaximo + preguntaActual.getPuntaje();
            if (correcta == v.getId()) {
                lblInfo.setText(Constantes.BIEN);
                lblInfo.setTextColor(Color.GREEN);
                aux.setBackgroundColor(Color.GREEN);
                puntajeTotal = puntajeTotal + preguntaActual.getPuntaje();
            } else {
                lblInfo.setText(Constantes.MAL);
                lblInfo.setTextColor(Color.RED);
                aux.setBackgroundColor(Color.RED);
                aux2.setBackgroundColor(Color.GREEN);
            }
            habilitarBotones(false);
        }
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

                    pregunta.iniciarValores(objetoJSONPreguntas);

                    //Leer array de JSON de respuestas de acuerdo al título arrojado (respuestas)
                    JSONArray listaRespuestas = new JSONArray(objetoJSONPreguntas.getString("respuestas"));
                    for (int j=0;j < listaRespuestas.length(); j++){
                        Respuesta respuesta = new Respuesta ();
                        JSONObject objetoJSONRespuestas = listaRespuestas.getJSONObject(j);

                        respuesta.iniciarValores(objetoJSONRespuestas);

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

                cargarPregunta(cont);

            } else {
                Toast.makeText(this, "Ocurrió un error al obtener datos", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cargarPregunta(int numPreg){
        preguntaActual = preguntasActuales.get(numPreg);
        cargarImagen(preguntaActual.getDescripcionp());
        definirVista();
    }

    public void cargarImagen(String nombreArchivo){
        try {
            TareaAsincronaArchivos tareaImagen = new TareaAsincronaArchivos(this);
            tareaImagen.delegar = this;

            ParametrosURL params = new ParametrosURL(Constantes.IMAGENES + nombreArchivo);
            tareaImagen.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void traerResultadosArchivos(Bitmap resultado) {
        imgImagen.setImageBitmap(resultado);
    }

    public void definirVista(){
        lblInfo.setText("");
        Respuesta respuestaAuxiliar = new Respuesta();

        respuestaAuxiliar = preguntaActual.getRespuestas().get(0);
        btnRespuesta1.setText(respuestaAuxiliar.getDescripcionr());
//        btnRespuesta1.setBackgroundColor(Color.rgb(51, 0, 153));
        btnRespuesta1.setBackgroundColor(Color.CYAN);

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta1.getId();
        }

        respuestaAuxiliar = preguntaActual.getRespuestas().get(1);
        btnRespuesta2.setText(respuestaAuxiliar.getDescripcionr());
//        btnRespuesta2.setBackgroundColor(Color.rgb(51, 0, 153));
        btnRespuesta2.setBackgroundColor(Color.CYAN);

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta2.getId();
        }

        respuestaAuxiliar = preguntaActual.getRespuestas().get(2);
        btnRespuesta3.setText(respuestaAuxiliar.getDescripcionr());
//        btnRespuesta3.setBackgroundColor(Color.rgb(51, 0, 153));
        btnRespuesta3.setBackgroundColor(Color.CYAN);

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta3.getId();
        }

        respuestaAuxiliar = preguntaActual.getRespuestas().get(3);
        btnRespuesta4.setText(respuestaAuxiliar.getDescripcionr());
//        btnRespuesta4.setBackgroundColor(Color.rgb(51, 0, 153));
        btnRespuesta4.setBackgroundColor(Color.CYAN);

        if(respuestaAuxiliar.getRcorrecta().toUpperCase().equals("S")){
            correcta = btnRespuesta4.getId();
        }
        habilitarBotones(true);
    }

    public void habilitarBotones(Boolean habilita){
        btnRespuesta1.setEnabled(habilita);
        btnRespuesta2.setEnabled(habilita);
        btnRespuesta3.setEnabled(habilita);
        btnRespuesta4.setEnabled(habilita);
        imgSiguiente.setEnabled(!habilita);
    }

}
