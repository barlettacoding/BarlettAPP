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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;
import barletta.coding.barlettapp.util.MySingleton;

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

    public void saveCouponOnline() {

        int UserId = SharedPrefManager.getInstance(getActivity()).getId();
        Iterator<Locale> iterator = CategoryListActivity.lista.iterator();

        while (iterator.hasNext()) {
            Locale temp = iterator.next();
            if (temp.getIdGestore() == UserId) {
                managerLocale = temp;
            }
        }

        final String idLocale = Integer.toString(managerLocale.getID());
        final String descrizione = description.getText().toString().trim();
        final String nomeLocale = managerLocale.getNome();
        final String idManager = Integer.toString(SharedPrefManager.getInstance(getActivity()).getId());
        //CREARE PHP CHE PRENDE IN POST STE COSE

        String addCouponUrl = "http://barlettacoding.altervista.org/AddCoupon.php";

        StringRequest request = new StringRequest(Request.Method.POST, addCouponUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getContext(), "COUPON MESSO", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IdLocale", idLocale);
                params.put("Descrizione", descrizione);
                params.put("NomeLocale", nomeLocale);
                params.put("IdManager", idManager);
                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

}
