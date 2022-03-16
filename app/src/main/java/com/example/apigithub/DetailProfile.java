package com.example.apigithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailProfile extends AppCompatActivity {
    String nama, gambar, follo, folli;
    TextView user, nm, perusahaan, lokasi;
    ImageView foto;
    ViewPager menuKonten;
    TabLayout menu;
    AdapterMenu adpMenu;
    FrameLayout load1;
    ProgressBar load2;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);

        user = (TextView) findViewById(R.id.user_profile);
        nm = (TextView) findViewById(R.id.nama_profile);
        perusahaan = (TextView) findViewById(R.id.perusahaan);
        lokasi = (TextView) findViewById(R.id.lokasi);
        foto = (ImageView) findViewById(R.id.foto_profile);
        menu = (TabLayout) findViewById(R.id.menu_table);
        menuKonten = (ViewPager) findViewById(R.id.menu_tampil);
        load1 = (FrameLayout) findViewById(R.id.load_profile1);
        load2 = (ProgressBar) findViewById(R.id.load_profile2);

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

        List<String> menuList = new ArrayList<>();
        menuList.add("Follower");
        menuList.add("Following");

        menu.setupWithViewPager(menuKonten);
        this.adpMenu = new AdapterMenu(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);



        if(
                getIntent().hasExtra("user") &&
                        getIntent().hasExtra("gambar") &&
                        getIntent().hasExtra("follo") &&
                        getIntent().hasExtra("folli")
        ){
            nama = getIntent().getStringExtra("user");
            gambar = getIntent().getStringExtra("gambar");
            follo = getIntent().getStringExtra("follo");
            folli = getIntent().getStringExtra("folli");

            user.setText(nama);
            Glide.with(this).load(gambar).circleCrop().into(foto);
            api(nama);
        }
    }

    public void api(String user){
        RequestQueue api = Volley.newRequestQueue(DetailProfile.this);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, "https://api.github.com/users/"+user, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    nm.setText(response.getString("name"));
                    perusahaan.setText(response.getString("company"));
                    lokasi.setText(response.getString("location"));
                    adpMenu.setFragment(new Follower(response.getString("followers_url"),response.getInt("followers")), "Follower");
                    adpMenu.setFragment(new Following(response.getString("following_url"),response.getInt("following")), "Following");
                    menuKonten.setAdapter(adpMenu);
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