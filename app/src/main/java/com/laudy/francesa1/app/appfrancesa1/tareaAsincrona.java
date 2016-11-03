package com.laudy.francesa1.app.appfrancesa1;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Laudy on 29/10/2016.
 */

/* TAREA ASÍNCRONA - General para enviar y recibir datos
Parametros, Progreso, Resultado */
public class tareaAsincrona extends AsyncTask<parametrosURL, Void, String> {

    //Inicializa las variables
    public respuestaAsincrona delegar = null;
    private ProgressDialog pDialog;
    private Context context;

    //Constructor
    public tareaAsincrona(Context context)
    {
        this.context = context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Cargando....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }

    @Override
    protected String doInBackground(parametrosURL... parame) {

        String resultado = null;
        String url = null;

        try {
            url = parame[0].getUrl(); //Se obtiene la url desde los parametros

            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();

            // Si hay parámetros, los envía
            if(!parame[0].getParams().isEmpty()){
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder();

                //Adjunta los parámetros a la url
                for(Map.Entry<String,String> e: parame[0].getParams().entrySet()){
                    builder.appendQueryParameter(e.getKey(),e.getValue());
                }

                //Se envían los parámetros
                String query = builder.build().getEncodedQuery();
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }

            urlConnection.connect();
            int status = urlConnection.getResponseCode();

            //Si la petición fué correcta
            if (status == HttpsURLConnection.HTTP_OK) {

                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //Convierte a String lo devuelto por el servicio
                    BufferedReader Breader = new BufferedReader(new InputStreamReader(in));
                    try {
                        StringBuilder out1 = new StringBuilder();
                        String linea;
                        while ((linea = Breader.readLine()) != null) {
                            out1.append(linea);
                        }
                        resultado = out1.toString();
                    } finally {
                        // Se hace limpieza
                        try {
                            in.close();
                        } catch (IOException e) {
                            resultado = e.getMessage();
                        }
                    }

                } catch (Exception e) {
                    resultado = e.getMessage();
                } finally {
                    urlConnection.disconnect();
                }
            }


        } catch (Exception e) {
            resultado = e.getStackTrace().toString();
        }

        return resultado;

    }

    @Override
    protected void onPostExecute(String s) {
        pDialog.dismiss();
        delegar.traerResultados(s);
    }

}
