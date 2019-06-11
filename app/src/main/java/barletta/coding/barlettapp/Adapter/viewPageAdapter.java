package barletta.coding.barlettapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import barletta.coding.barlettapp.Fragment.OpenLocalFragment;
import barletta.coding.barlettapp.HomeActivity;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.util.MySingleton;
import barletta.coding.barlettapp.util.SliderUtils;

public class viewPageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<SliderUtils> sliderImg;
    private ImageLoader imageLoader;

    //private Integer[] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    public viewPageAdapter(List<SliderUtils> sliderImg, Context context) {

        this.sliderImg = sliderImg;

        this.context = context;
    }

    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);

        SliderUtils utils = sliderImg.get(position);

        ImageView imageView = view.findViewById(R.id.imageViewCustom);
        TextView nomeLocale = view.findViewById(R.id.textViewNomeLocale);
        //imageView.setImageResource(images[position]);
        imageLoader = MySingleton.getInstance(context).getImageLoader();
        imageLoader.get(utils.getSliderImageUrl(), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        if (position == 0) {
            nomeLocale.setText(HomeActivity.localiTendenza[0].getNome());

        } else if (position == 1) {

            nomeLocale.setText(HomeActivity.localiTendenza[1].getNome());

        } else if (position == 2) {

            nomeLocale.setText(HomeActivity.localiTendenza[2].getNome());

        } else if (position == 3) {

            nomeLocale.setText(HomeActivity.localiTendenza[3].getNome());

        } else if (position == 4) {

            nomeLocale.setText(HomeActivity.localiTendenza[4].getNome());

        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    //AGGIUNGERE FRAGMENT PER IL LOCALE. PROPRIO PER IL LOCALE
                    Fragment fragment = new OpenLocalFragment();
                    OpenLocalFragment.setIdLocale(HomeActivity.localiTendenza[0]);
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentView, fragment, "LOCAL_OPEN")
                            .commit();
                    ((HomeActivity) context).hideClasseObject();

                } else if (position == 1) {
                    Fragment fragment = new OpenLocalFragment();
                    OpenLocalFragment.setIdLocale(HomeActivity.localiTendenza[1]);
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentView, fragment, "LOCAL_OPEN")
                            .commit();
                    ((HomeActivity) context).hideClasseObject();
                } else if (position == 2) {
                    Fragment fragment = new OpenLocalFragment();
                    OpenLocalFragment.setIdLocale(HomeActivity.localiTendenza[2]);
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentView, fragment, "LOCAL_OPEN")
                            .commit();
                    ((HomeActivity) context).hideClasseObject();
                } else if (position == 3) {
                    Fragment fragment = new OpenLocalFragment();
                    OpenLocalFragment.setIdLocale(HomeActivity.localiTendenza[3]);
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentView, fragment, "LOCAL_OPEN")
                            .commit();
                    ((HomeActivity) context).hideClasseObject();
                } else if (position == 4) {
                    Fragment fragment = new OpenLocalFragment();
                    OpenLocalFragment.setIdLocale(HomeActivity.localiTendenza[4]);
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentView, fragment, "LOCAL_OPEN")
                            .commit();
                    ((HomeActivity) context).hideClasseObject();
                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
