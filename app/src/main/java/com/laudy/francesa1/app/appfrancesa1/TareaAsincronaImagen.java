package com.laudy.francesa1.app.appfrancesa1;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Equipo 2 on 03/11/2016.
 */

public class TareaAsincronaImagen extends AsyncTask<ParametrosURL, Void, Bitmap> {

    //Inicializa las variables
    public RespuestaAsincronaImagen delegar = null;
    private ProgressDialog pDialog;
    private Context context;

    //Constructor
    public TareaAsincronaImagen(Context context)
    {
        this.context = context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Cargando imagen....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }

    @Override
    protected Bitmap doInBackground(ParametrosURL... parame) {
        Bitmap imagenCargada = null;
        URL imageUrl = null;
        try {
            imageUrl = new URL(parame[0].getUrl());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagenCargada = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imagenCargada;
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        pDialog.dismiss();
        delegar.traerResultadosImagen(s);
    }
}
