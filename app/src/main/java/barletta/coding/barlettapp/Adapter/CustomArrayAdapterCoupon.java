package barletta.coding.barlettapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import barletta.coding.barlettapp.Fragment.CouponFragment;
import barletta.coding.barlettapp.HomeActivity;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Coupon;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;
import barletta.coding.barlettapp.util.MySingleton;

public class CustomArrayAdapterCoupon extends ArrayAdapter<Coupon> {

    private Context mContext;
    private ArrayList<Coupon> listaCoupon;
    private Button deleteCouponButton;
    private Coupon currentCoupon;
    public CustomArrayAdapterCoupon(@NonNull Context mContext, ArrayList<Coupon> listaCoupon) {
        super(mContext, 0, listaCoupon);
        this.mContext = mContext;
        this.listaCoupon = listaCoupon;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.custom_list_coupon, parent, false);

        }

        currentCoupon = listaCoupon.get(position);

        TextView nomeLocale = (TextView) listItem.findViewById(R.id.textViewNomeLocaleCoupon);
        nomeLocale.setText(currentCoupon.getNomeLocale());

        TextView descrLocale = (TextView) listItem.findViewById(R.id.textViewDescrizioneCoupon);
        descrLocale.setText(currentCoupon.getDescrizione());

        deleteCouponButton = listItem.findViewById(R.id.buttonDeleteCoupon);

        deleteCouponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCoupon();
                ((HomeActivity)getContext()).getCoupon();
            }
        });

        if(SharedPrefManager.getInstance(getContext()).getTipo() == 2 && SharedPrefManager.getInstance(getContext()).getId() == currentCoupon.getIDManager()){
            deleteCouponButton.setVisibility(View.VISIBLE);
        }

        return listItem;
    }

    public void deleteCoupon(){

        final String IdCopun = Integer.toString(currentCoupon.getID());
        String deleteUrl = "http://barlettacoding.altervista.org/DeleteCoupon.php";

        StringRequest deleteCouponRequest = new StringRequest(Request.Method.POST, deleteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getContext(), "Coupon Rimosso!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", IdCopun);
                return params;
            }
        };

        MySingleton.getInstance(getContext()).addToRequestQueue(deleteCouponRequest);

    }


}
