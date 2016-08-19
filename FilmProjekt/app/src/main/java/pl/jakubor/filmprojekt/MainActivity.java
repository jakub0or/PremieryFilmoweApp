package pl.jakubor.filmprojekt;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ARTICLES_JSON = "Articles";
    public static final String MY_PREFERENCES_FILE_NAME = "myPreferencesFileName";
    SharedPreferences prefs;
    private MovieObject[] movies;
    private JSONObject jsonObject;
    private String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences(MY_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(haveNetworkConnection()) {
            try {
                jsonObject = new AsyncTaskParseJson().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            saveData(jsonObject.toString());
        } else {
            try {
                jsonObject = new JSONObject(restoreData());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       //movies = MovieObject.loadArrayFromJson(this,"filmy");
      movies = MovieObject.loadArrayFromJson2(this,jsonObject,"Film");

        navigationView.setCheckedItem(R.id.nav_premiery);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_premiery));
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

        if (id == R.id.nav_premiery) {
            PremieryFragment premieryFragment = PremieryFragment.newInstance(movies);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.containerLayout,premieryFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_info) {
          InfoFragment infoFragment = InfoFragment.newInstance();
           FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.containerLayout,infoFragment);
          fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void saveData(String x) { //zapiyswanie danych w sharedPreferences
        SharedPreferences.Editor prefsEdit = prefs.edit(); //ustawienie edycji
        prefsEdit.putString(ARTICLES_JSON, x);// zapisujemy obecny stan preferencji w pamięci trwałej
        prefsEdit.apply(); //i zatwierdzamy dodanie danych
    }

    protected String restoreData() { //pobranie danych z pamięci trwałej
        String textFromPreferences = prefs.getString(ARTICLES_JSON, ""); //pobranie danych z podanego klucza
        return(textFromPreferences); //zwrócenie pobranych danych
    }
    private boolean haveNetworkConnection() { //funkcja sprawdzająca czy użytkownik jest połączony z siecią
        boolean haveConnectedWifi = false; //flaga odpowiedzialna za połączenie WiFi
        boolean haveConnectedMobile = false; // flaga odpowiedzialna za połaczenie Mobilne operatora


        //Sprawdzenie czy posiadmy połączenie
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile; //zwracamy odpowiednią wartość.
    }
}
