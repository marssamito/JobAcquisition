package com.example.joshua.jobacquisition.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.joshua.jobacquisition.R;

public class Category extends AppCompatActivity {

    CardView post,view;
    public static String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        post=(CardView)findViewById(R.id.post);
        view=(CardView) findViewById(R.id.view);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Category.this, Employers.class);
//                startActivity(i);
                catId="1";
                Intent i = new Intent(Category.this, Login.class);
                startActivity(i);

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Category.this, Employee.class);
//                startActivity(i);
                catId="2";
                Intent i = new Intent(Category.this, Login.class);
                startActivity(i);
            }
        });
    }

}
