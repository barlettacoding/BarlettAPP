package barletta.coding.barlettapp;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
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

public class HomeActivity extends AppCompatActivity {
    //TUTTO DI PROVA. SERVE SOLO A CAPIRE COME PRENDERE I DATI

    private Button logoutB;
    private int tipo;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    viewPageAdapter viewPageAdapt;
    RequestQueue rq;
    List<SliderUtils> sliderImg;

    String request_url = "http://barlettacoding.altervista.org/getImmagini.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        logoutB = findViewById(R.id.buttonLogOut);

        tipo = SharedPrefManager.getInstance(this).getTipo();


        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(HomeActivity.this,LoginAndRegister.class));
                finish();
                return;
            }
        });

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

                    }else{

                        viewPager.setCurrentItem(0);

                    }

                }
            });

        }
    }

    public void sendRequest(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i< response.length(); i++){

                    SliderUtils sliderUtils = new SliderUtils();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        sliderUtils.setSliderImageUrl(jsonObject.getString("immagine"));

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


}
