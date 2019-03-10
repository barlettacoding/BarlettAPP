package barletta.coding.barlettapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OpenDiaryFragment extends Fragment {

    private ImageView imageDiary;
    private TextView titleTV, descriptionTV;
    public static diaryObject diaryToShow;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.open_diary_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inizializeComponent();

        imageDiary.setImageBitmap(loadImageFromStorage(diaryToShow.getPhoto(), diaryToShow.getTitle()));
        titleTV.setText(diaryToShow.getTitle().trim());
        descriptionTV.setText(diaryToShow.getDescription().trim());

    }

    private void inizializeComponent() {

        imageDiary = getView().findViewById(R.id.imageViewDiaryShow);
        titleTV = getView().findViewById(R.id.textViewShowDiaryTitle);
        descriptionTV = getView().findViewById(R.id.textViewShowDiaryDescription);

    }

    public static void setDiaryObject(diaryObject diaryShow) {
        diaryToShow = diaryShow;
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
