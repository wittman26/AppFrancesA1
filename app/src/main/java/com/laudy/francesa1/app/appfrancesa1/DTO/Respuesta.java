package com.laudy.francesa1.app.appfrancesa1.DTO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Equipo 2 on 03/11/2016.
 */

public class Respuesta {
    private int idrespuesta;
    private String descripcionr;
    private String rcorrecta;
    private int idactaprend;
    // Nombre de campos en Base de datos entregados por PHP
    public static final String IDRESPUESTA = "idrespuesta";
    public static final String DESCRIPCIONR = "descripcionr";
    public static final String RCORRECTA = "rcorrecta";

    public int getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(int idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public String getDescripcionr() {
        return descripcionr;
    }

    public void setDescripcionr(String descripcionr) {
        this.descripcionr = descripcionr;
    }

    public String getRcorrecta() {
        return rcorrecta;
    }

    public void setRcorrecta(String rcorrecta) {
        this.rcorrecta = rcorrecta;
    }

    public int getIdactaprend() {
        return idactaprend;
    }

    public void setIdactaprend(int idactaprend) {
        this.idactaprend = idactaprend;
    }

    public void iniciarValores(JSONObject objetoJSONRespuestas){
        try {
            setIdrespuesta(Integer.parseInt(objetoJSONRespuestas.getString(Respuesta.IDRESPUESTA)));
            setDescripcionr(objetoJSONRespuestas.getString(Respuesta.DESCRIPCIONR));
            setRcorrecta(objetoJSONRespuestas.getString(Respuesta.RCORRECTA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
