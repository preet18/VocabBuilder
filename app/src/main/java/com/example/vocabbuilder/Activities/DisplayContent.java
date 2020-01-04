package com.example.vocabbuilder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vocabbuilder.R;

public class DisplayContent extends AppCompatActivity {

    private TextView myText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);

        Intent wordIntent = getIntent();
        String content = wordIntent.getStringExtra("Response");
        String selectedWord = wordIntent.getStringExtra("selectedWord");

        Log.d("DisplayContent", "The selected word is......... " + content);
        myText = (TextView) findViewById(R.id.htmlToTextView);
        myText.setText( content);

        String newContent = content.replaceAll(selectedWord, "<font color='red'>"+selectedWord+"</font>");
        myText.setText(Html.fromHtml(newContent));

       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String result = getIntent().getStringExtra("Response");

        TextView lView = (TextView) findViewById(R.id.htmlToTextView);

        lView.setText("My Text");*/

        /*lView.addView(myText);*/
    }
}
