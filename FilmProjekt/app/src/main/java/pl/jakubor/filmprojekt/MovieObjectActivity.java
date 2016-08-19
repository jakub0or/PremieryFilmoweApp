package pl.jakubor.filmprojekt;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieObjectActivity extends AppCompatActivity {

    public static final String OBJECT_KEY = "object";
    @BindView(R.id.objectImageView)
    ImageView objectImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.descTextView)
    TextView descTextView;
    @BindView(R.id.objectTextView)
    TextView objectTextView;
    @BindView(R.id.dataTextView)
    TextView dataTextView;
    @BindView(R.id.data2TextView)
    TextView data2TextView;
    @BindView(R.id.genreTextView)
    TextView genreTextView;
    @BindView(R.id.genre2TextView)
    TextView genre2TextView;
    @BindView(R.id.directorTextView)
    TextView directorTextView;
    @BindView(R.id.director2TextView)
    TextView director2TextView;
    @BindView(R.id.countryTextView)
    TextView countryTextView;
    @BindView(R.id.country2TextView)
    TextView country2TextView;
    private MovieObject movieObject;
    private Bitmap bitmap;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_object);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYoutubeVideo(movieObject.getVideo());
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movieObject = (MovieObject) getIntent().getSerializableExtra(OBJECT_KEY);
        objectTextView.setText(movieObject.getDesc());
        director2TextView.setText(movieObject.getDirector());
        country2TextView.setText(movieObject.getCountry());
        data2TextView.setText(movieObject.getDate());
        genre2TextView.setText(movieObject.getGenre());
        Glide.with(this)
                .load(movieObject.getPosterUrl())
                .into(objectImageView);
        toolbarLayout.setTitle(movieObject.getTitlePL());
        if(haveNetworkConnection()){
            bitmap = getBitmapFromURL(movieObject.getPosterUrl());
            dynamicColor(fab, bitmap);
        } else
        {

        }


    }

    private void dynamicColor(final FloatingActionButton fab, Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int mutedColor = palette.getVibrantSwatch().getRgb();
                toolbarLayout.setBackgroundColor(mutedColor);
                int vibrantColor = palette.getVibrantColor(mutedColor);
                int darkVibrantColor = palette.getDarkVibrantColor(mutedColor);
                toolbarLayout.setStatusBarScrimColor(vibrantColor);
                toolbarLayout.setContentScrimColor(vibrantColor);
                fab.setBackgroundTintList(ColorStateList.valueOf(darkVibrantColor));
                fab.setRippleColor(vibrantColor);
                descTextView.setTextColor(vibrantColor);
                directorTextView.setTextColor(vibrantColor);
                countryTextView.setTextColor(vibrantColor);
                dataTextView.setTextColor(vibrantColor);
                genreTextView.setTextColor(vibrantColor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setNavigationBarColor(vibrantColor);
                }
            }
        });
    }

    private void showYoutubeVideo(String video) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video)));

    }

    public static void start(Activity activity, MovieObject movieObject) {
        Intent intent = new Intent(activity, MovieObjectActivity.class);
        intent.putExtra(OBJECT_KEY, movieObject);
        activity.startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
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
