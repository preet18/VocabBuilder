package com.example.vocabbuilder.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vocabbuilder.R;

public class OpenWebPage extends AppCompatActivity {

    String selctedWord = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent openWebPageIntent = getIntent();
        String url = openWebPageIntent.getStringExtra("URL");
        selctedWord = openWebPageIntent.getStringExtra("selectedWord");

        System.out.println("Preparing the webview..........");
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        //myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebViewClient(new WebViewClient(){
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageFinished(WebView view, String url) {
                String searchText = selctedWord;
                if (searchText != null && !searchText.equals("")) {
                    myWebView.findAllAsync(searchText);
                }
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(url);

    }
}
