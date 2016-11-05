package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class Ingresar extends AppCompatActivity implements OnClickListener, RespuestaAsincrona {

    //Declaro variables
    Button btnIngresar;
    EditText txtUsuario;
    EditText txtContrasena;
    TextView lblTituloMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        //Mapeo (Prepara btn para escuchar click)
        btnIngresar=(Button) findViewById(R.id.btnIngresar);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        lblTituloMain = (TextView) findViewById(R.id.lblTituloMain);

        //(Escucha click)
        btnIngresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Eventos de los btn
        switch (v.getId()){

            //Evento btn Registrar
            case R.id.btnIngresar:
                if(validarCampos()) {
                    try {
                        TareaAsincrona tarea = new TareaAsincrona(this);
                        tarea.delegar = this;

                        //Prepara los parámetros de envío
                        Map<String, String> parame = new TreeMap<>();
                        parame.put("usuario", txtUsuario.getText().toString());
                        parame.put("password", txtContrasena.getText().toString());

                        ParametrosURL params = new ParametrosURL(Constantes.LOGIN, parame);
                        tarea.execute(params);
                    } catch (Exception e) {
                        Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Ingrese usuario y contraseña", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private boolean validarCampos(){
        boolean resultado = true;
        if(txtUsuario.getText().length()==0) {txtUsuario.setError("Ingrese su usuario");resultado=false;}
        if(txtContrasena.getText().length()==0) {txtContrasena.setError("Ingrese la contraseña");resultado=false;}

        return resultado;
    }

    @Override
    public void traerResultados(String resultado) {
        try {
            JSONObject objetoJSON = new JSONObject(resultado);
            String exito= objetoJSON.getString("success");

            //Si se ha logeado correctamente
            if(exito.equals("1")){

                //Leer array de JSON
                JSONArray listaUsuario = new JSONArray(objetoJSON.getString("usuario"));

                for (int i=0; i < listaUsuario.length(); i++) {
                    JSONObject objetoJSONusuario = listaUsuario.getJSONObject(i);
                    Sesion.usuarioLogeado.iniciarValores(objetoJSONusuario);
                }

                Intent intentPrincipalMenu = new Intent(this, PrincipalMenu.class);
                startActivity(intentPrincipalMenu);

            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
