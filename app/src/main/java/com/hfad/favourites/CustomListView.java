package com.hfad.favourites;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUSHAR on 9/7/2019.
 */

public class CustomListView extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<ItemObject> listStorage;
    private  List<ItemObject> phonestorage;
    private ArrayList<String> favcontactsname = new ArrayList<String>();
    private ArrayList<String> favcontactsphone = new ArrayList<String>();
    ViewHolder listViewHolder;

    public CustomListView(Context context, List<ItemObject> customizedListView,List<ItemObject> customphonelist) {
        lInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        phonestorage = customphonelist;
    }

    public ArrayList<String> getfavcontacts(){
      int sz=favcontactsname.size();
      Log.e("xoxo","oehoeoehoe----------->");
      for(int pos=0;pos<sz;pos++)
      {
          Log.e("xoxo",favcontactsname.get(pos));
      }
        return favcontactsname;
    }

    public ArrayList<String> getfavcontactsphone(){

        int sz=favcontactsphone.size();
        for(int pos=0;pos<sz;pos++)
        {
            Log.e("xoxo",favcontactsphone.get(pos));
        }
        return favcontactsphone;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.listview_with_checkbox, parent, false);

            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.textView);
            listViewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textInListView.setText(listStorage.get(position).getName());
        listViewHolder.checkBox.setChecked(false);


        listViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listViewHolder.checkBox.isChecked()){
                    Log.e("xoxo",listStorage.get(position).getName()+phonestorage.get(position).getName()+"clicked-------------------------------->");
                    favcontactsname.remove(listStorage.get(position).getName());
                    favcontactsphone.remove(phonestorage.get(position).getName());
                    listViewHolder.checkBox.setChecked(false);
                }else{
                    listViewHolder.checkBox.setChecked(true);
                    Log.e("xoxo",listStorage.get(position).getName()+phonestorage.get(position).getName()+"unclicked-------------------------------->");
                    favcontactsname.add(listStorage.get(position).getName());
                    favcontactsphone.add(phonestorage.get(position).getName());
                }
            }
        });

        return convertView;
    }

    static class ViewHolder{

        TextView textInListView;
        CheckBox checkBox;
    }

}
