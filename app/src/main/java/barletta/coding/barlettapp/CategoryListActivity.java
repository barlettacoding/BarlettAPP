package barletta.coding.barlettapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Iterator;

public class CategoryListActivity extends Fragment {

    private ListView listViewCategoryList;
    private CustomArrayAdapterCategoryList categoryAdapter;
    public static ArrayList<Locale> lista = new ArrayList<>();
    public static ArrayList<Locale> listToShow = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_category_list, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listViewCategoryList = getView().findViewById(R.id.listViewCategoryList);

        categoryAdapter = new CustomArrayAdapterCategoryList(getActivity(), listToShow);
        listViewCategoryList.setAdapter(categoryAdapter);
        listViewCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Locale locale = (Locale) listViewCategoryList.getItemAtPosition(position);
                Toast.makeText(getActivity(), locale.getNome(), Toast.LENGTH_SHORT).show();
                //AGGIUNGERE FRAGMENT PER IL LOCALE. PROPRIO PER IL LOCALE
                Fragment fragment = new OpenLocalFragment();
                OpenLocalFragment.setIdLocale(locale);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentView,fragment)
                        .commit();

            }
        });


    }

    public static void showCorecctList(int tipo){

        Iterator<Locale> listIterator = lista.listIterator();

        while(listIterator.hasNext()){
            Locale local = listIterator.next();
            if(local.getTipologia() == tipo){
                listToShow.add(local);
            }
        }

    }

}

