package barletta.coding.barlettapp.util;

import java.util.ArrayList;
import java.util.Iterator;

import barletta.coding.barlettapp.Fragment.CategoryListActivity;
import barletta.coding.barlettapp.javaClass.Locale;

public class findLocal {

    public Locale findLocalWithCoord (double Lat, double Lang){

        Locale localeFound = null;

        ArrayList<Locale> localList = CategoryListActivity.lista;
        Iterator<Locale> LocalIteraot = localList.iterator();
        while (LocalIteraot.hasNext()){
            Locale temp = LocalIteraot.next();
            if(temp.getLatitude() == Lat && temp.getLongitude() == Lang){
                localeFound = temp;
            }
        }

        return localeFound;
    }

}
