package com.company.llp.spediteur.service;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.company.llp.spediteur.R;
import com.company.llp.spediteur.common.model.AthleteLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ForwarderService {
    private static ForwarderService service;



    private ForwarderService(){

    }

   public static ForwarderService getInstance(){
       if(service==null){
           service = new ForwarderService();
       }
       return service;
    }

    public void sendDataGet(AthleteLocation mockObj, String forwardToURL) {
        StringBuilder stringBuilder = new StringBuilder(forwardToURL+"?");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("lat", mockObj.getLat());
        parameters.put("lg", mockObj.getLongitude());
        parameters.put("lastUpdated", mockObj.getLastUpdated().getTime());
        parameters.put("bibNo", mockObj.getBibNo());
        parameters.put("userId", mockObj.getAthleteId());
        parameters.put("riderName", mockObj.getRiderName());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            stringBuilder.append(getParamsString(parameters));

            URL url = new URL(stringBuilder.toString());
            Log.i(getClass().getName(), stringBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            Log.i(getClass().getName(), String.valueOf(conn.getResponseCode()));
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.i(getClass().getName(), stringBuilder.toString());


                //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
           // System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            br.close();

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(getClass().getName(), e.getMessage());
        }


    }
    public static String getParamsString(Map<String, Object> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }



}
