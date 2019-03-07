package barletta.coding.barlettapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class UserDiaryList extends Fragment {

    private Button addToDiary;
    private ListView diaryListView;
    private CustomArrayAdapterDiary diaryAdapter;

    public static ArrayList<diaryObject> diaryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_list_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inizializeComponent();

        addToDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new UserAddDiaryFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView,fragment)
                        .commit();

            }
        });



    }

    public void inizializeComponent(){

        diaryList = SharedPrefManager.getInstance(getActivity()).loadList();
        if (diaryList == null){
            diaryList = new ArrayList<>();
        }
        addToDiary = getView().findViewById(R.id.buttonAddToDiary);
        diaryListView = getView().findViewById(R.id.listViewDiary);
        diaryAdapter = new CustomArrayAdapterDiary(getActivity(),diaryList);
        diaryListView.setAdapter(diaryAdapter);
    }


}
