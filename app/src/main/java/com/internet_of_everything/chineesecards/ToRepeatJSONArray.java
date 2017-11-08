package com.internet_of_everything.chineesecards;

import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by olga1 on 06.11.2017.
 */
//класс-синглетон
//формирует json-массив с иероглифами, на которые ранее был дан неверный ответ
public class ToRepeatJSONArray {
    private static final ToRepeatJSONArray ourInstance = new ToRepeatJSONArray();

    //массив из файла
    private static JSONArray myJSONarr=new JSONArray();

    static ToRepeatJSONArray getInstance() {
        return ourInstance;
    }

    //конструктор
    private ToRepeatJSONArray(){
        //если файл существует - читаем массив
        try {
            InputStream is = MainActivity.context.openFileInput(Integer.toString(R.string.file_to_repeat_name));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String resultJson = new String(buffer, "UTF-8");
            Log.d("toRepeatLog","file read ok: "+resultJson);
            //формируем массив JSON
            try {
                JSONObject myJSONObject=new JSONObject(resultJson);
                myJSONarr=myJSONObject.getJSONArray("toRepeat");
                Log.d("toRepeatLog","Array before random: "+myJSONarr.toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Log.d("toRepeatLog","json arr fail");
            }
        }  catch (IOException e){
            Log.d("toRepeatLog","no file ");

        }

    }

    //записать новый объект в массив
    public static void pushItem(int id)
    {
        boolean itemExists=false;
        try {
            for (int i = 0; i < myJSONarr.length(); i++) {
                try {
                    if (Integer.parseInt(myJSONarr.getJSONObject(i).getString("id")) == id) {
                        itemExists = true;
                        break;
                    }
                } catch (JSONException e) {
                }
            }
        } catch (Exception e){}
        if (!itemExists) {
            myJSONarr.put(MainJSONArray.getMyJSONobj(MainJSONArray.getObjectById(id)));
            writeToFile();
        }
    }

    //удалить объект из массива
    public static void removeItem(int id)
    {
        Log.d("toRepeatLog","remove item");
        for (int i=0;i<myJSONarr.length();i++)
        {
            try {
                if (Integer.parseInt(myJSONarr.getJSONObject(i).getString("id")) ==id) {
                    myJSONarr.remove(i);
                    writeToFile();
                    break;
                }
            } catch (JSONException e){}
        }
    }

    //записать массив в файл
    private static void writeToFile()
    {

        Log.d("toRepeatLog","json arr to repeat: "+myJSONarr.toString());
        try
        {
            File dir = MainActivity.context.getFilesDir();
            File file0 = new File(dir, Integer.toString(R.string.file_to_repeat_name));
            boolean deleted = file0.delete();
            BufferedWriter file=new BufferedWriter(new OutputStreamWriter(MainActivity.context.openFileOutput(Integer.toString(R.string.file_to_repeat_name),MODE_PRIVATE)));
            String string="{\"toRepeat\": "+myJSONarr.toString()+"}";
            file.write(string);
            file.close();
            Log.d("toRepeatLog","writing file ok: "+string);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            Log.d("toRepeatLog","writing file fail");

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //получаем один json объект,
    //который представляет из себя одну карточку
    public static JSONObject getMyToRepeatJSONobj(int i){
        JSONObject jo=new JSONObject();
        try{
            jo=myJSONarr.getJSONObject(i);}
        catch (JSONException e) {

        }
        return jo;
    }

    //получаем общее количество карточек
    public static int getNumPages(){
        int length;
        try {
            length=myJSONarr.length();
        } catch (Exception e)
        {
            length=0;
        }
        return length;
    }

}
