package com.example.vocabbuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vocabbuilder.util.Util;
import com.example.vocabbuilder.workerThreads.AsyncResponse;
import com.example.vocabbuilder.workerThreads.FetchNews;
import com.example.vocabbuilder.workerThreads.VerifyWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  AsyncResponse{

    ListView simpleList;
    /*String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};*/
    TextView textView;
    Context context = this;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayMainScreen();

        //Handling the intent receiving from other apps
        handleTheIntent();

        //browse over internet
       /* simpleList = (ListView) findViewById(R.id.simpleListView);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String str = (String) parent.getItemAtPosition(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/#q=" + str)));

            }
        });*/



        /*textView = (TextView) findViewById(R.id.textView);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.google.com/#q=
                Toast.makeText(MainActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/#q=" + textView.getText())));
            }
        });*/
        //browseTheWord();

    }

    private void browseTheWord() {

    }

    private void handleTheIntent() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                try {
                    handleSendText(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void displayMainScreen() {

        simpleList = (ListView) findViewById(R.id.simpleListView);

        //onclicking browse the word
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String word = (String) parent.getItemAtPosition(position);
                /*Intent textDisplayIntent = new Intent(getApplicationContext(), DisplayContent.class);
                textDisplayIntent.putExtra("word", word);
                startActivity(textDisplayIntent);*/

                try {
                    String newsAPIURL = formTheNewsAPIURL(word);
                    new FetchNews(context).execute(newsAPIURL, word);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/#q=" + str)));

            }
        });

        String[] vocabList = new String[0];
        try {
            vocabList = getVocabList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, vocabList);
        simpleList.setAdapter(arrayAdapter);
    }

    private String formTheNewsAPIURL(String word) throws IOException {
        String newsAPIKey = Util.readProperty("NEWS_API_KEY", this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String fromDate = sdf.format(date);
        return "https://newsapi.org/v2/everything?q=" + word + "&from=" + fromDate + "&sortBy=publishedAt&apiKey=" + newsAPIKey;
    }

    private String[] getVocabList() throws IOException {
        String fileName = "vocabulary";
        File file = new File(getFilesDir(), fileName);
        /**/
        List<String> list = readDataFromFile(file);
        String[] array =  new String[list.size()];
        array = list.toArray(array);
        return array;
    }

    private List<String> readDataFromFile(File file) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while((st=br.readLine())!=null){
            list.add(st);
        }
        return list;

    }

    //@TargetApi(19)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void handleSendText(Intent intent) throws IOException {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            System.out.println("The received text is ::" + sharedText);
            String oxfordAPIURL = formTheOxfordAPIURL(sharedText);
            String oxfordAPPID = Util.readProperty("OXFORD_APP_ID", this);
            String oxfordAPPKey = Util.readProperty("OXFORD_APP_KEY", this);
            new VerifyWord(this).execute(oxfordAPIURL, oxfordAPPID, oxfordAPPKey, sharedText);

            /*if(!response.equals("")) {
                String fileName = "vocabulary";

                File file = createFile(fileName);
                writeDataToFile(file, sharedText);

                displayMainScreen();
                displayFilesInStorage();
            }*/
            /*Gson gson = new GsonBuilder().serializeNulls().create();
            OxfordResponse res = gson.fromJson(response, OxfordResponse.class);
            JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(data);*/

            /*String fileName = "vocabulary";

            File file = createFile(fileName);
            writeDataToFile(file, sharedText);

            displayMainScreen();
            displayFilesInStorage();*/

            //String response = Util.verifyWord(oxfordAPIURL, this);

            /*boolean isWritable = isExternalStorageWritable();
            if (isWritable) {
                String directoryType = Environment.DIRECTORY_DOCUMENTS;
                File targetFolder = getExternalFilesDir(directoryType);
                System.out.println("The path of the targt folder is ::" + targetFolder.getAbsolutePath());
                targetFolder.mkdirs();
                String fileName = "VocabList.txt";
                String filePath = targetFolder+File.separator+fileName;
                File file = new File(filePath);
                if(targetFolder.isFile()){
                    System.out.println("File exists...");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(filePath);
                    fw.write(sharedText);
                    fw.close();
                }else {
                    System.out.println("File doesn't exist");
                    file.mkdir();
                }
                //FileOutputStream fos = new FileOutputStream(filePath);


                //targetFolder.mkdirs();
                //System.out.println();
            }*/



            //file.delete();
            //Printing the written data from text file
            /*FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(sharedText.getBytes());
            fos.close();*/

        }
    }

    private String formTheOxfordAPIURL(String word) {
        final String language = "en-gb";
        final String fields = "pronunciations";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;

    }

    /*Printing the fileNames in the internal storge path*/
    public void displayFilesInStorage() {
        String[] fileNames = fileList();
        System.out.println("The no of files is :: " + fileNames.length);
        for(String filename : fileNames)
            System.out.println("The FileName is :: " + filename);
    }

    /*Creates the file in the path if it doesn't exist*/
    public File createFile(String fileName) throws IOException {
        File file = new File(getFilesDir(), fileName);
        System.out.println("The location is ::" + file.getAbsolutePath());
        if(file.createNewFile()){
            System.out.println("File named " + fileName + " is created");
        }else{
            System.out.println("File named " + fileName + " already exists");
        }
        return file;
    }

    /*Writes the data to the given file*/
    public void writeDataToFile(File file, String sharedText) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(sharedText + "\n");
        bw.close();
    }

    boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    @Override
    public void processFinish(String result, String source) throws IOException, JSONException {
        if(source!=null && source.equalsIgnoreCase("oxford")) {
            parseOxfordResonse(result);
        }else if(source!=null && source.equalsIgnoreCase("news")){
            parseNewsAPIResponse(result);
        }
    }

    private void parseOxfordResonse(String result) throws JSONException, IOException {

        if (result != null && !result.equals("")) {
            JSONObject response = new JSONObject(result);
            String word = response.getString("id");

            String fileName = "vocabulary";

            File file = createFile(fileName);
            writeDataToFile(file, word);

            displayMainScreen();
            displayFilesInStorage();
        }else{
            new AlertDialog.Builder(context)
                    .setTitle("Invalid Word")
                    .setMessage("This is not a valid word")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, null)

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    /*.setNegativeButton(android.R.string.no, null)*/
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void parseNewsAPIResponse(String result) throws JSONException {
        if (result != null && !result.equals("")) {
            JSONObject response = new JSONObject(result);
            JSONArray articles = response.getJSONArray("articles");
            List<String> urls = new ArrayList<>();
            for(int i=0; i<articles.length(); i++){
                JSONObject article = articles.getJSONObject(i);
                urls.add(article.getString("url"));
                System.out.println("URL :: " + urls.get(i));
            }

        }
    }
}
