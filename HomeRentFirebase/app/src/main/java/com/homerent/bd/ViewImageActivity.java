package com.homerent.bd;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewImageActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ImageAdapter mAdapter;
    EditText search;
    Button call1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private ProgressBar progressBar;
    String number;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    int leangh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        search = (EditText) findViewById(R.id.search_apart);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


//        googlebutton = findViewById(R.id.googlemap);
//        messagebutton = findViewById(R.id.message);


        progressBar = findViewById(R.id.progress_circular);


        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {
                        Upload upload = postSnapshot1.getValue(Upload.class);
                        mUploads.add(upload);
                        number = upload.phone;

                    }
                }
                mAdapter = new ImageAdapter(ViewImageActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewImageActivity.this, databaseError.getMessage(),
                        Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

//        googlebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ViewImageActivity.this, MapsActivity.class);
//                startActivity(i);
//
//            }
//        });
//
//        messagebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ViewImageActivity.this, LoginActivity.class);
//                startActivity(i);
//            }
//        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                leangh = search.getText().length();
                mUploads.clear();
                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            for (DataSnapshot postSnapshot2 : postSnapshot.getChildren()) {

                                if (leangh <= postSnapshot2.getValue(Upload.class).imgUpo.length()) {
                                    if (postSnapshot2.getValue(Upload.class).imgUpo.toLowerCase().trim().contains(
                                            search.getText().toString().toLowerCase().trim()
                                    )) {
                                        for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {
                                            Upload upload = postSnapshot1.getValue(Upload.class);
                                            mUploads.add(upload);
                                        }
                                    }
                                }


                                mAdapter = new ImageAdapter(ViewImageActivity.this, mUploads);
                                mRecyclerView.setAdapter(mAdapter);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ViewImageActivity.this, databaseError.getMessage(),
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                call1 = (Button) findViewById(R.id.callP);
                call1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int permissionCheck = ContextCompat.checkSelfPermission(ViewImageActivity.this, Manifest.permission.CALL_PHONE);
                        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ViewImageActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        } else {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + number));
                            startActivity(callIntent);
                        }
                    }
                });
            }
        }, 500);



    }



}
