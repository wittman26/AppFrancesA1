package com.laudy.francesa1.app.appfrancesa1;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Laudy on 29/10/2016.
 */
/* PARAMETROS URL - Objeto genérico para enviar Url y parámetros de ser necesario */
public class ParametrosURL {
    private String url;
    private Map<String,String> params = new TreeMap<>();

    public ParametrosURL(String url, Map<String, String> params) {
        this.params = params;
        this.url = url;
    }

    public ParametrosURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
