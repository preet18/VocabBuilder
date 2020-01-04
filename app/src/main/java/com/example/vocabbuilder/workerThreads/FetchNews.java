package com.example.vocabbuilder.workerThreads;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.vocabbuilder.Activities.WordUsageActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class FetchNews extends AsyncTask<String, Void, String> {

    public Context context = null;
    private String selectedWord = "";

    /*public FetchNews(AdapterView.OnItemClickListener context){
        this.context = context;
    }*/

    public FetchNews(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String newsAPIURL = strings[0];
        selectedWord = strings[1];
        try{
            System.out.println("The news api url is :: " + newsAPIURL);
            URL url = new URL(newsAPIURL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            int respCode = urlConnection.getResponseCode();
            System.out.println("Response code :: " + respCode);
            if(respCode==200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder("");
                while((line=reader.readLine())!=null){
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }else{
                return "";
            }
        }catch(Exception e){
            System.out.println("In exception :: " + e.getMessage());
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
       /* try {
            delegate.processFinish(result);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }*/
        System.out.println("Response is :: " + result);
        try {
            if (result != null && !result.equals("")) {
                JSONObject response = new JSONObject(result);
                JSONArray articles = response.getJSONArray("articles");
                List<String> urls = new ArrayList<>();
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    urls.add(article.getString("url"));
                    System.out.println("URL :: " + urls.get(i));
                }

            }
            Intent intent = new Intent(context, WordUsageActivity.class);
            intent.putExtra("Response", result);
            intent.putExtra("selectedWord", selectedWord);
            context.startActivity(intent);
        }catch(Exception e) {
            System.out.println("In exception :: " + e.getMessage());
        }
    }
}
