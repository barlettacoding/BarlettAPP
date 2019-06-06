package barletta.coding.barlettapp.Fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import barletta.coding.barlettapp.Adapter.CustomArrayAdapterCategoryList;
import barletta.coding.barlettapp.MapsActivityNearMe;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Locale;

public class CategoryListActivity extends Fragment {

    private Button nearMeButton;
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

        nearMeButton = getView().findViewById(R.id.buttonNearMe);
        listViewCategoryList = getView().findViewById(R.id.listViewCategoryList);

        categoryAdapter = new CustomArrayAdapterCategoryList(getActivity(), listToShow);

        listViewCategoryList.setAdapter(categoryAdapter);
        listViewCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Locale locale = (Locale) listViewCategoryList.getItemAtPosition(position);
                //AGGIUNGERE FRAGMENT PER IL LOCALE. PROPRIO PER IL LOCALE
                Fragment fragment = new OpenLocalFragment();
                OpenLocalFragment.setIdLocale(locale);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentView,fragment)
                        .commit();

            }
        });

        nearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), MapsActivityNearMe.class));
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

