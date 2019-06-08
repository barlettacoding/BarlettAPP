package barletta.coding.barlettapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Iterator;

import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;

public class AddCouponFragment extends Fragment {

    Locale managerLocale;
    EditText description;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.add_coupon_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button addCouponDB = getView().findViewById(R.id.buttonSendCoupon);
        description = getView().findViewById(R.id.editTextAddCoupon);


        addCouponDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCouponOnline();
            }
        });

    }

    public void saveCouponOnline(){

        int UserId = SharedPrefManager.getInstance(getActivity()).getId();
        Iterator<Locale> iterator = CategoryListActivity.lista.iterator();

        while (iterator.hasNext()){
            Locale temp = iterator.next();
            if(temp.getIdGestore() == UserId){
                managerLocale = temp;
            }
        }

        final int idLocale = managerLocale.getID();
        final String descrizione = description.getText().toString().trim();
        final String nomeLocale = managerLocale.getNome();

        //CREARE PHP CHE PRENDE IN POST STE COSE
        
    }

}
