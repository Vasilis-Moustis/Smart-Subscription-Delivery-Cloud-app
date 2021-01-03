package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


public class crewInfo extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    String url = "http://192.168.1.151:8000/";
    private TextView todo;
    private Button showtask, showtask2, crewout;
    private Spinner proastio;
    List<String> proastiaSpinner = Arrays.asList("Κεντρικού Τομέα Αθηνών" , "Νοτίου Τομέα Αθηνών", "Βορείου Τομέα Αθηνών", "Δυτικού Τομέα Αθηνών", "Πειραιώς", "Νήσων Αττικής", "Δυτικής Αττικής", "Ανατολικής Αττικής");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_info);
        //////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //////////////////////
        final Spinner s2 = (Spinner) findViewById(R.id.proastio2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, proastiaSpinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
        showtask = (Button) findViewById(R.id.showtask);
        showtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = null;
                try {
                    final String proastio = (String) s2.getSelectedItem();
                    result = run(url,proastio);
                    //code
                    todo = (TextView) findViewById(R.id.todo);
                    todo.setText(result.toString());
                } catch (IOException e) {
                    todo = (TextView) findViewById(R.id.todo);
                    todo.setText(e.toString());
                }
            }
        });

        showtask2 = (Button) findViewById(R.id.showtask2);
        showtask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = null;
                try {
                    final String proastio = (String) s2.getSelectedItem();
                    result = run2(url,proastio);
                    //code
                    todo = (TextView) findViewById(R.id.todo);
                    todo.setText(result.toString());
                } catch (IOException e) {
                    todo = (TextView) findViewById(R.id.todo);
                    todo.setText(e.toString());
                }
            }
        });

        crewout = (Button) findViewById(R.id.crewout);
        crewout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    public void logout() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public static String run(String url, String proastio) throws IOException {
        // issue the Get request
        crewInfo example = new crewInfo();
        String getResponse = example.doGetRequest(url, proastio);
        return getResponse;
    }

    public String doGetRequest(String url, String proastio) throws IOException {

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                  .addQueryParameter("action", "ontofix")
                    .addQueryParameter("proastio", proastio);

            url = urlBuilder.build().toString();
        }catch(Exception i){
            i.printStackTrace();
        }

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request)
                    .execute();
            return response.body().string().toString();
        }catch(Exception j){
            j.printStackTrace();
            return j.toString();
        }

    }

    public static String run2(String url, String proastio) throws IOException {
        // issue the Get request
        crewInfo example = new crewInfo();
        String getResponse = example.doGetRequest2(url, proastio);
        return getResponse;
    }

    public String doGetRequest2(String url, String proastio) throws IOException {

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                    .addQueryParameter("action", "ontoload")
                    .addQueryParameter("proastio", proastio);

            url = urlBuilder.build().toString();
        }catch(Exception i){
            i.printStackTrace();
        }

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request)
                    .execute();
            return response.body().string().toString();
        }catch(Exception j){
            j.printStackTrace();
            return j.toString();
        }

    }
}

/*
public class test
{
    public static void main(String str[])
    {
        String jsonString = "{\"stat\": { \"sdr\": \"aa:bb:cc:dd:ee:ff\", \"rcv\": \"aa:bb:cc:dd:ee:ff\", \"time\": \"UTC in millis\", \"type\": 1, \"subt\": 1, \"argv\": [{\"type\": 1, \"val\":\"stackoverflow\"}]}}";
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject newJSON = jsonObject.getJSONObject("stat");
        System.out.println(newJSON.toString());
        jsonObject = new JSONObject(newJSON.toString());
        System.out.println(jsonObject.getString("rcv"));
       System.out.println(jsonObject.getJSONArray("argv"));
    }
}
 */