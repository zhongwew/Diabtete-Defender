package com.example.diabetedefender;

import android.content.Intent;
//
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    static Fragment home;
    static Fragment recom;
    static Fragment info;


    private boolean loadFragment(Fragment fragment){
        if(fragment !=null){
             getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                     .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        home = new HomeFragment();
        recom = new RecommendationFragment();
        info = new MyInfoFragment();
        loadFragment(home);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("UserName:XXX");
        toolbar.setLogo(R.mipmap.for_fun);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment=null;
        System.out.println("changing fragment");

        switch (item.getItemId()){

            case R.id.navigation_home:
                fragment = home;
                break;
            case R.id.navigation_recommendation:
                fragment = recom;
                break;
            case R.id.navigation_my_info:
                fragment = info;
                break;

        }
        return loadFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.help:

                Intent help = new Intent(this,HelpActivity.class);
                startActivity(help);

                break;
            case R.id.settings:


                Intent settings = new Intent(this,SettingsActivity.class);
                startActivity(settings);

                break;
            case R.id.update:
                Toast.makeText(getApplicationContext(),"Latest Version Installed!",Toast.LENGTH_SHORT).show();
                Intent update = new Intent(this,UpdateActivity.class);
                startActivity(update);
                break;

            case R.id.search:
                Toast.makeText(getApplicationContext(),"Search icon clicked",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }


}
