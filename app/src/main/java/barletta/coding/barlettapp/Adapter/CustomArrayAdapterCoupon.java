package barletta.coding.barlettapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Coupon;
import barletta.coding.barlettapp.util.MySingleton;

public class CustomArrayAdapterCoupon extends ArrayAdapter<Coupon> {

    private Context mContext;
    private ArrayList<Coupon> listaLocali;
    private ImageLoader imgLoader;

    public CustomArrayAdapterCoupon(@NonNull Context mContext, ArrayList<Coupon> listaLocali) {
        super(mContext, 0, listaLocali);
        this.mContext = mContext;
        this.listaLocali = listaLocali;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.custom_list_coupon, parent, false);

        }

        Coupon currentLocale = listaLocali.get(position);

        TextView nomeLocale = (TextView) listItem.findViewById(R.id.textViewNomeLocaleCoupon);
        nomeLocale.setText(currentLocale.getNomeLocale());

        TextView descrLocale = (TextView) listItem.findViewById(R.id.textViewDescrizioneCoupon);
        descrLocale.setText(currentLocale.getDescrizione());

        return listItem;
    }


}
