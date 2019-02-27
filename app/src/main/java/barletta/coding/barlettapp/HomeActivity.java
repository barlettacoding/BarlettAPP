package barletta.coding.barlettapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    //TUTTO DI PROVA. SERVE SOLO A CAPIRE COME PRENDERE I DATI


    private int tipo;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    viewPageAdapter viewPageAdapt;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    public static Locale[] localiTendenza;
    BottomNavigationView bottomNavigation;



    String request_url = "http://barlettacoding.altervista.org/getImmagini.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        broadCastCallFromUser();

        inizializeComponent();

        rq = Volley.newRequestQueue(this);

        sliderImg = new ArrayList<>();

        viewPager = findViewById(R.id.slideShowHome);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        sendRequest();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        //Come prima cosa, vediamo se l'utente è loggato, se no, lo mandiamo al login
        if(!SharedPrefManager.getInstance(this).isLogged()){
            finish();
            startActivity(new Intent(this,LoginAndRegister.class));
        }



        tipo = SharedPrefManager.getInstance(this).getTipo();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_home :
                fragment = new EmptyFragment();
                showClassObject();
                break;
            case R.id.navigation_dashboard:
                fragment = new UserFragment();
                hideClasseObject();
                break;
        }



        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){

        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentView, fragment)
                    .commit();

            return true;
        }

        return false;
    }

    private void hideClasseObject(){

        viewPager.setVisibility(View.GONE);
        sliderDotspanel.setVisibility(View.GONE);

    }

    private void showClassObject(){
        viewPager.setVisibility(View.VISIBLE);
        sliderDotspanel.setVisibility(View.VISIBLE);
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){

                        viewPager.setCurrentItem(1);

                    }else if(viewPager.getCurrentItem() == 1){

                        viewPager.setCurrentItem(2);

                    }else if(viewPager.getCurrentItem() == 2){

                        viewPager.setCurrentItem(3);

                    }else if(viewPager.getCurrentItem() == 3){

                        viewPager.setCurrentItem(4);

                    }else{
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }

    public void sendRequest(){

        localiTendenza = new Locale[5];


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i< localiTendenza.length; i++){

                    localiTendenza[i] = new Locale();

                    SliderUtils sliderUtils = new SliderUtils();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        sliderUtils.setSliderImageUrl(jsonObject.getString("immagine"));
                        localiTendenza[i].setNome(jsonObject.getString("nome"));
                        localiTendenza[i].setDescrizione(jsonObject.getString("descrizione"));
                        localiTendenza[i].setID(jsonObject.getInt("ID"));
                        localiTendenza[i].setIdGestore(jsonObject.getInt("idGestore"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sliderImg.add(sliderUtils);

                }
                viewPageAdapt = new viewPageAdapter(sliderImg, HomeActivity.this);

                viewPager.setAdapter(viewPageAdapt);



                dotscount = viewPageAdapt.getCount();

                dots = new ImageView[dotscount];

                for(int i = 0; i<dotscount;i++){

                    dots[i] = new ImageView(HomeActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 0,8,0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() ,"Errore",Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(jsonArrayRequest);
        //MySingleton.getInstance(this).addToRequestQueueImage(jsonArrayRequest);
    }

    public void inizializeComponent(){

        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }



    public void broadCastCallFromUser(){
        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish")) {
                    //finishing the activity
                    finish();
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish"));
    }

}
