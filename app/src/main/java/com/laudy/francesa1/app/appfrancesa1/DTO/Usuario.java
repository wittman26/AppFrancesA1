package com.laudy.francesa1.app.appfrancesa1.DTO;

/**
 * Created by Laudy on 29/10/2016.
 */

public class Usuario {
    private String nombreUsuario;
    private String email;

    public Usuario(String nombreUsuario, String email) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
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
}
