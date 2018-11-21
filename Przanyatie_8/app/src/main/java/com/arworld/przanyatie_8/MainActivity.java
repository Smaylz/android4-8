package com.arworld.przanyatie_8;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.webkit.WebIconDatabase;
import android.widget.Button;
import android.widget.SearchView;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String go_url= "https://google.com";
    private WebView webview_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        webview_main = (WebView) findViewById(R.id.webview_main);
        WebSettings webSettings = webview_main.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_main.setWebViewClient(new WebViewClient());
        webview_main.loadUrl(go_url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview_main.canGoBack()) {
                        webview_main.goBack();
                        get_history();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //Returns browser history
    public ArrayList<String> get_history(){
        WebBackForwardList history = webview_main.copyBackForwardList();
        ArrayList<String> history_url= new ArrayList<String>();

        for (int i=0; i<history.getSize();i++){
            WebHistoryItem item = history.getItemAtIndex(i);
            String url = item.getUrl();
            history_url.add(url);
            System.out.println( "The URL at index: " + Integer.toString(i) + " is " + url );
        }
        return history_url;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem url_bar_f= menu.findItem(R.id.cool_bar);

        SearchView searchView = (SearchView)url_bar_f.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                go_url= query.toString();
                if (!go_url.contains(".") && (!go_url.equalsIgnoreCase("google"))){
                    go_url = "http://www.google.com/#q=" + go_url;
                }
                webview_main.loadUrl(go_url);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            ArrayList<String> history_url= get_history();
            Intent historyScreenIntent= new Intent(this,HistoryScreen.class);
            final int result =1;
            historyScreenIntent.putExtra("history_url",history_url);
            startActivityForResult(historyScreenIntent,result);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        go_url= data.getStringExtra("URL_clicked");
        webview_main.loadUrl(go_url);
    }


}
