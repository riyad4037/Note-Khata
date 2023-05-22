package com.example.getalcohall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        TextView tv=findViewById(R.id.category_title_textview);
        tv.setText(getIntent().getStringExtra("subject"));
        findViewById(R.id.category_button1_cardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(Category.this, ListOfBook.class) ;
                in.putExtra("category","Lecture Notes");
                in.putExtra("subject",getIntent().getStringExtra("subject"));
                startActivity(in);
            }
        });
        findViewById(R.id.category_button2_cardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(Category.this, ListOfBook.class) ;
                in.putExtra("category","Question Bank");
                in.putExtra("subject",getIntent().getStringExtra("subject"));
                startActivity(in);
            }
        });
        findViewById(R.id.category_button3_cardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in =new Intent(Category.this,ListOfBook.class) ;
//                in.putExtra("category","Quick Test");
//                in.putExtra("subject",getIntent().getStringExtra("subject"));
//                startActivity(in);
                startActivity(new Intent(Category.this,QuizActivity.class));
            }
        });
        findViewById(R.id.category_button4_cardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(Category.this, ListOfBook.class) ;
                in.putExtra("category","Sessional Notes");
                in.putExtra("subject",getIntent().getStringExtra("subject"));
                startActivity(in);
            }
        });
    }
}