package com.laudy.francesa1.app.appfrancesa1.DTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Equipo 2 on 03/11/2016.
 */

public class Pregunta {
    private int idpregunta;
    private String descripcionp;
    private int puntaje;
    private int idactaprend;
    private List<Respuesta> respuestas;

    // Nombre de campos en Base de datos entregados por PHP
    public static final String IDPREGUNTA = "idpregunta";
    public static final String DESCRIPCIONP = "descripcionp";
    public static final String PUNTAJE = "puntaje";
    public static final String IDACTAPREND = "idactaprend";


    public int getIdpregunta() {
        return idpregunta;
    }

    public void setIdpregunta(int idpregunta) {
        this.idpregunta = idpregunta;
    }

    public String getDescripcionp() {
        return descripcionp;
    }

    public void setDescripcionp(String descripcionp) {
        this.descripcionp = descripcionp;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getIdactaprend() {
        return idactaprend;
    }

    public void setIdactaprend(int idactaprend) {
        this.idactaprend = idactaprend;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public void iniciarValores(JSONObject objetoJSONPreguntas){
        try {
            setIdpregunta(Integer.parseInt(objetoJSONPreguntas.getString(Pregunta.IDPREGUNTA)));
            setDescripcionp(objetoJSONPreguntas.getString(Pregunta.DESCRIPCIONP));
            setPuntaje(Integer.parseInt(objetoJSONPreguntas.getString(Pregunta.PUNTAJE)));
            setIdactaprend(Integer.parseInt(objetoJSONPreguntas.getString(Pregunta.IDACTAPREND)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
