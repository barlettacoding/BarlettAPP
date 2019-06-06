package barletta.coding.barlettapp.javaClass;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import barletta.coding.barlettapp.util.MySingleton;
import barletta.coding.barlettapp.R;

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

    public void createPopUpRatingBar(final int idLocale, String message, final Context mContext){

        final RatingBar rateBar;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_util_rating_bar, null);
        rateBar = popupView.findViewById(R.id.ratingBarGiveRate);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        //CIAO
        popup = new PopupWindow(popupView, width, height, focusable);


        TextView textMessage = popupView.findViewById(R.id.textViewPopupMessage);
        textMessage.setText(message);
        Button delete = popupView.findViewById(R.id.buttonDeleteUtils);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                giveRate(idLocale, rateBar.getRating(), mContext);
            }
        });
        Button cancel = popupView.findViewById(R.id.buttonCancelUtil);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }

    public void giveRate(int IdLocale, float rate, final Context mContext){

        final String idToString = Integer.toString(IdLocale);
        final String rateToString = Float.toString(rate);

        String giveRateUrl = "http://barlettacoding.altervista.org/rateLocal.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, giveRateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "Grazie!",Toast.LENGTH_LONG).show();
                popup.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Fallito",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", idToString);
                params.put("voto", rateToString);
                return params;
            }
        };
        MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

}
