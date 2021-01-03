package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import okhttp3.FormBody;
import okio.BufferedSink;

public class Complain extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    String gurl = "http://192.168.1.151:8000/";
    private EditText afm, searchbar, loc, odos;
    private Button submitcomplain;
    private Spinner ids, proastio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        //////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //////////////////////Getting inf ids
        String searching = fillSearchView(gurl);
        String[] options = searching.split(",");
        String[] arraySpinner = options;
        List<String> proastiaSpinner = Arrays.asList("Κεντρικού Τομέα Αθηνών" , "Νοτίου Τομέα Αθηνών", "Βορείου Τομέα Αθηνών", "Δυτικού Τομέα Αθηνών", "Πειραιώς", "Νήσων Αττικής", "Δυτικής Αττικής", "Ανατολικής Αττικής");
        final Spinner s = (Spinner) findViewById(R.id.ids);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        final Spinner s2 = (Spinner) findViewById(R.id.proastio);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, proastiaSpinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);

        submitcomplain = (Button) findViewById(R.id.submitcomplain);
        submitcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitSubscription(s, s2);
            }
        });

    }

    private void submitSubscription(Spinner s, Spinner s2){
        //////////////////////gathering data
        final String subID = (String) s.getSelectedItem();
        final String proastio = (String) s2.getSelectedItem();
        afm = (EditText) findViewById(R.id.afm);
        final String userafm = afm.getText().toString();
        loc = (EditText) findViewById(R.id.loc);
        final String custloc = loc.getText().toString();
        odos = (EditText) findViewById(R.id.odos);
        final String subaddress = odos.getText().toString();
        searchbar = (EditText) findViewById(R.id.searchbar);
        //////////////////////
        String result = null;
        String  url = gurl;
        try {
            result = run(url, userafm, subID.toString(), proastio.toString(), subaddress.toString(), custloc.toString());
            /*
            if (result.toString().equals("complain")){
                //backToMenu();
            }else{
                //display failed login message
            }*/
            searchbar.setText(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void backToMenu() {
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
    }

    public String fillSearchView(String url) {
        String urlcopy = gurl;
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(urlcopy).newBuilder();
            urlBuilder
                    .addQueryParameter("action", "givemeoptions");
            urlcopy = urlBuilder.build().toString();
        }catch(Exception i){
            i.printStackTrace();
        }

        try {
            Request request = new Request.Builder()
                    .url(urlcopy)
                    .get()
                    .build();

            Response response = client.newCall(request)
                    .execute();
            return response.body().string().toString();
        }catch(Exception j){
            return "j\n" + j.toString();
        }
    }


    public static String run(String url, String userafm, String subID, String proastio, String odos, String custloc) throws IOException {
        // issue the Get request
        Complain example = new Complain();
        String getResponse = example.doGetRequest(url, userafm, subID, proastio, odos, custloc);
        return getResponse;
    }

    public String doGetRequest(String url, String userafm, String subID, String proastio, String odos, String custloc) throws IOException {

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                    .addQueryParameter("action", "icomplain")
                    .addQueryParameter("afm", userafm)
                    .addQueryParameter("subID", subID)
                    .addQueryParameter("proastio", proastio)
                    .addQueryParameter("odos", odos)
                    .addQueryParameter("custloc", custloc);

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
            return "j\n" + j.toString();

        }

    }

}
