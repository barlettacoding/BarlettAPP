package barletta.coding.barlettapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import barletta.coding.barlettapp.Fragment.OpenDiaryFragment;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.UserDiaryList;
import barletta.coding.barlettapp.javaClass.DiaryDbHelper;
import barletta.coding.barlettapp.javaClass.PopupUtil;
import barletta.coding.barlettapp.javaClass.diaryObject;

public class CustomArrayAdapterDiary extends ArrayAdapter<diaryObject> {

    private Context mContext;
    private ArrayList<diaryObject> diaryList;
    DiaryDbHelper dbHelper = null;

    public CustomArrayAdapterDiary(@NonNull Context context, ArrayList<diaryObject> list) {
        super(context, 0, list);
        mContext = context;
        diaryList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.diary_custom_adapter, parent, false);
        }

        final PopupUtil popup = new PopupUtil();

        dbHelper = new DiaryDbHelper(getContext(), null, null, 1);

        final diaryObject currentDiary = diaryList.get(position);

        ImageView image = listItem.findViewById(R.id.imageViewDiary);

        image.setImageBitmap(loadImageFromStorage(currentDiary.getPhoto(), currentDiary.getTitle()));

        TextView title = listItem.findViewById(R.id.textViewDiaryTitle);
        title.setText(currentDiary.getTitle());

        TextView description = listItem.findViewById(R.id.textViewDiaryDescription);
        description.setText(currentDiary.getDescription());

        Button buttonDelete = listItem.findViewById(R.id.buttonDeleteDiaryS);


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup.createPopUp(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelper.deleteFromDiary(currentDiary.getId());

                        Fragment fragment = new UserDiaryList();
                        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentView, fragment)
                                .commit();
                        popup.popUpDismiss();
                    }
                }, "Vuoi cancellare la foto?", mContext);


            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDiaryFragment.setDiaryObject(currentDiary);
                Fragment fragment = new OpenDiaryFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentView, fragment, "OPEN_DIARY")
                        .commit();
            }
        });

        return listItem;
    }

    private Bitmap loadImageFromStorage(String path, String title) {

        Bitmap b = null;

        try {
            File f = new File(path, title);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
}
