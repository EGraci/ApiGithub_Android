package com.example.apigithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Follower extends Fragment {
    TextView info;
    String lk;
    RecyclerView konten;
    int jml;
    Follower(String link, int jumlah){
        this.lk = link;
        this.jml = jumlah;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View tampil = inflater.inflate(R.layout.fragment_follower, container, false);
        info = (TextView)  tampil.findViewById(R.id.folo);
        konten = (RecyclerView) tampil.findViewById(R.id.konten_folo);
        info.setText("Tidak Memiliki Pengikut");

        if(jml > 0){
            info.setVisibility(View.GONE);
        }else{
            info.setVisibility(View.VISIBLE);
        }
        api(lk,getContext());
        return tampil;
    }
    public void api(String link, Context ct){
        List<GitHub> data = new ArrayList<>();
        RequestQueue api = Volley.newRequestQueue(ct);

        JsonArrayRequest json = new JsonArrayRequest(Request.Method.GET, link, null,  new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i<response.length(); i++){
                        GitHub tmp = new GitHub();
                        tmp.setUsername(response.getJSONObject(i).getString("login"));
                        tmp.setGambar(response.getJSONObject(i).getString("avatar_url"));
                        tmp.setFollo(response.getJSONObject(i).getString("followers_url"));
                        tmp.setFolli(response.getJSONObject(i).getString("following_url"));
                        data.add(tmp);
                    }
                }catch (JSONException e){
                    Log.e("Error", e.toString());
                }
                AdapterKonten tampilan = new AdapterKonten(data,ct);
                konten.setAdapter(tampilan);
                konten.setLayoutManager(new LinearLayoutManager(ct));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("json follower", error.toString());
            }
        });
        api.add(json);
    }
}