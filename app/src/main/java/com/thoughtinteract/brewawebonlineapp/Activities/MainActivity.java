package com.thoughtinteract.brewawebonlineapp.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.thoughtinteract.brewawebonlineapp.CustomAdapter.ProductCustomListAdapter;
import com.thoughtinteract.brewawebonlineapp.Model.Product;
import com.thoughtinteract.brewawebonlineapp.R;
import com.thoughtinteract.brewawebonlineapp.Utils.makeServiceCall;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Animation.AnimationListener {

    Animation zoomOut;
    ImageView img_dashboard;
    String data;
    private List<Product> productList = new ArrayList<Product>();
    private ListView listView;
    private ProductCustomListAdapter adapter;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//comment
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//hiii azhar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //hi as
        zoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out_anim);
        zoomOut.setAnimationListener(this);
        img_dashboard=(ImageView)findViewById(R.id.img_dashboard);
        img_dashboard.startAnimation(zoomOut);
        listView = (ListView) findViewById(R.id.list);

        fetchListData();
    }

    private void fetchListData() {
        new fetchListDataAsync().execute("http://172.17.11.18/brewawebonlinePHP/product_list.php");
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about) {
            // Handle the camera action
        } else if (id == R.id.offers) {

        } else if (id == R.id.cafe) {

        } else if (id == R.id.contactus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private class fetchListDataAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                data = new makeServiceCall().makeServiceCall(url);
            } catch (Exception e) {
                e.printStackTrace();
                data="";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("data",data);
            pDialog.dismiss();
            if(data != null && !data.equalsIgnoreCase(""))
            {
                try {
                    JSONArray json = new JSONArray(data);
                    for(int i =0; i<json.length();i++)
                    {
                        if(json.getJSONObject(i).has("product_id")) {
                            String product_id = json.getJSONObject(i).getString("product_id");
                            String product_title = json.getJSONObject(i).getString("product_title");
                            String product_details = json.getJSONObject(i).getString("product_details");
                            String p_address = json.getJSONObject(i).getString("p_address");
                            String image_url = json.getJSONObject(i).getString("image_url");
                            Log.d("product_id",product_id);
                            Product product = new Product();
                            product.setTitle(product_title);
                            product.setP_details(product_details);
                            product.setP_address(p_address);
                            product.setThumbnailUrl(image_url);
                            productList.add(product);
                        }

                    }
                    adapter = new ProductCustomListAdapter(MainActivity.this, productList);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



