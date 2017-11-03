package com.internet_of_everything.chineesecards;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by olga1 on 03.11.2017.
 */

//класс-синглетон
//формирует json-массив с иероглифами уровня hsk1
class HSK2JSONArray {
    private static final HSK2JSONArray ourInstance = new HSK2JSONArray();

    //массив hsk1
    private static JSONArray myHSK2JSONarr;

    static HSK2JSONArray getInstance() {
        return ourInstance;
    }

    //конструктор
    private HSK2JSONArray() {
        Log.d("HSK2Log","constructor started");
        try {
            for (int i = 0; i < MainJSONArray.getNumPages(); i++) {
                if (MainJSONArray.getMyJSONobj(i).getString("hsk") == "2") {
                    myHSK2JSONarr.put(MainJSONArray.getMyJSONobj(i));
                }
            }
            Log.d("HSK2Log",myHSK2JSONarr.toString());
        } catch (JSONException e) {
            Log.d("HSK2Log","json arr fail");
        }
    }

    //получаем один json объект,
    //который представляет из себя одну карточку
    public static JSONObject getMyHSK2JSONobj(int i){
        JSONObject jo=new JSONObject();
        try{
            jo=myHSK2JSONarr.getJSONObject(i);}
        catch (JSONException e) {

        }
        return jo;
    }

    //получаем общее количество карточек
    public static int getNumPages(){
        return myHSK2JSONarr.length();
    }
}
