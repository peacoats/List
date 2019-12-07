package edu.example.list;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListDB extends SQLiteOpenHelper {
    public static final String QUERY1 = "create table list (id integer primary key autoincrement, list text, number real, cost real)";
    public static final String QUERY2 = "insert into list (list, number, cost) values ('steak', 1, 9);";
    public static final String TABLE_LIST = "list";
    private static final String ID = "id";
    private static final String LISTING = "list";
    private static final String NUMBER = "number";
    private static final String COST = "cost";

    public ListDB(Context context){super(context, "ListDB", null, 1);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY1);
        sqLiteDatabase.execSQL(QUERY2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists list");
        onCreate(sqLiteDatabase);
    }

    public void insertList(String list, double number, double cost){
        String query = "insert into list (list, number, cost) values ('" + list + "' ," + number + " ," + cost + ");";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        Log.w("MainActivity",query);
    }

    public void updateById(int id, String list, double number, double cost){
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "update " + TABLE_LIST;
        sqlUpdate += " set " + LISTING + " = '" + list + "', ";
        sqlUpdate += NUMBER + " = '" + number + "', ";
        sqlUpdate += COST + " = '" + cost + "'";
        sqlUpdate += " where " + ID + " = " + id;

        db.execSQL(sqlUpdate);
        db.close();
    }

    public ArrayList<Listing> selectAll(){
        String sqlQuery = "select * from list";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        ArrayList<Listing> listings = new ArrayList<>();

        while (cursor.moveToNext()){
            Listing currentList = new Listing((Integer.parseInt(cursor.getString(0))),
                    cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3));
            listings.add(currentList);
        }
        db.close();
        return listings;
    }

    public String estimate(){
        double estimate = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(COST) as Total FROM TABLE_LIST", null);
        if(cursor.moveToFirst()){
            estimate = cursor.getDouble(cursor.getColumnIndex("Total"));
        }
        String cost = String.valueOf(estimate);
        return cost;
    }
}
