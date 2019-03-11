package barletta.coding.barlettapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PopupUtil {

    static public PopupWindow popup = null;

    public PopupUtil (){}

    public void createPopUp(View.OnClickListener deleteListener, String message, Context mContext){

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_util, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popup = new PopupWindow(popupView, width, height, focusable);


        TextView textMessage = popupView.findViewById(R.id.textViewPopupMessage);
        textMessage.setText(message);
        Button delete = popupView.findViewById(R.id.buttonDeleteUtils);
        delete.setOnClickListener(deleteListener);
        Button cancel = popupView.findViewById(R.id.buttonCancelUtil);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }

    public void popUpDismiss(){
        popup.dismiss();
    }

}
