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
class NoHSKJSONArray {
    private static final NoHSKJSONArray ourInstance = new NoHSKJSONArray();

    //массив hsk1
    private static JSONArray myNoHSKJSONarr;

    static NoHSKJSONArray getInstance() {
        return ourInstance;
    }

    //конструктор
    private NoHSKJSONArray() {
        Log.d("NoHSKLog","constructor started");
        try {
            for (int i = 0; i < MainJSONArray.getNumPages(); i++) {
                if (MainJSONArray.getMyJSONobj(i).getString("hsk") == "") {
                    myNoHSKJSONarr.put(MainJSONArray.getMyJSONobj(i));
                }
            }
            Log.d("HSK1Log",myNoHSKJSONarr.toString());
        } catch (JSONException e) {
            Log.d("NoHSKLog","json arr fail");
        }
    }

    //получаем один json объект,
    //который представляет из себя одну карточку
    public static JSONObject getMyNoHSKJSONobj(int i){
        JSONObject jo=new JSONObject();
        try{
            jo=myNoHSKJSONarr.getJSONObject(i);}
        catch (JSONException e) {

        }
        return jo;
    }

    //получаем общее количество карточек
    public static int getNumPages(){
        return myNoHSKJSONarr.length();
    }
}
