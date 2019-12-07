package edu.example.list;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    ListDB db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new ListDB(this);
        editView();
    }

    public void editView() {
        ArrayList<Listing> listings = db.selectAll();
        if (listings.size() > 0){
            ScrollView scrollView = new ScrollView(this);
            GridLayout grid = new GridLayout(this);
            grid.setRowCount(listings.size());
            grid.setColumnCount(5);

            TextView[] ids = new TextView[listings.size()];
            EditText[][] lists = new EditText[listings.size()][3];
            Button[] buttons = new Button[listings.size()];
            ButtonHandler bh = new ButtonHandler();

            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;

            int i = 0;

            for (Listing list : listings){
                ids[i] = new TextView(this);
                ids[i].setGravity(Gravity.CENTER);
                ids[i].setText(""+ list.getId());

                lists[i][0] = new EditText(this);
                lists[i][1] = new EditText(this);
                lists[i][2] = new EditText(this);
                lists[i][0].setText(list.getListing());
                lists[i][1].setText(""+ list.getNumber());
                lists[i][2].setText("" + list.getCost());
                lists[i][1].setInputType(InputType.TYPE_CLASS_NUMBER);
                lists[i][2].setInputType(InputType.TYPE_CLASS_NUMBER);
                lists[i][0].setId(10 * list.getId());
                lists[i][1].setId(10 * list.getId() + 1);

                buttons[i] = new Button(this);
                buttons[i].setText("Update");
                buttons[i].setId(list.getId());
                buttons[i].setOnClickListener(bh);

                grid.addView( ids[i], width / 10,
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( lists[i][0], ( int ) ( width * .4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( lists[i][1], ( int ) ( width * .15 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( lists[i][2], ( int ) ( width * .15 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( buttons[i], ( int ) ( width * .35 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );

                i++;
            }
            scrollView.addView(grid);
            setContentView(scrollView);
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {
            int listId = v.getId( );
            EditText currencyET = ( EditText ) findViewById( 10 * listId );
            EditText convertET = ( EditText ) findViewById( 10 * listId + 1 );
            String listing = currencyET.getText( ).toString( );
            String numberString = convertET.getText( ).toString( );
            String costString = convertET.getText().toString();

            // update candy in database
            try {
                double number = Double.parseDouble( numberString );
                double cost = Double.parseDouble(costString);
                db.updateById( listId, listing, number, cost );
                Toast.makeText( EditActivity.this, "Item updated",
                        Toast.LENGTH_SHORT ).show( );

                // update screen
                editView( );
            } catch( NumberFormatException nfe ) {
                Toast.makeText( EditActivity.this,
                        "Factor error", Toast.LENGTH_LONG ).show( );
            }
        }
    }
}