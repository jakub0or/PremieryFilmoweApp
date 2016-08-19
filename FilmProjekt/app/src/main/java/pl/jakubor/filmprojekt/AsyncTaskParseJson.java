package pl.jakubor.filmprojekt;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kuba on 07.06.2016.
 */

public class AsyncTaskParseJson extends AsyncTask<String, String, JSONObject> {
    final String TAG = "AsyncTaskParseJson.java";

    // set your json string url here
    String yourJsonStringUrl = "http://jakubor.esy.es/Movie/ParseXML";

    // contacts JSONArray
    JSONArray dataJsonArr = null;
    private JSONObject json;

    @Override
    protected void onPreExecute() {}

    @Override
    protected JSONObject doInBackground(String... arg0) {

        try {

            // instantiate our json parser
            JsonParser jParser = new JsonParser();

            // get json string from url
            json = jParser.getJSONFromUrl(yourJsonStringUrl);

            // get the array of users
            dataJsonArr = json.getJSONArray("Film");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject strFromDoInBg) {}
}

