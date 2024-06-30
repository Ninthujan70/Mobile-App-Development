package com.liepu.finalassignment;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ServiceHandler {

    static String response = null;

    public ServiceHandler(String url) {}

    public String makeServiceCall(String url) {
        BufferedReader bufferedReader = null;

        URLConnection urlConnection;
        try {
            URL urlObject = new URL(url);
            urlConnection = urlObject.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }

            response = buffer.toString();
            Log.i("JSON", response);
        } catch (Exception e) {
            Log.e("APP", "was not able to get data", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}
