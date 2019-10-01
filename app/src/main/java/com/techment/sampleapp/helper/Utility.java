package com.techment.sampleapp.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.techment.sampleapp.model.ResponseItems;

import java.util.List;

public class Utility {
    public static String getCheckedCount(List<ResponseItems> list){
        int getCount = 0;
        for (int i=0;i<list.size();i++){
            if(list.get(i).isChecked()){
                getCount++;
            }
        }
        return String.valueOf(getCount);
    }

    public static boolean checkInternetConnectivity(Context context){
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (mgr != null) {
            netInfo = mgr.getActiveNetworkInfo();
        }

        if (netInfo != null) {
            return netInfo.isConnected();
        } else {
            //No internet
            return false;
        }
    }

    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
