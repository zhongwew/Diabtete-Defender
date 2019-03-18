package com.example.diabetedefender;

import android.app.Activity;
import android.content.Context;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import global.Recoomendataion;
import global.app_global;

public class RecommendationFragment extends Fragment {


    private BaseAdapter madapter = null;
    private static List<Recoomendataion> list;
    private Context mContext;

    private ListView list_recom;
    private SwipeRefreshLayout swipelayout;

    private Handler updateHandle;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_recommendation, null);
        updateHandle = new Handler();
        //define swipe container
        swipelayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipelayout.setY(140);
        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipelayout.setRefreshing(false);
                        new Thread(runnable).start();
                    }
                },2000);
            }

        });


        list_recom  = view.findViewById(R.id.recom_list);
        mContext = getContext();

        //mock init data
        if(list == null){
            list = new ArrayList<>();
            list.add(new Recoomendataion("Exercise","Jogging", "1","hour"));
            list.add(new Recoomendataion("Food", "Rice","100","g"));
            list.add(new Recoomendataion("Exercise", "gym","1","hour"));
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
                title.setText(list.get(position).type);
                items.setText("Detail:"+list.get(position).event+" amount:"+list.get(position).amount+list.get(position).unit);
                return convertView;
            }
        };
        list_recom.setAdapter(madapter);
        list_recom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCalendar(list.get(position));
            }
        });

        return view;
    }

    //create a Runnable to load getRecommend
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("runnable generated");
                getRecommend();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    };

    private void getRecommend() {
        System.out.println("sending suggest request");
        try {
            String response = app_global.sendGet("/suggest");
            JSONObject js = new JSONObject(response);
            String recStr = js.getString("type");

            //analyze the JSON string
            if (recStr.equals("success")) {
                System.out.println("received response from suggest");
                JSONObject suggest = js.getJSONObject("suggest");

                if (suggest.getString("type").equals("good")) {
                    updateHandle.post(new Runnable() {
                        @Override
                        public void run() {
                            Context context = getActivity().getApplicationContext();
                            Toast.makeText(context,"no more suggestions",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Thread.interrupted();
                    return;
                }
                else
                    list.add(new Recoomendataion(suggest.getString("type"),
                        suggest.getString("event"), suggest.getString("amount"), suggest.getString("unit")));
            }
            else
                updateHandle.post(new Runnable() {
                    @Override
                    public void run() {
                        Context context = getActivity().getApplicationContext();
                        Toast.makeText(context,"get suggestion fail",Toast.LENGTH_SHORT).show();
                    }
                });
        }
        catch (Exception e){
            Context context = getActivity().getApplicationContext();
            System.out.println(e);
            updateHandle.post(new Runnable() {
                @Override
                public void run() {
                    Context context = getActivity().getApplicationContext();
                    Toast.makeText(context,"network error",Toast.LENGTH_SHORT).show();
                }
            });
        }
        updateHandle.post(new Runnable() {
            @Override
            public void run() {
                madapter.notifyDataSetChanged();
            }
        });
        Thread.interrupted();
        return;
    }

    private void setCalendar(Recoomendataion rec){
//        Calendar beginTime = Calendar.getInstance();
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(2012, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(Events.TITLE, rec.type)
                .putExtra(Events.DESCRIPTION, rec.event)
                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "user@example.com");
        startActivity(intent);
    }



}
