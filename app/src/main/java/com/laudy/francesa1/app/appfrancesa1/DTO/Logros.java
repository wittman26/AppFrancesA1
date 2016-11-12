package com.laudy.francesa1.app.appfrancesa1.DTO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Laudy on 08/11/2016.
 */

public class Logros {
    private int puntajeacumulado;
    private int iddossier;
    private String nombredossier;
    private int puntajemaximo;

    // Nombre de campos entregados por PHP
    public static final String PUNTAJEACUMULADO = "puntajeacumulado";
    public static final String IDDOSSIER = "iddossier";
    public static final String NOMBREDOSSIER = "nombredossier";
    public static final String PUNTAJEMAXIMO = "puntajemaximo";

    public int getPuntajeacumulado() {
        return puntajeacumulado;
    }

    public void setPuntajeacumulado(int puntajeacumulado) {
        this.puntajeacumulado = puntajeacumulado;
    }

    public String getNombredossier() {
        return nombredossier;
    }

    public void setNombredossier(String nombredossier) {
        this.nombredossier = nombredossier;
    }

    public int getPuntajemaximo() {
        return puntajemaximo;
    }

    public void setPuntajemaximo(int puntajemaximo) {
        this.puntajemaximo = puntajemaximo;
    }

    public void setIddossier(int iddossier) {
        this.iddossier = iddossier;
    }

    public int getIddossier() {
        return iddossier;
    }

    public void iniciarValores(JSONObject objetoJSONLogros){
        try {
            setPuntajeacumulado(Integer.parseInt(objetoJSONLogros.getString(Logros.PUNTAJEACUMULADO)));
            setIddossier(Integer.parseInt(objetoJSONLogros.getString(Logros.IDDOSSIER)));
            setNombredossier(objetoJSONLogros.getString(Logros.NOMBREDOSSIER));
            setPuntajemaximo(Integer.parseInt(objetoJSONLogros.getString(Logros.PUNTAJEMAXIMO)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
