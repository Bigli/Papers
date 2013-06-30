package com.Bigli.Papers;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * By Bigli
 */
public class Corrector {

    public int getImageId(Context context, String imageName) {
        //Возвращяет картину.
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public String getShort(String text)
    {
        if(text.length() > 10)
        {
            String r_text = text.substring(10,text.length());
            Log.d("", "text = " + r_text);
            return r_text + "...";
        }

        return text;
    }

    public String getDate()
    {
        //Вызвращяет день месяц.
        return new SimpleDateFormat("dd MMMM").format(Calendar.getInstance().getTime());
    }
}
