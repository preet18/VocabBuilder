package com.example.vocabbuilder.workerThreads;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.vocabbuilder.Activities.DisplayContent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class FetchInformation extends AsyncTask<String, Void, String> {

    private Context context = null;
    private static final int BUFFER_SIZE = 1024 * 10;
    private static final int ZERO = 0;
    private final byte[] dataBuffer = new byte[BUFFER_SIZE];
    private Document htmlDocument;
    private String htmlContentInStringFormat;
    private String selectedWord = "";
    public FetchInformation(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String link = strings[0];
        selectedWord = strings[1];
        try{

            /*Document doc = Jsoup.connect(link).get();
            Elements spanTags = doc.getElementsByTag("span");
            for (Element spanTag : spanTags) {
                String text = spanTag.ownText();
                System.out.println(text);
            }
*/
            /*Connection connection = Jsoup.connect(link);
            Document doc = connection.get();
            System.out.println("The content of the webpage is..................");
            System.out.println(doc.title());
            System.out.println(doc.body());*/

            htmlDocument = Jsoup.connect(link).get();
            htmlContentInStringFormat = htmlDocument.title();
            System.out.println("The title of the webpage..................");
            System.out.println(htmlContentInStringFormat);
            Document doc = Jsoup.parse(htmlDocument.toString());
            System.out.println("The text is...................................");
            String content = doc.text();
            return content;

            //return "ANDROID APP";

           /* String[] arr = content.split("\\.");
            for(String str : arr) {
                System.out.println(str);
            }*/
        }catch (Exception ex){
            System.out.println("In Exception................");
        }
        /*try {
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
            encoding = encoding == null ? "UTF-8" : encoding;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            String body = new String(baos.toByteArray(), encoding);
            System.out.println("Data from the website.............................");
            System.out.println(body);

            *//*BufferedInputStream in = new BufferedInputStream(url.openStream());
            StringBuilder sb = new StringBuilder();
            int bytesRead = ZERO;

            while ((bytesRead = in.read(dataBuffer, ZERO, BUFFER_SIZE)) >= ZERO)
            {
                sb.append(new String(dataBuffer, ZERO, bytesRead));
            }

            System.out.println("Data from the website..............................................");
            System.out.println(sb.toString());*//*

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return "";
    }


    @Override
    protected void onPostExecute(String result){
        System.out.println("Response is :: " + result);
        try {
            if (result == null || result.equals("")) {
                result = "No Content Found";

            }
            Intent intent = new Intent(context, DisplayContent.class);
            intent.putExtra("Response", result);
            intent.putExtra("selectedWord", selectedWord);
            context.startActivity(intent);
        }catch(Exception e) {
            System.out.println("In exception :: " + e.getMessage());
        }
    }
}
