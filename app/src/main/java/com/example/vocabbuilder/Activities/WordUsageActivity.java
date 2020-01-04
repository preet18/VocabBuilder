package com.example.vocabbuilder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vocabbuilder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WordUsageActivity extends AppCompatActivity {
    TextView responseView;
    ListView listView;
    Context context = this;
    String selectedWord = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_usage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Retrieving the ListView object from the xml
        listView = findViewById(R.id.urlsList);

        Intent wordUsageIntent = getIntent();
        String result = wordUsageIntent.getStringExtra("Response");
        selectedWord = wordUsageIntent.getStringExtra("selectedWord");
        List<String> urls;
        if (result != null && !result.equals("")) {
            try {
                JSONObject response = null;
                response = new JSONObject(result);
                JSONArray articles = null;
                articles = response.getJSONArray("articles");
                urls =  new ArrayList<>();
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    urls.add(article.getString("url"));
                }

            } catch (JSONException e) {
                urls =  new ArrayList<>();
                urls.add("No urls Found");
                e.printStackTrace();
            }
        }else {
            urls =  new ArrayList<>();
            urls.add("No urls Found");
        }

        //Defining an Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, urls);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Inside the listener....................................");
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                System.out.println("Item value :: " + itemValue);
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemValue)));

                //new FetchInformation(context).execute(itemValue, selectedWord);

                Intent openWebPageIntent = new Intent(getApplicationContext(), OpenWebPage.class);
                openWebPageIntent.putExtra("URL", itemValue);
                openWebPageIntent.putExtra("selectedWord", selectedWord);
                startActivity(openWebPageIntent);






                /*System.out.println("Preparing the webview..........");
                WebView myWebView = (WebView) findViewById(R.id.webview);
                myWebView.setWebViewClient(new WebViewClient());
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setDomStorageEnabled(true);
                webSettings.setJavaScriptEnabled(true);
                myWebView.loadUrl(itemValue);*/

                /*myWebView.setWebViewClient(new WebViewClient(){
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        String searchText = "Food";
                        if (searchText != null && !searchText.equals("")) {
                            myWebView.findAllAsync(searchText);
                        }
                    }
                });*/
                //myWebView.loadUrl(itemValue);
                /*myWebView.setWebViewClient(new WebViewClient() {
                    String searchText = "is";
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        if (searchText != null && !searchText.equals("")) {
                            int i = myWebView.findAll(searchText);
                            Toast.makeText(getApplicationContext(), "Found " + i + " results !",
                                    Toast.LENGTH_SHORT).show();
                            try {
                                Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
                                m.invoke(myWebView, true);
                            } catch (Throwable ignored) {
                            }
                            searchText = "";
                        }
                    }
                });*/


            }
        });
    }
}
