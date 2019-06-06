package barletta.coding.barlettapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import barletta.coding.barlettapp.Adapter.CustomArrayAdapterDiary;
import barletta.coding.barlettapp.Fragment.OpenDiaryFragment;
import barletta.coding.barlettapp.Fragment.UserAddDiaryFragment;
import barletta.coding.barlettapp.javaClass.DiaryDbHelper;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;
import barletta.coding.barlettapp.javaClass.diaryObject;

public class UserDiaryList extends Fragment {
    private ConstraintLayout layout;
    private Button addToDiary;
    private ListView diaryListView;
    private CustomArrayAdapterDiary diaryAdapter;
    public diaryObject diaryToOpen;
    public static ArrayList<diaryObject> diaryList;

    DiaryDbHelper db = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_list_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DiaryDbHelper(getActivity(),null,null ,1);
        inizializeComponent();

        addToDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new UserAddDiaryFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, fragment)
                        .commit();

            }
        });

        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                diaryToOpen = (diaryObject)parent.getItemAtPosition(position);
                OpenDiaryFragment.setDiaryObject(diaryToOpen);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView,new OpenDiaryFragment())
                        .commit();
            }
        });



    }

    public void inizializeComponent(){

        diaryList = db.getListDiary(SharedPrefManager.getInstance(getActivity()).getId());
        if (diaryList == null){
            diaryList = new ArrayList<>();
        }
        addToDiary = getView().findViewById(R.id.buttonAddToDiary);
        diaryListView = getView().findViewById(R.id.listViewDiary);
        diaryAdapter = new CustomArrayAdapterDiary(getActivity(),diaryList);
        diaryListView.setAdapter(diaryAdapter);

        layout = getView().findViewById(R.id.constraintDiaryList);
    }




}
