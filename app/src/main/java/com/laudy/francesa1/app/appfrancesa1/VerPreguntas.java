package com.laudy.francesa1.app.appfrancesa1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class VerPreguntas extends AppCompatActivity  implements View.OnClickListener, RespuestaAsincronaImagen,RespuestaAsincrona {


    private ImageView imgImagen;
    private TextView lblPreguntaTexto;
    private Button btnAudio;

    private Button btnVerLogrosPreg;
    private Button btnVerDossiers;
    private Button btnVerPerfil;
    private Button btnRespuesta1;
    private Button btnRespuesta2;
    private Button btnRespuesta3;
    private Button btnRespuesta4;
    private TextView lblTituloActAprend;
    private TextView lblTituloDossier;
    private TextView lblInfo;
    private ImageButton imgSiguiente;
    private Button btnSalirPreg;

    private List<Pregunta> preguntasActuales = new ArrayList<Pregunta>();
    private Pregunta preguntaActual = new Pregunta();
    private int correcta = 0;
    int cont = 0; //Contador de preguntas
    int puntajeTotal = 0;
    int puntajeMaximo = 0;
    private String iddossier="";
    private String idactaprend ="";
    private String urlAudio = "";
    MediaPlayer mpAudio = null; //variable para reproducir audios

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_preguntas);

        //Mapeo (Prepara btn para escuchar click)
        imgImagen=(ImageView) findViewById(R.id.imgImagen);
        lblPreguntaTexto=(TextView) findViewById(R.id.lblPreguntaTexto);
        btnAudio=(Button) findViewById(R.id.btnAudio);
        btnVerLogrosPreg =(Button) findViewById(R.id.btnVerLogrosPreg);
        btnVerDossiers =(Button) findViewById(R.id.btnVerDossiers);
        btnVerPerfil =(Button) findViewById(R.id.btnVerPerfil);

        btnRespuesta1 =(Button) findViewById(R.id.btnRespuesta1);
        btnRespuesta2 =(Button) findViewById(R.id.btnRespuesta2);
        btnRespuesta3 =(Button) findViewById(R.id.btnRespuesta3);
        btnRespuesta4 =(Button) findViewById(R.id.btnRespuesta4);
        lblInfo = (TextView) findViewById(R.id.lblInfo);
        lblTituloActAprend = (TextView) findViewById(R.id.lblTituloActAprend);
        lblTituloDossier = (TextView) findViewById(R.id.lblTituloDossier);
        imgSiguiente = (ImageButton)findViewById(R.id.imgSiguiente);

        btnSalirPreg =(Button) findViewById(R.id.btnSalirPreg);

        //(Escucha click)
        btnAudio.setOnClickListener(this);
        btnVerLogrosPreg.setOnClickListener(this);
        btnVerDossiers.setOnClickListener(this);
        btnVerPerfil.setOnClickListener(this);
        btnRespuesta1.setOnClickListener(this);
        btnRespuesta2.setOnClickListener(this);
        btnRespuesta3.setOnClickListener(this);
        btnRespuesta4.setOnClickListener(this);
        imgSiguiente.setOnClickListener(this);
        btnSalirPreg.setOnClickListener(this);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado

        if(extras != null){
            idactaprend = String.valueOf(extras.getInt(Constantes.IDACTAPREND));
            iddossier = extras.getString(Constantes.IDDOSSIER);
            lblTituloActAprend.setText(extras.getString(Constantes.NOMBREACT));
            lblTituloDossier.setText(extras.getString(Constantes.NOMBREDOSSIER));
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

    /*@Override
    public void onDestroy(){
        if(mpAudio!=null) {
            mpAudio.release();
            mpAudio = null;
        }
    }*/

    //Muestra ventana de acuerdo al boton elegido
    void mostrarPantalla(int id){
        switch (id) {
            //Evento btn Ver Logros
            case R.id.btnVerLogrosPreg:
                Intent intentVerLogros = new Intent(this, VerLogros.class);
                startActivity(intentVerLogros);
                finish();
                break;
            //Evento btn Ver dossiers
            case R.id.btnVerDossiers:
                Intent intentVerDossiers = new Intent(this, VerDossiers.class);
                startActivity(intentVerDossiers);
                finish();
                break;
            //Evento btn Ver perfil
            case R.id.btnVerPerfil:
                Intent intentVerPerfil = new Intent(this, VerPerfil.class);
                startActivity(intentVerPerfil);
                finish();
                break;
        }
    }

    //Antes de salir, pregunta confirmación
    void mostrarDialogo(final int idBtn){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Perderá todos los avances realizados hasta ahora ¿Seguro que desea salir?")
                .setTitle("Confirma salir")
                .setCancelable(false)
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Confirmar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mostrarPantalla(idBtn); // metodo que se debe implementar
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()) {
            //Evento btn Ver Logros
            case R.id.btnVerLogrosPreg:
                mostrarDialogo(v.getId());
                break;
            //Evento btn Ver dossiers
            case R.id.btnVerDossiers:
                mostrarDialogo(v.getId());
                break;
            //Evento btn Ver perfil
            case R.id.btnVerPerfil:
                mostrarDialogo(v.getId());
                break;
            //Evento btn Salir
            case R.id.btnSalirPreg:
                Sesion.usuarioLogeado = null;
                Intent intentSalir = new Intent(this, Ingresar.class);
                intentSalir.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSalir);
                break;
            //Evento btn Reproducir audio
            case R.id.btnAudio:
                mpAudio =
                        MediaPlayer.create(getApplicationContext(), Uri.parse(urlAudio));
                mpAudio.start();
                break;
            //Verifica si se oprimió Siguiente
            case R.id.imgSiguiente:
                if (cont < preguntasActuales.size() - 1) {
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
                break;
            default:
                //Botones auxiliares para retroalimentación
                Button btnResElegida;
                Button btnResCorrecta;

                btnResElegida = (Button) findViewById(v.getId());
                btnResCorrecta = (Button) findViewById(correcta);

                puntajeMaximo = puntajeMaximo + preguntaActual.getPuntaje();

                if (correcta == v.getId()) {
                    lblInfo.setText(Constantes.BIEN);
                    lblInfo.setTextColor(Color.GREEN);
                    btnResElegida.setBackgroundColor(Color.GREEN);
                    puntajeTotal = puntajeTotal + preguntaActual.getPuntaje();
                } else {
                    lblInfo.setText(Constantes.MAL);
                    lblInfo.setTextColor(Color.RED);
                    btnResElegida.setBackgroundColor(Color.RED);
                    btnResCorrecta.setBackgroundColor(Color.GREEN);
                }
                //Solo se habilita el botón siguiente si se ha respondido la pergunta
                habilitarBotones(false);

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

        //Verifica que tipo de pregunta es
        switch(preguntaActual.getTipopregunta().toUpperCase()){
            case Constantes.IMAGEN:
                lblPreguntaTexto.setVisibility(View.GONE);
                btnAudio.setVisibility(View.GONE);
                imgImagen.setVisibility(View.VISIBLE);
                cargarImagen(preguntaActual.getDescripcionp());
                break;
            case Constantes.AUDIO:
                lblPreguntaTexto.setVisibility(View.GONE);
                btnAudio.setVisibility(View.VISIBLE);
                imgImagen.setVisibility(View.GONE);
                urlAudio = Constantes.AUDIOS + preguntaActual.getDescripcionp();
                break;
            case Constantes.TEXTO:
                lblPreguntaTexto.setVisibility(View.VISIBLE);
                btnAudio.setVisibility(View.GONE);
                imgImagen.setVisibility(View.GONE);
                lblPreguntaTexto.setText(preguntaActual.getDescripcionp());
                break;
        }


        definirVista();
    }

    public void cargarImagen(String nombreArchivo){
        try {
            TareaAsincronaImagen tareaImagen = new TareaAsincronaImagen(this);
            tareaImagen.delegar = this;

            ParametrosURL params = new ParametrosURL(Constantes.IMAGENES + nombreArchivo);
            tareaImagen.execute(params);
        } catch (Exception e) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void traerResultadosImagen(Bitmap resultado) {
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
