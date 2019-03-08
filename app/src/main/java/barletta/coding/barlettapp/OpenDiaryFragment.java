package barletta.coding.barlettapp;

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


        byte[] imageAsBytes = Base64.decode(diaryToShow.getPhoto().getBytes(), Base64.DEFAULT);
        imageDiary.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        titleTV.setText(diaryToShow.getTitle().trim());
        descriptionTV.setText(diaryToShow.getDescription().trim());

    }

    private void inizializeComponent(){

        imageDiary = getView().findViewById(R.id.imageViewDiaryShow);
        titleTV = getView().findViewById(R.id.textViewShowDiaryTitle);
        descriptionTV = getView().findViewById(R.id.textViewShowDiaryDescription);

    }

    public static void setDiaryObject(diaryObject diaryShow){
        diaryToShow = diaryShow;
    }

}
