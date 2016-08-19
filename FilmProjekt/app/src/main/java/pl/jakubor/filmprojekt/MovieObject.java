package pl.jakubor.filmprojekt;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Kuba on 04.06.2016.
 */

public class MovieObject implements Serializable {
    private String titlePL;
    private String titleEng;
    private String posterUrl;
    private String date;
    private String desc;
    private String video;
    private String director;
    private String country;
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitlePL() {
        return titlePL;
    }

    public void setTitlePL(String titlePL) {
        this.titlePL = titlePL;
    }

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public MovieObject() {

    }

    public MovieObject(String name) {
        this.titlePL = name;
    }

    public MovieObject(JSONObject jsonObject) throws JSONException {
        titlePL = jsonObject.getString("tytul_pl");
        titleEng = jsonObject.optString("tytul_ang");
        posterUrl = jsonObject.getString("plakat");
        video = jsonObject.optString("trailer");
        desc=jsonObject.getString("opis");
        director = jsonObject.getString("rezyseria");
        country = jsonObject.getString("produkcja");
        genre = jsonObject.getString("gatunek");
        date = jsonObject.getString("data");

    }

    public static String loadStringFromAssets(Context context,String fileName) throws IOException {

        InputStream inputStream = context.getAssets().open(fileName);
        int size = inputStream.available();
        byte [] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer,"UTF-8");
    }
    public static MovieObject[] loadArrayFromJson(Context context, String type){
        try {
            String json = loadStringFromAssets(context,"filmy.json");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(type);
            MovieObject[] movieObjects = new MovieObject[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                MovieObject movieObject = new MovieObject(jsonArray.getJSONObject(i));
                movieObjects[i] = movieObject;
            }
            return movieObjects;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new MovieObject[0];
    }

    public static MovieObject[] loadArrayFromJson2(Context context, JSONObject jo,String type){
        try {
            JSONArray jsonArray = jo.getJSONArray(type);
            MovieObject[] movieObjects = new MovieObject[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("Długość tablicy", jsonArray.length()+"");
                Log.e("Długość tablicy", jsonArray.getJSONObject(i)+"");
                MovieObject movieObject = new MovieObject(jsonArray.getJSONObject(i));
                movieObjects[i] = movieObject;
            }
            return movieObjects;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new MovieObject[0];
    }
}
