package barletta.coding.barlettapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import barletta.coding.barlettapp.Adapter.viewPageAdapter;
import barletta.coding.barlettapp.Fragment.CategoryListActivity;
import barletta.coding.barlettapp.Fragment.CouponFragment;
import barletta.coding.barlettapp.Fragment.EmptyFragment;
import barletta.coding.barlettapp.Fragment.OpenLocalFragment;
import barletta.coding.barlettapp.Fragment.UserFragment;
import barletta.coding.barlettapp.javaClass.Coupon;
import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;
import barletta.coding.barlettapp.util.MySingleton;
import barletta.coding.barlettapp.util.SliderUtils;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, Runnable {

    ProgressDialog progressD;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots; //ImageView per i punti sotto le immagini
    viewPageAdapter viewPageAdapt; //Adapter Per lo slide di immagini
    RequestQueue rq; //richiesta per prendere i dati dei locali
    List<SliderUtils> sliderImg;
    public static Locale[] localiTendenza; //Inseriamo i dati dei 5 locali di tendenza qui
    BottomNavigationView bottomNavigation; //BottomNavigation bar. Dobbiamo nascondere l'activity

    String request_url = "http://barlettacoding.altervista.org/getListaLocali.php";
    Button confirmDelete, cancelDelete, btHotel, bCulture, bFun, bRelax, bGreenSpace, bGastronomy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Come prima cosa, vediamo se l'utente è loggato, se no, lo mandiamo al login
        if (!SharedPrefManager.getInstance(this).isLogged()) {
            finish();
            startActivity(new Intent(this, LoginAndRegister.class));

        }

        broadCastCallFromUser();

        inizializeComponent();

        //sendRequestGetLocal();

        rq = Volley.newRequestQueue(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        sliderImg = new ArrayList<>();

        viewPager = findViewById(R.id.slideShowHome);


        sliderDotspanel = findViewById(R.id.SliderDots);

        sendRequest();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        sendRequestGetLocal();
        getCoupon();

        btHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListActivity.showCorecctList(1);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, new CategoryListActivity())
                        .commit();
                hideClasseObject();

            }
        });

        bCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListActivity.showCorecctList(2);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, new CategoryListActivity())
                        .commit();
                hideClasseObject();
            }
        });


        bFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListActivity.showCorecctList(3);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, new CategoryListActivity())
                        .commit();
                hideClasseObject();
            }
        });

        bGastronomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListActivity.showCorecctList(4);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, new CategoryListActivity())
                        .commit();
                hideClasseObject();
            }
        });

        bRelax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListActivity.showCorecctList(5);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, new CategoryListActivity())
                        .commit();
                hideClasseObject();
            }
        });

        bGreenSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListActivity.showCorecctList(6);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, new CategoryListActivity())
                        .commit();
                hideClasseObject();
            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");

        if (bundle != null) {
            Locale local = (Locale) bundle.getSerializable("Locale");
            Fragment fragment = new OpenLocalFragment();
            ((OpenLocalFragment) fragment).setIdLocale(local);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.fragmentView, fragment)
                    .commit();
            hideClasseObject();
        }

        checkPermission();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        String titleActioBar = "";
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                CategoryListActivity.lista.clear();
                CategoryListActivity.listToShow.clear();
                sendRequestGetLocal();
                fragment = new EmptyFragment();
                showClassObject();
                titleActioBar = getString(R.string.app_name);
                setActionBar(false);
                getSupportActionBar().setTitle(titleActioBar);
                break;
            case R.id.navigation_profile:
                fragment = new UserFragment();
                hideClasseObject();
                titleActioBar = getString(R.string.profile_bottom_bar);
                setActionBar(false);
                getSupportActionBar().setTitle(titleActioBar);
                break;
            case R.id.navigation_favourite: //COUPON
                fragment = new CouponFragment();
                hideClasseObject();
                titleActioBar = "Coupon";
                setActionBar(false);
                getSupportActionBar().setTitle(titleActioBar);
                break;
            case R.id.navigation_diary:
                fragment = new UserDiaryList();
                hideClasseObject();
                titleActioBar = "Diary";
                setActionBar(false);
                getSupportActionBar().setTitle(titleActioBar);
                break;

        }


        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentView, fragment)
                    .commit();

            return true;
        }

        return false;
    }

    public void hideClasseObject() {

        viewPager.setVisibility(View.GONE);
        sliderDotspanel.setVisibility(View.GONE);
        btHotel.setVisibility(View.GONE);
        bCulture.setVisibility(View.GONE);
        bFun.setVisibility(View.GONE);
        bRelax.setVisibility(View.GONE);
        bGreenSpace.setVisibility(View.GONE);
        bGastronomy.setVisibility(View.GONE);


    }

    public void showClassObject() {
        viewPager.setVisibility(View.VISIBLE);
        sliderDotspanel.setVisibility(View.VISIBLE);
        btHotel.setVisibility(View.VISIBLE);
        bCulture.setVisibility(View.VISIBLE);
        bFun.setVisibility(View.VISIBLE);
        bRelax.setVisibility(View.VISIBLE);
        bGreenSpace.setVisibility(View.VISIBLE);
        bGastronomy.setVisibility(View.VISIBLE);
    }

    @Override
    public void run() {

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0) {

                        viewPager.setCurrentItem(1);

                    } else if (viewPager.getCurrentItem() == 1) {

                        viewPager.setCurrentItem(2);

                    } else if (viewPager.getCurrentItem() == 2) {

                        viewPager.setCurrentItem(3);

                    } else if (viewPager.getCurrentItem() == 3) {

                        viewPager.setCurrentItem(4);

                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }

    public void sendRequest() {

        localiTendenza = new Locale[5];

        progressD.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressD.dismiss();

                for (int i = 0; i < localiTendenza.length; i++) {

                    localiTendenza[i] = new Locale();

                    SliderUtils sliderUtils = new SliderUtils();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        sliderUtils.setSliderImageUrl(jsonObject.getString("immagine"));
                        localiTendenza[i].setID(jsonObject.getInt("ID"));
                        localiTendenza[i].setNome(jsonObject.getString("nome"));
                        localiTendenza[i].setDescrizione(jsonObject.getString("descrizione"));
                        localiTendenza[i].setIdGestore(jsonObject.getInt("idGestore"));
                        localiTendenza[i].setImmagine(jsonObject.getString("immagine"));
                        localiTendenza[i].setTipologia(jsonObject.getInt("Tipologia"));
                        localiTendenza[i].setDescrizioneCompleta(jsonObject.getString("descrizioneCompleta"));
                        localiTendenza[i].setLatitude(jsonObject.getDouble("Latitude"));
                        localiTendenza[i].setLongitude(jsonObject.getDouble("Longitude"));
                        localiTendenza[i].setVoto(jsonObject.getDouble("voto"));
                        localiTendenza[i].setNumeroVoti(jsonObject.getInt("numeroVoti"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sliderImg.add(sliderUtils);

                }
                viewPageAdapt = new viewPageAdapter(sliderImg, HomeActivity.this);

                viewPager.setAdapter(viewPageAdapt);


                dotscount = viewPageAdapt.getCount();

                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {

                    dots[i] = new ImageView(HomeActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(jsonArrayRequest);
        //MySingleton.getInstance(this).addToRequestQueueImage(jsonArrayRequest);
    }

    public void inizializeComponent() {


        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        btHotel = findViewById(R.id.buttonHotel);
        progressD = new ProgressDialog(this);
        progressD.setMessage("");
        bCulture = findViewById(R.id.buttonCultur);
        bFun = findViewById(R.id.buttonFun);
        bGastronomy = findViewById(R.id.buttonGastronomy);
        bGreenSpace = findViewById(R.id.buttonGreenSpace);
        bRelax = findViewById(R.id.buttonRelax);
    }

    //BroadCast per finire l'activity
    public void broadCastCallFromUser() {
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

    public void setActionBar(Boolean setting) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(setting);

    }

    public void createPopup(View view) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_layout, null);

        confirmDelete = popupView.findViewById(R.id.buttonDelete);
        cancelDelete = popupView.findViewById(R.id.buttonCancelD);
        //creazione popup
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popup = new PopupWindow(popupView, width, height, focusable);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        //popup.setAnimationStyle(R.anim.bounce);
        //mostra popup
        popupView.setAnimation(animation);
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);


        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountRequest();
                //Continuare verso le cose
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(getApplicationContext(), LoginAndRegister.class));
                finish();
            }

        });

        final Animation dismissAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        dismissAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                popup.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupView.startAnimation(dismissAnimation);
            }
        });

    }

    public void deleteAccountRequest() {

        String urlDelete = "http://barlettacoding.altervista.org/DeleteUser.php";

        int ID = SharedPrefManager.getInstance(this).getId();
        final String idToString = Integer.toString(ID);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(getApplicationContext(), "Account cancellato", Toast.LENGTH_SHORT).show();


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Errore cancellazione", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("ID", idToString);

                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void sendRequestGetLocal() {

        String urlRequest = "http://barlettacoding.altervista.org/getListaLocali.php";


        JsonArrayRequest jSARequest = new JsonArrayRequest(Request.Method.GET, urlRequest, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        for (int i = 0; i < response.length(); i++) {

                            Locale locale = new Locale();

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);


                                locale.setID(jsonObject.getInt("ID"));
                                locale.setNome(jsonObject.getString("nome"));
                                locale.setDescrizione(jsonObject.getString("descrizione"));
                                locale.setIdGestore(jsonObject.getInt("idGestore"));
                                locale.setImmagine(jsonObject.getString("immagine"));
                                locale.setTipologia(jsonObject.getInt("Tipologia"));
                                locale.setDescrizioneCompleta(jsonObject.getString("descrizioneCompleta"));
                                locale.setLatitude(jsonObject.getDouble("Latitude"));
                                locale.setLongitude(jsonObject.getDouble("Longitude"));
                                locale.setVoto(jsonObject.getDouble("voto"));
                                locale.setNumeroVoti(jsonObject.getInt("numeroVoti"));
                                CategoryListActivity.lista.add(locale);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //rq.add(jSARequest);


        MySingleton.getInstance(this).addToRequestQueue(jSARequest);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (keyCode == KeyEvent.KEYCODE_BACK && fragmentManager.findFragmentByTag("LOCAL_OPEN").isVisible()) {
            Fragment frag = new CategoryListActivity();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentView, frag)
                    .commit();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    public void getCoupon() {

        String urlRequest = "http://barlettacoding.altervista.org/getListaCoupon.php";
        CouponFragment.listaCoupon = new ArrayList<>();

        JsonArrayRequest jSARequest = new JsonArrayRequest(Request.Method.GET, urlRequest, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        for (int i = 0; i < response.length(); i++) {

                            Coupon coupon = new Coupon();

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);


                                coupon.setID(jsonObject.getInt("ID"));
                                coupon.setIDLocale(jsonObject.getInt("IdLocale"));
                                coupon.setDescrizione(jsonObject.getString("descrizione"));
                                coupon.setNomeLocale(jsonObject.getString("NomeLocale"));
                                coupon.setIDManager(jsonObject.getInt("IdManager"));
                                CouponFragment.listaCoupon.add(coupon);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jSARequest);
    }

}
