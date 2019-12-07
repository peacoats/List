package edu.example.list;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    ListDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView estimatedCost = (TextView) findViewById(R.id.estimatedCost);
        Button bt_add = (Button) findViewById(R.id.addButton);
        Button bt_estimate = (Button) findViewById(R.id.estimateButton);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert(view);
            }
        });
        bt_estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sum = db.estimate();
                estimatedCost.setText(sum);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {
            Intent editActivity = new Intent(this, EditActivity.class);
            this.startActivity(editActivity);
            return true;
        }

        if (id == R.id.view) {
            Intent viewActivity = new Intent(this, ViewActivity.class);
            this.startActivity(viewActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insert(View v) {
        EditText itemET = findViewById(R.id.etItem);
        EditText priceET = findViewById(R.id.etPrice);
        EditText amountET = findViewById(R.id.etAmount);
        String item = itemET.getText().toString();
        double price = Double.parseDouble(priceET.getText().toString());
        double amount = Double.parseDouble(amountET.getText().toString());
        ListDB db = new ListDB(this);
        db.insertList(item, amount, price);
        Toast.makeText(MainActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
    }
}