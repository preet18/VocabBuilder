package com.example.vocabbuilder.util;

import android.content.Context;
import android.view.inspector.PropertyReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

public class Util {

    public static String verifyWord(String oxfordAPIURL, Context context) throws IOException {
        try {
            System.out.println("Request is : " + oxfordAPIURL);
            URL url = new URL(oxfordAPIURL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            String appId = readProperty("OXFORD_APP_ID", context);
            String appKey = readProperty("OXFORD_APP_KEY", context);
            System.out.println("appId :: " + appId);
            System.out.println("appKey :: " + appKey);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", appId);
            urlConnection.setRequestProperty("app_key", appKey);

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            System.out.println("Response is :" + stringBuilder.toString() );
            return stringBuilder.toString();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }

    }

    public static String readProperty(String key, Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties.getProperty(key);
    }
}
