package com.example.vocabbuilder.workerThreads;

import android.os.AsyncTask;

import com.example.vocabbuilder.MainActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class VerifyWord extends AsyncTask<String, Void, String> {


    public AsyncResponse delegate = null;
    public MainActivity mainActivity = new MainActivity();
    public String source = null;
    public VerifyWord(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        String oxfordAPIURL = strings[0];
        String oxfordAPPID = strings[1];
        String oxfordAPPKey = strings[2];
        String word = strings[3];
        try {
            source = "oxford";
            System.out.println("Request is : " + oxfordAPIURL);

            URL url = new URL(oxfordAPIURL);

            System.out.println("Url object is created...");

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            System.out.println("Connection established...");
            System.out.println("Oxford appId :: " + oxfordAPPID);
            System.out.println("Oxford appKey :: " + oxfordAPPKey);

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", oxfordAPPID);
            urlConnection.setRequestProperty("app_key", oxfordAPPKey);

            int respCode = urlConnection.getResponseCode();
            System.out.println("Response Code :: " + respCode);
            if(respCode==200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                reader.close();
                System.out.println("Response is :" + stringBuilder.toString());
                /* String fileName = "vocabulary";

                File file = mainActivity.createFile(fileName);
                mainActivity.writeDataToFile(file, word);

                mainActivity.displayMainScreen();
                mainActivity.displayFilesInStorage();*/
                return stringBuilder.toString();
            }else{
                return "";
            }

        }catch(Exception e){
            System.out.println("IN exception:: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
        try {
            delegate.processFinish(result, source);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
