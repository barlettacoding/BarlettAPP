package barletta.coding.barlettapp;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class UserAddDiaryFragment extends Fragment {


    private Button buttoncamera, buttonSave;
    private ImageView cameraView;
    private EditText titleText, descText;
    private TextView maxCharTextDesc, maxCharTextTitle;
    private Bitmap bitmap;
    public diaryObject addToDiary;

    private DiaryDbHelper dbHelper = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.user_diary_add_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPhoto();
        dbHelper = new DiaryDbHelper(getActivity(), null, null, 1);
        inizializeComponent();

        buttoncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitleDescription();
                Fragment fragment = new UserDiaryList();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentView, fragment)
                        .commit();

            }
        });

    }

    public void inizializeComponent() {

        maxCharTextDesc = getView().findViewById(R.id.textViewLiveCharDesc);
        maxCharTextTitle = getView().findViewById(R.id.textViewMaxCharTitle);
        buttoncamera = getView().findViewById(R.id.buttonCamera);
        cameraView = getView().findViewById(R.id.imageViewCameraTest);
        titleText = getView().findViewById(R.id.editTextTitle);
        titleText.addTextChangedListener(myTextWatcherTitle);
        descText = getView().findViewById(R.id.editTextDescription);
        descText.addTextChangedListener(myTextWatcherDesc);
        buttonSave = getView().findViewById(R.id.buttonSave);
        cameraView.setVisibility(View.GONE);
        titleText.setVisibility(View.GONE);
        descText.setVisibility(View.GONE);
        buttonSave.setVisibility(View.GONE);


    }


    //MOSTRARE QUANTE LETTERE HO INSERITO NELLA DESCRIZIONE
    private final TextWatcher myTextWatcherDesc = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            maxCharTextDesc.setText(String.valueOf(descText.length()) + " /200");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    //MOSTRARE QUANTE LETTERE HO INSERITO NEL TITOLO
    private final TextWatcher myTextWatcherTitle = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            maxCharTextTitle.setText(String.valueOf(titleText.length()) + " /50");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        cameraView.setImageBitmap(bitmap);
        titleText.setVisibility(View.VISIBLE);
        descText.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);
        cameraView.setVisibility(View.VISIBLE);

    }

    public void setTitleDescription() {

        String title = titleText.getText().toString();
        String description = descText.getText().toString();

        String bitmapEncoded = saveBitmapInternalStorage(bitmap);

        diaryObject diaryToAdd = new diaryObject(bitmapEncoded, title, description);

        dbHelper.insertDiary(diaryToAdd, SharedPrefManager.getInstance(getActivity()).getId());

        //addToDiary = new diaryObject(bitmapEncoded,title,description);


    }

    public void getPhoto() {
        Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(picIntent, 0);

    }

    public String encodeBitmap() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteBitm = baos.toByteArray();
        String encoded = Base64.encodeToString(byteBitm, Base64.DEFAULT);
        return encoded;
    }

    public String saveBitmapInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getActivity());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String photoTitle = titleText.getText().toString().trim();
        File mypath = new File(directory, photoTitle);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return directory.getAbsolutePath();
    }

}
