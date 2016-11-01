package com.laudy.francesa1.app.appfrancesa1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class Registrar extends AppCompatActivity implements OnClickListener,respuestaAsincrona {

    //Declaro variables
    Button btnIniciarSesion;
    Button btnRegistrar;
    EditText txtNombreUsuario;
    EditText txtEmail;
    EditText txtContrasena;
    EditText txtConfirmarContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Mapeo (Prepara btn para escuchar click)
        btnIniciarSesion=(Button) findViewById(R.id.btnIniciarSesionReg);
        btnRegistrar=(Button) findViewById(R.id.btnRegistrarReg);
        txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContrasena = (EditText) findViewById(R.id.txtContrasenaReg);
        txtConfirmarContrasena = (EditText) findViewById(R.id.txtConfirmarContrasena);

        //(Escucha click)
        btnIniciarSesion.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Evento btn Atras
            case R.id.btnIniciarSesionReg:
                Intent intentIngresar = new Intent(this, Ingresar.class);
                startActivity(intentIngresar);
                break;
            case R.id.btnRegistrarReg:
                if(validarCampos()){
                    try {
                        tareaAsincrona tarea =new tareaAsincrona(this);
                        tarea.delegar = this;

                        //Prepara los parámetros de envío
                        Map<String,String> parame = new TreeMap<>();
                        parame.put("nombreusuario",txtNombreUsuario.getText().toString());
                        parame.put("email",txtEmail.getText().toString());
                        parame.put("contrasena",txtContrasena.getText().toString());

                        parametrosURL params = new parametrosURL(constantes.NUEVO_USUARIO,parame);
                        tarea.execute(params);
                    } catch (Exception e) {
                        Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Verifique los campos", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
    private boolean validarCampos(){
        boolean resultado = true;
        if(txtNombreUsuario.getText().length()==0) {txtNombreUsuario.setError("Ingrese nombre de usuario");resultado=false;}
        if(txtEmail.getText().length()==0) {txtEmail.setError("Ingrese email");resultado=false;}
        if(txtContrasena.getText().length()==0) {txtContrasena.setError("Ingrese nombre la contraseña");resultado=false;}
        if(txtConfirmarContrasena.getText().length()==0) {txtConfirmarContrasena.setError("Confirme la contraseña");resultado=false;}

        if(!txtContrasena.getText().toString().equals(txtConfirmarContrasena.getText().toString())){
            txtConfirmarContrasena.setError("Las contraseñas no coinciden");
            resultado=false;
        }
        return resultado;
    }

    @Override
    public void traerResultados(String resultado) {
        try {
            JSONObject objetoJSON = new JSONObject(resultado);
            String exito= objetoJSON.getString("success");

            //Si se ha logueado correctamente
            if(exito.equals("1")){
                Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_LONG).show();
                Intent intentIngresar = new Intent(this, Ingresar.class);
                startActivity(intentIngresar);
                finish();
            } else {
                Toast.makeText(this, objetoJSON.getString("message"), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}