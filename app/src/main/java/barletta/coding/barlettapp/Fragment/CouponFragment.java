package barletta.coding.barlettapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import barletta.coding.barlettapp.Adapter.CustomArrayAdapterCoupon;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Coupon;

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

