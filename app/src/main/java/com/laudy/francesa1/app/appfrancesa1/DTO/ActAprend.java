package com.laudy.francesa1.app.appfrancesa1.DTO;

/**
 * Created by Equipo 2 on 02/11/2016.
 */

public class ActAprend {
    private int idactaprend;
    private String nombreact;
    private String tipoactaprend;

    public String getTipoactaprend() {
        return tipoactaprend;
    }

    public void setTipoactaprend(String tipoactaprend) {
        this.tipoactaprend = tipoactaprend;
    }

    public int getIdactaprend() {
        return idactaprend;
    }

    public void setIdactaprend(int idactaprend) {
        this.idactaprend = idactaprend;
    }

    public String getNombreact() {
        return nombreact;
    }

    public void setNombreact(String nombreact) {
        this.nombreact = nombreact;
    }
}
