package barletta.coding.barlettapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryListActivity extends Fragment {

    private ListView listViewCategoryList;
    private CustomArrayAdapterCategoryList categoryAdapter;
    private static ArrayList<Locale> lista = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_category_list, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        sendRequestGetLocal();

        listViewCategoryList = getView().findViewById(R.id.listViewCategoryList);
        categoryAdapter=new CustomArrayAdapterCategoryList(getActivity(),lista);
        listViewCategoryList.setAdapter(categoryAdapter);


    }


    public void sendRequestGetLocal(){

        String urlRequest="http://barlettacoding.altervista.org/getImmagini.php";



        JsonArrayRequest jSARequest = new JsonArrayRequest(Request.Method.GET, urlRequest, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {



                        for(int i=0;i<response.length();i++) {

                            Locale locale = new Locale();

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                locale.setID(jsonObject.getInt("ID"));
                                locale.setNome(jsonObject.getString("nome"));
                                locale.setDescrizione(jsonObject.getString("descrizione"));
                                locale.setIdGestore(jsonObject.getInt("idGestore"));
                                locale.setImmagine(jsonObject.getString("immagine"));

                                lista.add(locale);

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


        MySingleton.getInstance(getActivity()).addToRequestQueue(jSARequest);

    }
}

