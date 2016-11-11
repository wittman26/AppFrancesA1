package com.laudy.francesa1.app.appfrancesa1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class VerPerfil extends AppCompatActivity implements View.OnClickListener, RespuestaAsincrona {

    //Declaro variables
    TextView lblNombreUsuario;
    TextView lblEmail;
    Button btnModificarPerfil;
    Button btnEliminarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        //Mapeo (Prepara btn para escuchar click)
        btnModificarPerfil=(Button) findViewById(R.id.btnModificarPerfil);
        btnEliminarPerfil=(Button) findViewById(R.id.btnEliminarPerfil);
        lblNombreUsuario = (TextView)findViewById(R.id.lblNombreUsuario);
        lblEmail = (TextView)findViewById(R.id.lblEmail);

        //(Escucha click)
        btnModificarPerfil.setOnClickListener(this);
        btnEliminarPerfil.setOnClickListener(this);

        if(Sesion.usuarioLogeado != null){
            lblNombreUsuario.setText(Sesion.usuarioLogeado.getNombreUsuario());
            lblEmail.setText(Sesion.usuarioLogeado.getEmail());
        }
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){

            //Evento btn Ver perfil
            case R.id.btnModificarPerfil:
                Intent intentRegistrar = new Intent(this, Registrar.class);
                startActivity(intentRegistrar);
                break;
            //Evento btn Ver perfil
            case R.id.btnEliminarPerfil:
                mostrarDialogo();
                break;
        }
    }

    void mostrarDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea borrar el perfil?")
                .setTitle("Confirma eliminación")
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
                                eliminarPerfil(); // metodo que se debe implementar
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    void eliminarPerfil(){

        try {
            TareaAsincrona tareaEliminar = new TareaAsincrona(this);
            tareaEliminar.delegar = this;

            //Prepara los parámetros de envío
            Map<String,String> parame = new TreeMap<>();
            parame.put("nombreusuario",Sesion.usuarioLogeado.getNombreUsuario());

            ParametrosURL params = new ParametrosURL(Constantes.ELIMINAR_PERFIL,parame);
            tareaEliminar.execute(params);
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

                Intent intentSalir = new Intent(this, Ingresar.class);
                intentSalir.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSalir);
                Toast.makeText(this, "Se ha Eliminado el perfil", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Error al eliminar Perfil: " + objetoJSON.getString("message"), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}