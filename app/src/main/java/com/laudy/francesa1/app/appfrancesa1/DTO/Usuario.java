package com.laudy.francesa1.app.appfrancesa1.DTO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Laudy on 29/10/2016.
 */

public class Usuario {
    private String nombreUsuario;
    private String email;
    private String contrasena;

    // Nombre de campos en Base de datos entregados por PHP
    public static final String NOMBREUSUARIO = "nombreusuario";
    public static final String EMAIL = "email";
    public static final String CONTRASENA = "contrasena";

    public Usuario(String nombreUsuario, String email, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Usuario() {
        this.nombreUsuario = "";
        this.email = "";
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void iniciarValores(JSONObject objetoJSONusuario){
        try {
            setNombreUsuario(objetoJSONusuario.getString(Usuario.NOMBREUSUARIO));
            setEmail(objetoJSONusuario.getString(Usuario.EMAIL));
            setContrasena(objetoJSONusuario.getString(Usuario.CONTRASENA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
