package com.laudy.francesa1.app.appfrancesa1.DTO;

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
}
