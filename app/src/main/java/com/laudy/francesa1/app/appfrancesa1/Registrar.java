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

public class Registrar extends AppCompatActivity implements OnClickListener,RespuestaAsincrona {

    //Declaro variables
    Button btnIniciarSesion;
    Button btnRegistrar;
    EditText txtNombreUsuario;
    EditText txtEmail;
    EditText txtContrasena;
    EditText txtConfirmarContrasena;
    private String urlUsuario; //Guarda url para crear o actualizar usuario

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

        txtNombreUsuario.setEnabled(true);

        urlUsuario = Constantes.NUEVO_USUARIO;

        //Si está logeado el usuario, va a modificar los datos
        if(Sesion.usuarioLogeado != null){
            urlUsuario = Constantes.ACTUALIZAR_USUARIO;
            txtNombreUsuario.setEnabled(false);
            txtNombreUsuario.setText(Sesion.usuarioLogeado.getNombreUsuario());
            txtEmail.setText(Sesion.usuarioLogeado.getEmail());
            txtContrasena.setText(Sesion.usuarioLogeado.getContrasena());
            txtConfirmarContrasena.setText(Sesion.usuarioLogeado.getContrasena());
        }
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
                        TareaAsincrona tarea =new TareaAsincrona(this);
                        tarea.delegar = this;

                        //Prepara los parámetros de envío
                        Map<String,String> parame = new TreeMap<>();
                        parame.put("nombreusuario",txtNombreUsuario.getText().toString());
                        parame.put("email",txtEmail.getText().toString());
                        parame.put("contrasena",txtContrasena.getText().toString());

                        ParametrosURL params = new ParametrosURL(urlUsuario,parame);
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
                //Si es actualización, refresca el objeto de sesión
                if(Sesion.usuarioLogeado != null){
                    Sesion.usuarioLogeado.setNombreUsuario(txtNombreUsuario.getText().toString());
                    Sesion.usuarioLogeado.setEmail(txtEmail.getText().toString());
                    Sesion.usuarioLogeado.setContrasena(txtContrasena.getText().toString());
                    Toast.makeText(this, "Su perfil se ha modificado exitosamente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_LONG).show();
                    Intent intentIngresar = new Intent(this, Ingresar.class);
                    startActivity(intentIngresar);
                }

                finish();
            } else {
                Toast.makeText(this, objetoJSON.getString("message"), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}