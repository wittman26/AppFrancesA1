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

import com.laudy.francesa1.app.appfrancesa1.DTO.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class Ingresar extends AppCompatActivity implements OnClickListener, respuestaAsincrona {

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
                try {
                    tareaAsincrona tarea =new tareaAsincrona(this);
                    tarea.delegar = this;

                    //Prepara los parámetros de envío
                    Map<String,String> parame = new TreeMap<>();
                    parame.put("usuario",txtUsuario.getText().toString());
                    parame.put("password",txtContrasena.getText().toString());

                    parametrosURL params = new parametrosURL(constantes.LOGIN,parame);
                    tarea.execute(params);
                } catch (Exception e) {
                    Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    @Override
    public void traerResultados(String resultado) {
        try {
            JSONObject objetoJSON = new JSONObject(resultado);
            String exito= objetoJSON.getString("success");

            //Si se ha logeado correctamente
            if(exito.equals("1")){

                Usuario usuarioLogeado = new Usuario();
                //Leer array de JSON
                JSONArray listaUsuario = new JSONArray(objetoJSON.getString("usuario"));

                for (int i=0; i < listaUsuario.length(); i++) {
                    JSONObject objetoJSONusuario = listaUsuario.getJSONObject(i);
                    usuarioLogeado.setNombreUsuario(objetoJSONusuario.getString("nombreusuario"));
                    usuarioLogeado.setEmail(objetoJSONusuario.getString("email"));
                }

                //Toast.makeText(this, "Bienvenido " + usuarioLogeado.getNombreUsuario(), Toast.LENGTH_LONG).show();
                Intent intentPrincipalMenu = new Intent(this, PrincipalMenu.class);
                intentPrincipalMenu.putExtra(constantes.USUARIO, usuarioLogeado.getNombreUsuario());
                startActivity(intentPrincipalMenu);

            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
