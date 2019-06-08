package barletta.coding.barlettapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.util.MySingleton;

public class CustomArrayAdapterCategoryList extends ArrayAdapter<Locale> {

    private Context mContext;
    private ArrayList<Locale> listaLocali;
    private ImageLoader imgLoader;

    public CustomArrayAdapterCategoryList(@NonNull Context mContext, ArrayList<Locale> listaLocali) {
        super(mContext, 0, listaLocali);
        this.mContext = mContext;
        this.listaLocali = listaLocali;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        imgLoader = MySingleton.getInstance(mContext).getImageLoader();

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.custom_list_category_layout, parent, false);

        }

        Locale currentLocale = listaLocali.get(position);

        //TO DO IMMAGINI
        ImageView immagineLocale = listItem.findViewById(R.id.imageViewLocaleLista);
        imgLoader.get(currentLocale.getImmagine(), ImageLoader.getImageListener(immagineLocale, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        TextView nomeLocale = (TextView) listItem.findViewById(R.id.textViewNomeLocaleCoupon);
        nomeLocale.setText(currentLocale.getNome());

        TextView descrLocale = (TextView) listItem.findViewById(R.id.textViewDescrizioneLocaleLista);
        descrLocale.setText(currentLocale.getDescrizione());

        RatingBar ratingBarList = listItem.findViewById(R.id.ratingBarList);
        DrawableCompat.setTint(ratingBarList.getProgressDrawable(), ContextCompat.getColor(mContext, R.color.colorPrimary));

        ratingBarList.setRating((float)currentLocale.getVoto()/currentLocale.getNumeroVoti());




        return listItem;
    }


}
