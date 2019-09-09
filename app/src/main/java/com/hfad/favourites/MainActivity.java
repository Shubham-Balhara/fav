package com.hfad.favourites;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable{

    DatabaseHandler ordb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        ordb=db;
        Global g = (Global)getApplication();
        //g.setstr("tushar");
        g.setdata(db);


        Log.e("xoxo","activity main called");
        DatabaseHandler database = g.getdb();


        String selectQuery = "SELECT  * FROM contacts";

        SQLiteDatabase dbase = database.getWritableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        ArrayList<String> web = new ArrayList<String>();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                dbcontacts contact = new dbcontacts();
                Log.e("xoxox",cursor.getString(0));
                Log.e("xoxox",cursor.getString(1));
                web.add(cursor.getString(1));
                Log.e("xoxo",cursor.getString(2));
                // Adding contact to list
            } while (cursor.moveToNext());
        }
        CustomGrid adapter = new CustomGrid(MainActivity.this, web);
        GridView grid =(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contacts:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(this, Contacts.class);
                intent.putExtra("database",ordb);
                startActivity(intent);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
