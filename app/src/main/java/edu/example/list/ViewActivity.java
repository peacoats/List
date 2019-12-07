package edu.example.list;


import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ListDB db;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new ListDB(this);
        viewView();
    }

    public void viewView() {
        ArrayList<Listing> listings = db.selectAll();
        if (listings.size() > 0){
            ScrollView scrollView = new ScrollView(this);
            GridLayout grid = new GridLayout(this);
            grid.setRowCount(listings.size());
            grid.setColumnCount(4);

            TextView[] ids = new TextView[listings.size()];
            TextView[][] curAndCon = new TextView[listings.size()][3];


            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;

            int i = 0;

            for (Listing list : listings){
                ids[i] = new TextView(this);
                ids[i].setGravity(Gravity.CENTER);
                ids[i].setText(""+ list.getId());

                curAndCon[i][0] = new TextView(this);
                curAndCon[i][1] = new TextView(this);
                curAndCon[i][2] = new TextView(this);
                curAndCon[i][0].setText(list.getListing());
                curAndCon[i][1].setText(""+ list.getNumber());
                curAndCon[i][2].setText("" + list.getCost());
                curAndCon[i][1].setInputType(InputType.TYPE_CLASS_NUMBER);
                curAndCon[i][2].setInputType(InputType.TYPE_CLASS_NUMBER);
                curAndCon[i][0].setId(10 * list.getId());
                curAndCon[i][1].setId(10 * list.getId() + 1);


                grid.addView( ids[i], width / 10,
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( curAndCon[i][0], ( int ) ( width * .4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( curAndCon[i][1], ( int ) ( width * .15 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( curAndCon[i][2], ( int ) ( width * .15 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );

                i++;
            }
            scrollView.addView(grid);
            setContentView(scrollView);
        }
    }
}
