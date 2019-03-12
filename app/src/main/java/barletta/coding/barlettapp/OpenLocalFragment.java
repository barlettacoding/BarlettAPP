package barletta.coding.barlettapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OpenLocalFragment extends Fragment{

    TextView nameLocal;
    static private Locale localeS;
    ViewPager imageSlider;
    viewPagerAdapterLocal viewPageAdapt;
    List<SliderUtils> sliderImg = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.open_local_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameLocal = getActivity().findViewById(R.id.TextViewLocalName);
        imageSlider = getActivity().findViewById(R.id.imageLocalSlider);
        getImageLocal(localeS.getID());
        nameLocal.setText(localeS.getNome()+" "+Integer.toString(localeS.getID()));


    }

    public void getImageLocal(final int idLocale) {

        String phpUrl = "http://barlettacoding.altervista.org/getImmaginiLocali.php";
        final String idToString = String.valueOf(idLocale);
        Toast.makeText(getActivity(),idToString,Toast.LENGTH_LONG).show();

        JsonArrayRequest jra = new JsonArrayRequest(Request.Method.GET, phpUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                SliderUtils sliderUtils = new SliderUtils();

                for (int i = 0; i<response.length();i++){

                    JSONObject jsonObject;

                    try {
                        //jsonObject = response.getJSONObject(i);
                        if(response.getJSONObject(i).getInt("IDLocale") == idLocale){
                            sliderUtils.setSliderImageUrl(response.getJSONObject(i).getString("Immagine"));
                            sliderImg.add(sliderUtils);
                        }

                        Toast.makeText(getActivity(),"PIENO",Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

                viewPageAdapt = new viewPagerAdapterLocal(sliderImg, getActivity());

                imageSlider.setAdapter(viewPageAdapt);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"VUOTO",Toast.LENGTH_LONG).show();
            }
        });

        Toast.makeText(getActivity(),"Prima della request",Toast.LENGTH_LONG).show();
        MySingleton.getInstance(getActivity()).addToRequestQueue(jra);
        Toast.makeText(getActivity(),"Dopo della request",Toast.LENGTH_LONG).show();
    }

    public static void setIdLocale(Locale locale){
        localeS = locale;
    }

}
