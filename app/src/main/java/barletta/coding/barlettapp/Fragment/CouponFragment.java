package barletta.coding.barlettapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

import barletta.coding.barlettapp.Adapter.CustomArrayAdapterCategoryList;
import barletta.coding.barlettapp.Adapter.CustomArrayAdapterCoupon;
import barletta.coding.barlettapp.MapsActivityNearMe;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Coupon;
import barletta.coding.barlettapp.javaClass.Locale;

public class CouponFragment extends Fragment {

    private ListView listViewCategoryList;
    private CustomArrayAdapterCoupon categoryAdapter;
    public static ArrayList<Coupon> listaCoupon = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.coupon_fragment_list, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listViewCategoryList = getView().findViewById(R.id.listViewCouponList);

        categoryAdapter = new CustomArrayAdapterCoupon(getActivity(), listaCoupon);

        listViewCategoryList.setAdapter(categoryAdapter);

    }

}

