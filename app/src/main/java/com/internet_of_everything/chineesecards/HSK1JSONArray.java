package com.internet_of_everything.chineesecards;

/**
 * Created by olga1 on 03.11.2017.
 */

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by olga1 on 02.10.2017.
 */

//класс-синглетон
//формирует json-массив с иероглифами уровня hsk1
class HSK1JSONArray {
    private static final HSK1JSONArray ourInstance = new HSK1JSONArray();

    //массив hsk1
    private static JSONArray myHSK1JSONarr;

    static HSK1JSONArray getInstance() {
        return ourInstance;
    }

    //конструктор
    private HSK1JSONArray() {
        myHSK1JSONarr=new JSONArray();
        Log.d("HSK1Log","constructor started");
        try {
            Log.d("HSK1Log","try");
            for (int i = 0; i < MainJSONArray.getNumPages(); i++) {
                Log.d("HSK1Log",""+i);
                if (MainJSONArray.getMyJSONobj(i).getString("hsk").equals("1")) {
                    myHSK1JSONarr.put(MainJSONArray.getMyJSONobj(i));
                }
            }
            Log.d("HSK1Log", "hsk1 arr: "+myHSK1JSONarr.toString());
            Log.d("HSK1Log", "hsk1 arr length: "+myHSK1JSONarr.length());
        } catch (JSONException e) {
            Log.d("HSK1Log","json arr fail");
        }
    }

    //получаем один json объект,
    //который представляет из себя одну карточку
    public static JSONObject getMyHSK1JSONobj(int i){
        JSONObject jo=new JSONObject();
        try{
            jo=myHSK1JSONarr.getJSONObject(i);}
        catch (JSONException e) {

        }
        return jo;
    }

    //получаем общее количество карточек
    public static int getNumPages(){
        return myHSK1JSONarr.length();
    }
}
