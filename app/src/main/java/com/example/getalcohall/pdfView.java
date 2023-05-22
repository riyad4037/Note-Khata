package com.example.getalcohall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class pdfView extends AppCompatActivity {

    PDFView pdfView;
    String URLaddress;
    TextView text1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("url");
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        pdfView = findViewById(R.id.pdfView);
        text1 = findViewById(R.id.text1);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference= firebaseStorage.getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                text1.setText(value);
                Toast.makeText(pdfView.this, "Updated", Toast.LENGTH_SHORT).show();
                String url = text1.getText().toString();
                new RetrivePdfSteam().execute(url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(pdfView.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });}

        class RetrivePdfSteam extends AsyncTask<String, Void, InputStream>{

            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if(urlConnection.getResponseCode()==200){
                        inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    }
                }catch (IOException e){
                    return null;
                }
                return inputStream;
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                pdfView.fromStream(inputStream).load();
            }
        }

    }
