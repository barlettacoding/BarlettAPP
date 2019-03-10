package barletta.coding.barlettapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CustomArrayAdapterDiary extends ArrayAdapter<diaryObject> {

    private Context mContext;
    private ArrayList<diaryObject> diaryList = new ArrayList<>();

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


        final diaryObject currentDiary = diaryList.get(position);

        ImageView image = listItem.findViewById(R.id.imageViewDiary);

        image.setImageBitmap(loadImageFromStorage(currentDiary.photoEncoded, currentDiary.getTitle()));

        TextView title = listItem.findViewById(R.id.textViewDiaryTitle);
        title.setText(currentDiary.getTitle());

        TextView description = listItem.findViewById(R.id.textViewDiaryDescription);
        description.setText(currentDiary.getDescription());

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
