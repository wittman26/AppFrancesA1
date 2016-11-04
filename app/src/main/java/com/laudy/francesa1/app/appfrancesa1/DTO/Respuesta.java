package com.laudy.francesa1.app.appfrancesa1.DTO;

/**
 * Created by Equipo 2 on 03/11/2016.
 */

public class Respuesta {
    private int idrespuesta;
    private String descripcionr;
    private String rcorrecta;
    private int idactaprend;

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
}
