package com.example.diabetedefender;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import data.Recommendation;

import java.util.ArrayList;
import java.util.List;

public class Recommpage extends Fragment {

    private BaseAdapter madapter = null;
    private List<Recommendation> list;
    private Context mContext;

    private ListView list_recom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fg2_content, container, false);
        list_recom  = view.findViewById(R.id.fg2_list);
        mContext = getContext();

        //mock init data
        list = new ArrayList<>();
        for(int i = 0; i<3; i++)
            list.add(new Recommendation(new ArrayList<String>()));

        madapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recomm,parent,false);
                TextView title = convertView.findViewById(R.id.recom_title);
                TextView items = convertView.findViewById(R.id.recom_item);
                title.setText("Recommendation "+Integer.toString(position+1));
                items.setText("test");
                return convertView;
            }
        };
        list_recom.setAdapter(madapter);
        return view;
    }

}
