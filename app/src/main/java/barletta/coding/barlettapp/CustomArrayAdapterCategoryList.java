package barletta.coding.barlettapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

public class CustomArrayAdapterCategoryList extends ArrayAdapter<Locale> {

    private Context mContext;
    private ArrayList<Locale> listaLocali;
    private ImageLoader imgLoader;

    public CustomArrayAdapterCategoryList(@NonNull Context mContext, ArrayList<Locale>listaLocali) {
        super(mContext,0,listaLocali);
        this.mContext=mContext;
        this.listaLocali=listaLocali;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        imgLoader = MySingleton.getInstance(mContext).getImageLoader();

        View listItem= convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.custom_list_category_layout,parent,false);

        }

        Locale currentLocale = listaLocali.get(position);

        //TO DO IMMAGINI
        ImageView immagineLocale = listItem.findViewById(R.id.imageViewLocaleLista);
        imgLoader.get(currentLocale.getImmagine(), ImageLoader.getImageListener(immagineLocale, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        TextView nomeLocale= (TextView) listItem.findViewById(R.id.textViewNomeLocaleLista);
        nomeLocale.setText(currentLocale.getNome());

        TextView descrLocale = (TextView) listItem.findViewById(R.id.textViewDescrizioneLocaleLista);
        descrLocale.setText(currentLocale.getDescrizione());


        return listItem;
    }


}
