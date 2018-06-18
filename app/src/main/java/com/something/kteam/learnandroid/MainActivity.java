package com.something.kteam.learnandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.something.kteam.learnandroid.data.Mydatabase;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnCloseListener,AdapterView.OnItemClickListener{
    private  SearchView searchView;
    private CustomAdapter customAdapter;
    private ListView listView;
    private ArrayList<Video> list;
    private Mydatabase mydatabase;
    private AdView mAdView;
//    private RewardedVideoAd mRewardedVideoAd;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if(savedInstanceState == null) {
            listView = (ListView) findViewById(R.id.listView);
            mAdView =(AdView)findViewById(R.id.adView);
            mydatabase = new Mydatabase(this);
            listView.setOnItemClickListener(this);
            adRequest = new AdRequest.Builder().build();
            File data = getApplicationContext().getDatabasePath(Mydatabase.DB_NAME);
            if(false == data.exists()){
                mydatabase.getReadableDatabase();
                if(copyDatabase(this)){
                    Toast.makeText(this,"copy",Toast.LENGTH_SHORT).show();
                }else return;
            }
            list = mydatabase.getAll();
        }
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        customAdapter = new CustomAdapter(this,list);
        listView.setAdapter(customAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem itemSearch = menu.findItem(R.id.search);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0)  setTitle("");
                else setTitle(getResources().getString(R.string.app_name));
                ArrayList<Video> shirts = new ArrayList<Video>();
                shirts = mydatabase.search(newText.trim().toString());
                customAdapter = new CustomAdapter(MainActivity.this,shirts);
                listView.setAdapter(customAdapter);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onClose() {
        setTitle(getResources().getString(R.string.app_name));
        return false;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this,PlayVideo.class);
        i.putExtra("link",list.get(position).getLink());
        startActivity(i);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("list",list);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        list = savedInstanceState.getParcelableArrayList("list");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    private boolean copyDatabase(Context context )  {
        try {
            InputStream inputStream = context.getAssets().open(Mydatabase.DB_NAME);
            String outFilename = Mydatabase.DBLOCATION + Mydatabase.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFilename);
            byte[] out = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(out)) > 0) {
                outputStream.write(out, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (Exception e){}
        return false;
    }
}
