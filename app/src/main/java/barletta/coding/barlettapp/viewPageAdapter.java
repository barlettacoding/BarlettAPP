package barletta.coding.barlettapp;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

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
                    //FAI ROBE
                    Toast.makeText(context, HomeActivity.localiTendenza[0].getNome(), Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(context, HomeActivity.localiTendenza[1].getNome(), Toast.LENGTH_SHORT).show();
                } else if (position == 2) {
                    Toast.makeText(context, "Loli 3", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {
                    Toast.makeText(context, "Loli 3", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {
                    Toast.makeText(context, "Loli 3", Toast.LENGTH_SHORT).show();
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
