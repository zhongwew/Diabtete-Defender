package com.example.diabetedefender;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import global.Recoomendataion;
import global.app_global;

public class RecommendationFragment extends Fragment {

    final boolean ifMock = true;

    private BaseAdapter madapter = null;
    private static List<Recoomendataion> list;
    private Context mContext;

    private ListView list_recom;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recommendation, null);
        list_recom  = view.findViewById(R.id.recom_list);
        mContext = getContext();

        //mock init data
        list = new ArrayList<>();
        if(ifMock){
            for(int i = 0; i<10; i++)
                list.add(new Recoomendataion("Exercise","100 miles"));
        }
        else {
            try {
                getRecommend();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
                title.setText(list.get(position).getTitle());
                items.setText(list.get(position).getDetail());
                return convertView;
            }
        };
        list_recom.setAdapter(madapter);

        return view;
    }

    private void getRecommend() throws IOException, JSONException {
        String response = app_global.sendGet("/suggest");
        JSONObject js = new JSONObject(response);
        String recStr = js.getString("type");
        if(recStr.equals("success")){
            JSONObject suggest = js.getJSONObject("suggest");
            list.add(new Recoomendataion(suggest.getString("type"),
                    "Detail:"+suggest.getString("event")));
        }

    }

}
