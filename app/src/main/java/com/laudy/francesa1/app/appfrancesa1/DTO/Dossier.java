package com.laudy.francesa1.app.appfrancesa1.DTO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Equipo 2 on 02/11/2016.
 */

public class Dossier {
    private int iddossier;
    private String nombredossier;
    // Nombre de campos en Base de datos entregados por PHP
    public static final String IDDOSSIER = "iddossier";
    public static final String NOMBREDOSSIER = "nombredossier";

    public int getIddossier() {
        return iddossier;
    }

    public void setIddossier(int iddossier) {
        this.iddossier = iddossier;
    }

    public String getNombredossier() {
        return nombredossier;
    }

    public void setNombredossier(String nombredossier) {
        this.nombredossier = nombredossier;
    }

    public void iniciarValores(JSONObject objetoJSONDossier){
        try {
            setIddossier(Integer.parseInt(objetoJSONDossier.getString(Dossier.IDDOSSIER)));
            setNombredossier(objetoJSONDossier.getString(Dossier.NOMBREDOSSIER));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
