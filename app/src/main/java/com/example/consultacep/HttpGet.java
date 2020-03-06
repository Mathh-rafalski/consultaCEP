package com.example.consultacep;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGet extends AsyncTask<Void,Void, String> {
    public String cep;
    public HttpGet(String cep) {
        this.cep = cep;
    }
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://viacep.com.br/ws/"+this.cep+"/json/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String linha;
            StringBuffer buffer = new StringBuffer();
            while ((linha = reader.readLine()) != null) {
                buffer.append(linha);
                buffer.append("\n");
            }
            inputStream.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }
}
