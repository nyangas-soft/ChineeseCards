package com.internet_of_everything.chineesecards;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by olga1 on 02.10.2017.
 */

//класс-синглетон
    //считывает json-файл и формирует выводимый массив
class MainJSONArray {
    private static final MainJSONArray ourInstance = new MainJSONArray();

    //массив из файла
    private static JSONArray myJSONarr;
    //перемешанный массив
    private static JSONArray myRandomizedJSONarr;

    static MainJSONArray getInstance() {
        return ourInstance;
    }

    //конструктор
    private MainJSONArray() {
        //извлекаем строку из json файла
        try
        {
            InputStream is = MainActivity.context.getAssets().open("chineesecardsdata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            Log.d("mainActLog","buffer: "+buffer);
            is.close();
            String resultJson = new String(buffer, "UTF-8");
            Log.d("mainActLog","file read ok: "+resultJson);
            //формируем массив JSON
            try {
                JSONObject myJSONObject=new JSONObject(resultJson);
                myJSONarr=myJSONObject.getJSONArray("data");
                Log.d("mainActLog","Array before random: "+myJSONarr.toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Log.d("mainActLog","json arr fail");
            }
        }
        catch(
                IOException e)
        {
            Log.d("mainActLog","file read fail");
        }
        //перемешиваем массив
         try {
            ArrayList<Integer> intArr1 = new ArrayList<>(myJSONarr.length());
            for (int i = 0; i < myJSONarr.length(); i++) {
                intArr1.add(i);
            }
            Log.d("randLog","intArr1 before random: "+intArr1.toString());
            Collections.shuffle(intArr1);
            Log.d("randLog","intArr1 after random: "+intArr1.toString());
             myRandomizedJSONarr=new JSONArray();
            for (int i = 0; i < intArr1.size(); i++) {
                myRandomizedJSONarr.put(i, myJSONarr.getJSONObject(intArr1.get(i)));
                Log.d("randLog","myRandomizedJSONarr ["+i+"]: "+myRandomizedJSONarr.get(i).toString());
            }
            Log.d("randLog","Array after random: "+myJSONarr.toString());
        }
        catch (JSONException e){}

    }

    //получаем один json объект,
    //который представляет из себя одну карточку
    public static JSONObject getMyJSONobj(int i){
        JSONObject jo=new JSONObject();
        try{
        jo=myRandomizedJSONarr.getJSONObject(i);}
        catch (JSONException e) {

        }
        return jo;
    }

    //получаем общее количество карточек
    public static int getNumPages(){
        return myJSONarr.length();
    }
}
