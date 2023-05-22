package com.example.getalcohall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Random;

public class MainView extends Fragment {
    Home home;
    public MainView(){}
    public MainView(Home home){
        this.home=home;
    }



ListView lv;
BaseAdapter ba;
String text[]={"Machine Learning","Programming with C","Operating System","Number Theory","Data Structure"};
int colors[]={Color.YELLOW,Color.LTGRAY,Color.BLUE,Color.GREEN,Color.RED, android.R.color.holo_purple};
int icons[]={R.drawable.lecturenote,R.drawable.lecturenote,R.drawable.lecturenote,R.drawable.lecturenote,R.drawable.lecturenote};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_main_view, container, false);

        lv=view.findViewById(R.id.main_view_list_view);

        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return text.length;
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
            public View getView(int i, View vi, ViewGroup viewGroup) {
                if(vi==null){
                    vi=inflater.inflate(R.layout.main_view_list_item,null);
                }
                TextView tv=vi.findViewById(R.id.main_view_lsit_item_text_view);
                ImageView iv=vi.findViewById(R.id.main_view_list_item_image_view);
                tv.setText(text[i]);
                iv.setImageResource(icons[i]);
                LinearLayout ll=vi.findViewById(R.id.main_view_list_item_color_linner_layout);
                ll.setBackgroundColor(colors[new Random().nextInt(5)]);
                return vi;
            }
        };
        lv.setAdapter(ba);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in=new Intent(home, Category.class);
                in.putExtra("subject",text[i]);
                startActivity(in);
            }
        });


        return view;
    }
}