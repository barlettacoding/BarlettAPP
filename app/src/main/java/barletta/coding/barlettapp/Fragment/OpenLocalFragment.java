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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.MySingleton;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.SliderUtils;
import barletta.coding.barlettapp.viewPagerAdapterLocal;


public class OpenLocalFragment extends Fragment{

    private Button showOnMap;
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

        return inflater.inflate(R.layout.open_local_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progress = new ProgressDialog(getActivity());
        nameLocal = getActivity().findViewById(R.id.TextViewLocalName);
        imageSlider = getActivity().findViewById(R.id.imageLocalSlider);
        getImageLocal(localeS.getID());
        nameLocal.setText(localeS.getNome());
        descrizioneLocale = getView().findViewById(R.id.textViewDescrizioneOpenLocal);
        descrizioneLocale.setText(localeS.getDescrizioneCompleta());
        sliderDotspanel = getView().findViewById(R.id.SliderDotsLocal);
        showOnMap = getView().findViewById(R.id.buttonDirection);
        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + localeS.getLatitude() + "," + localeS.getLongitude() + " (" + localeS.getNome() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

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
        final String idToString = String.valueOf(idLocale);

        progress.show();


        JsonArrayRequest jra = new JsonArrayRequest(Request.Method.GET, phpUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progress.dismiss();

                SliderUtils sliderUtils = new SliderUtils();

                for (int i = 0; i<response.length();i++){

                    JSONObject jsonObject;

                    try {
                        //jsonObject = response.getJSONObject(i);
                        if(response.getJSONObject(i).getInt("IDLocale") == idLocale){
                            sliderUtils.setSliderImageUrl(response.getJSONObject(i).getString("Immagine"));
                            sliderImg.add(sliderUtils);
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

    public static void setIdLocale(Locale locale){
        localeS = locale;
    }

}
