package barletta.coding.barlettapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import barletta.coding.barlettapp.Adapter.viewPagerAdapterLocal;
import barletta.coding.barlettapp.HomeActivity;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.javaClass.PopupUtil;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;
import barletta.coding.barlettapp.util.MySingleton;
import barletta.coding.barlettapp.util.SliderUtils;

import static android.content.Intent.ACTION_CALL;


public class OpenLocalFragment extends Fragment {

    private EditText editableDescription;
    private RatingBar LocalRate;
    private PopupUtil popupUtil = new PopupUtil();
    private Button openRatingBar;
    private Button showOnMap, buttonCall, changeLocalDescription;
    private Button saveDescription;
    ProgressDialog progress;
    TextView nameLocal, descrizioneLocale;
    LinearLayout sliderDotspanel;
    static private Locale localeS;
    ViewPager imageSlider;
    viewPagerAdapterLocal viewPageAdapt;
    List<SliderUtils> sliderImg = new ArrayList<>();
    private int dotscount;
    private ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (SharedPrefManager.getInstance(getActivity()).getTipo() == 2) {
            return inflater.inflate(R.layout.open_local_fragment_manager, null);
        }

        return inflater.inflate(R.layout.open_local_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((HomeActivity) getActivity()).setActionBar(false);

        progress = new ProgressDialog(getActivity());
        nameLocal = getActivity().findViewById(R.id.TextViewLocalName);
        imageSlider = getActivity().findViewById(R.id.imageLocalSlider);
        getImageLocal(localeS.getID());
        nameLocal.setText(localeS.getNome());
        descrizioneLocale = getView().findViewById(R.id.textViewDescrizioneOpenLocal);
        descrizioneLocale.setMovementMethod(new ScrollingMovementMethod());
        descrizioneLocale.setText(localeS.getDescrizioneCompleta());

        if (SharedPrefManager.getInstance(getActivity()).getTipo() == 2) {
            startManagerLayout();
        }

        sliderDotspanel = getView().findViewById(R.id.SliderDotsLocal);
        showOnMap = getView().findViewById(R.id.buttonDirection);
        LocalRate = getView().findViewById(R.id.ratingBarFissoVoto);

        setLocalStar();
        buttonCall = getView().findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(ACTION_CALL);

                callIntent.setData(Uri.parse("tel:3476724409"));
                startActivity(callIntent);
            }
        });
        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + localeS.getLatitude() + "," + localeS.getLongitude() + " (" + localeS.getNome() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

            }
        });

        openRatingBar = getView().findViewById(R.id.buttonRatingBar);
        openRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupUtil.createPopUpRatingBar(localeS.getID(), getString(R.string.giveUsFeedback), getContext());
            }
        });


        imageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    public void getImageLocal(final int idLocale) {

        String phpUrl = "http://barlettacoding.altervista.org/getImmaginiLocali.php";

        progress.show();

        JsonArrayRequest jra = new JsonArrayRequest(Request.Method.GET, phpUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progress.dismiss();

                SliderUtils sliderUtils = new SliderUtils();

                for (int i = 0; i < response.length(); i++) {

                    try {
                        if (response.getJSONObject(i).getInt("IDLocale") == idLocale) {
                            sliderUtils.setSliderImageUrl(response.getJSONObject(i).getString("Immagine"));
                            sliderImg.add(sliderUtils);
                            sliderUtils = new SliderUtils();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                viewPageAdapt = new viewPagerAdapterLocal(sliderImg, getActivity());

                imageSlider.setAdapter(viewPageAdapt);

                dotscount = viewPageAdapt.getCount();

                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {

                    dots[i] = new ImageView(getActivity());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        MySingleton.getInstance(getActivity()).addToRequestQueue(jra);

    }

    public static void setIdLocale(Locale locale) {
        localeS = locale;
    }

    public void setLocalStar() {
        double finaleRate;
        finaleRate = localeS.getVoto() / localeS.getNumeroVoti();

        LocalRate.setRating((float) finaleRate);
    }

    public void changeDescription() {
        editableDescription.setEnabled(true);
        saveDescription.setVisibility(View.VISIBLE);
    }

    public void SaveNewDescription() {

        String url = "http://barlettacoding.altervista.org/changeLocalDescription.php";

        final String id = Integer.toString(localeS.getID());
        final String newDesciption = editableDescription.getText().toString().trim();

        StringRequest changeDescRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                editableDescription.setEnabled(false);
                saveDescription.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", id);
                params.put("descrizioneCompleta", newDesciption);
                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(changeDescRequest);

    }

    public void startManagerLayout() {

        editableDescription = getView().findViewById(R.id.textViewDescrizioneOpenLocal);
        editableDescription.setEnabled(false);
        editableDescription.setText(localeS.getDescrizioneCompleta());
        changeLocalDescription = getView().findViewById(R.id.buttonChangeDescription);
        if (localeS.getIdGestore() != SharedPrefManager.getInstance(getActivity()).getId()) {
            changeLocalDescription.setVisibility(View.GONE);
        }

        changeLocalDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDescription();
            }
        });
        saveDescription = getView().findViewById(R.id.buttonSaveDescription);
        saveDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveNewDescription();
            }
        });
    }


}
