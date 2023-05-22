package com.example.getalcohall;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfBook extends AppCompatActivity {
    TextView title,subtitle;
    String sub,cat;
    ListView lv;
    BaseAdapter ba;
    ArrayList<BookItem> list;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_book);
        title=findViewById(R.id.book_list_subject_textview);
        subtitle=findViewById(R.id.book_list_catagory_textview);
        sub=getIntent().getStringExtra("subject");
        cat=getIntent().getStringExtra("category");
        title.setText(sub);
        subtitle.setText(cat);
        dr= FirebaseDatabase.getInstance().getReference("Books").child(sub.replaceAll(" ","")).child(cat.replaceAll(" ",""));
        list=new ArrayList<>();
        lv=findViewById(R.id.book_list_listview);

        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view==null){
                    view=getLayoutInflater().inflate(R.layout.main_view_list_item,null);
                }
                TextView name=view.findViewById(R.id.main_view_lsit_item_text_view);
                ImageView image=view.findViewById(R.id.main_view_list_item_image_view);
                name.setText(list.get(i).getName());
                image.setImageResource(R.drawable.pdfimg);
                name.setTextColor(Color.BLACK);
                return view;
            }

        };
        lv.setAdapter(ba);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                list=new ArrayList<>();
                for(DataSnapshot d:ds.getChildren()){
                    list.add(d.getValue(BookItem.class));
                }
                try {
                    ba.notifyDataSetChanged();
                }catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListOfBook.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent browserIntent = new Intent(ListOfBook.this, pdfView.class);
                startActivity(browserIntent);

            }
        });

    }
}