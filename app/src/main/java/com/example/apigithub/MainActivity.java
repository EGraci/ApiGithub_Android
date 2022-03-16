package com.example.apigithub;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView.OnQueryTextListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView isi;
    FrameLayout load1;
    ProgressBar load2;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load1 = (FrameLayout) findViewById(R.id.load_main1);
        load2 = (ProgressBar) findViewById(R.id.load_main2);

        load2.setProgress(i);

        CountDownTimer waktu = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                i++;
            }

            @Override
            public void onFinish() {
                load1.setVisibility(View.GONE);
            }
        };
        waktu.start();
        api("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuBar = getMenuInflater();
        menuBar.inflate(R.menu.menu, menu);

        MenuItem header = menu.findItem(R.id.cari);
        SearchView cari = (SearchView) MenuItemCompat.getActionView(header);
        cari.setQueryHint("Ketik disini");
        cari.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                api(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void api(String user){
        String link = "";
        if(user.length() == 0){
            link = "https://api.github.com/search/users?q=\"\"";
        }else{
            link = "https://api.github.com/search/users?q="+user;
        }

        List<GitHub> data = new ArrayList<>();
        RequestQueue api = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, link, null,  new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                    try {
                        JSONArray raw = response.getJSONArray("items");
                        for(int i = 0; i<raw.length(); i++){
                            GitHub tmp = new GitHub();
                            tmp.setUsername(raw.getJSONObject(i).getString("login"));
                            tmp.setGambar(raw.getJSONObject(i).getString("avatar_url"));
                            tmp.setFollo(raw.getJSONObject(i).getString("followers_url"));
                            tmp.setFolli(raw.getJSONObject(i).getString("following_url"));
                            data.add(tmp);
                        }
                        AdapterKonten tampilan = new AdapterKonten(data,MainActivity.this);
                        isi = findViewById(R.id.konten);
                        isi.setAdapter(tampilan);
                        isi.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        api.add(json);
    }
}