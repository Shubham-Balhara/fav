package com.hfad.favourites;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Contacts extends AppCompatActivity {

    private CustomListView cstlstvw;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS=100;
    DatabaseHandler dbor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
  //      DatabaseHandler db1=(DatabaseHandler) getIntent().getSerializableExtra("database");
//        g.setdata(db1);

        Global g = (Global)getApplication();
        dbor=g.getdb();
        f();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Settings" item, show the app settings UI...
                Log.e("xoxo","entered----------------->");
                ArrayList<String> favcontacts = cstlstvw.getfavcontacts();
                ArrayList<String> favcontactsphone = cstlstvw.getfavcontactsphone();
                Log.e("xoxo","inserted----------------------------->");
                int sz=favcontacts.size();

                for(int pos=0;pos<sz;pos++)
                {

                   dbor.addContact( new dbcontacts(favcontacts.get(pos),favcontactsphone.get(pos)));
                Log.e("xoxo","saved to db"+favcontacts.get(pos)+favcontactsphone.get(pos));
                }
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;





            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int [] grantReslts)
    {
        if(requestCode == PERMISSIONS_REQUEST_READ_CONTACTS)
        {
            if(grantReslts[0]== PackageManager.PERMISSION_GRANTED)
            {
                f();
            }
            else
            {

            }
        }
    }
    public void f() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ListView listViewWithCheckBox = (ListView)findViewById(R.id.listView);
        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        List<ItemObject> listphoneitems=new ArrayList<ItemObject>();
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.e("xxx", name);
                        Log.i("xxx", phoneNo);


                        listViewItems.add(new ItemObject(name));
                        listphoneitems.add(new ItemObject(phoneNo));


                    }

                    CustomListView cstlv = new CustomListView(this, listViewItems,listphoneitems);
                    cstlstvw = cstlv;
                    listViewWithCheckBox.setAdapter(cstlv);
                    listViewWithCheckBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// make Toast when click
                            Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                        }
                    });
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

}
