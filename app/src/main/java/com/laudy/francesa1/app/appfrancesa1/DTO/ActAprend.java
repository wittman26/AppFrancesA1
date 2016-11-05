package com.laudy.francesa1.app.appfrancesa1.DTO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Equipo 2 on 02/11/2016.
 */

public class ActAprend {
    private int idactaprend;
    private String nombreact;
    private String tipoactaprend;
    // Nombre de campos en Base de datos entregados por PHP
    public static final String IDACTAPREND = "idactaprend";
    public static final String NOMBREACT = "nombreact";
    public static final String TIPOACTAPREND = "tipoactaprend";

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

    public void iniciarValores(JSONObject objetoJSONActAprend){
        try {
            setIdactaprend(Integer.parseInt(objetoJSONActAprend.getString(ActAprend.IDACTAPREND)));
            setNombreact(objetoJSONActAprend.getString(ActAprend.NOMBREACT));
            setTipoactaprend(objetoJSONActAprend.getString(ActAprend.TIPOACTAPREND));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
