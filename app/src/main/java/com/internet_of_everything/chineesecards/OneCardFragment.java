package com.internet_of_everything.chineesecards;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneCardFragment extends Fragment {

    public OneCardFragment() {
        // Required empty public constructor
    }

    OnRepeatCatalogChangedListener mCallback;

    // Container Activity must implement this interface
    public interface OnRepeatCatalogChangedListener {
        public void onRepeatCatalogChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnRepeatCatalogChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //инициализация все view
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_one_card, container, false);
        TextView russianTW = (TextView) rootView.findViewById(R.id.russian);
        final EditText rusET = (EditText) rootView.findViewById(R.id.editRus);
        TextView hieroTW = (TextView) rootView.findViewById(R.id.hieroglyph);
        final EditText hieroET = (EditText) rootView.findViewById(R.id.editHiero);
        TextView pinyinTW = (TextView) rootView.findViewById(R.id.pinyin);
        final EditText pinyinET = (EditText) rootView.findViewById(R.id.editPinyin);

        //получаем знаечения из MainActivity
        Log.d("oneCardLog","Id: "+getArguments().getString("ID"));
        final boolean isOnToRepeatCatalog=getArguments().getString("CATALOG").equals("toRepeat");

        final Integer id = Integer.parseInt(getArguments().getString("ID"));
        final String hieroglyph = getArguments().getString("HIERO_ARG");
        final String pinyin = getArguments().getString("PINYIN_ARG").toLowerCase();
        final String pinyin_dig = getArguments().getString("PINYIN_DIG");
        final String russianString = getArguments().getString("RUS_ARG").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "").toString().toLowerCase();
        //.replaceAll("\\s", "")
        final String[] russian = russianString.split(",");
        String str="";
        try {
            str=getArguments().getString("RUS_VAR").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "").toString();
        } catch (NullPointerException e){}
        final String russianVarString = str;
        //.replaceAll("\\s", "")
        final String[] russianVar = russianVarString.split(",");

        //регуируем размер шрифта иероглифов
        if (hieroglyph.length()==1){
            hieroTW.setTextSize(170);
        } else {
            if (hieroglyph.length()==2){
                hieroTW.setTextSize(130);
            } else {
                hieroTW.setTextSize(100);
            }
        }


        //скрыть клавиатуру при клике за пределами поля ввода
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                boolean toRepeat=false;
                //скрыть клавиатуру
                imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                //проверяем значения edit text-ов
                //перевод
                if ((rusET.getVisibility()==View.VISIBLE)
                        &&!("".equals(rusET.getText().toString()))
                ) {
                    Log.d("fragmentLog", "point 1 ="+toRepeat);
                    for (int i = 0; i < russian.length; i++) {
                        if (russian[i].equals(rusET.getText().toString())) {
                            Log.d("fragmentLog", "point 7 "+toRepeat);
                            rusET.setTextColor(Color.GREEN);
                            break;
                        } else {
                            for (int j = 0; j < russianVar.length; j++) {
                                if (russianVar[j].equals(rusET.getText().toString())&&!("".equals(rusET.getText().toString()))) {
                                    Log.d("fragmentLog", "point 8 "+toRepeat);
                                    rusET.setTextColor(Color.GREEN);
                                    break;
                                }
                                else {
                                    rusET.setTextColor(Color.RED);
                                    toRepeat=true;
                                    Log.d("fragmentLog", "point 6 "+toRepeat);
                                    Toast toast = Toast.makeText(getContext(),
                                            getString(R.string.right_answer) + russianString,
                                            Toast.LENGTH_SHORT);
                                    // toast.show();
                                }
                            }

                        }
                    }
                } else {
                    if ((rusET.getVisibility()==View.VISIBLE)&&("".equals(rusET.getText().toString()))&&(isOnToRepeatCatalog)){
                        toRepeat=true;
                    }
                }
                //иероглиф
                //регуируем размер шрифта иероглифов
                if (hieroET.getText().toString().length()==1){
                    hieroET.setTextSize(170);
                } else {
                    if (hieroET.getText().toString().length()==2){
                        hieroET.setTextSize(130);
                    } else {
                        hieroET.setTextSize(100);
                    }
                }
                if ((hieroET.getVisibility()==View.VISIBLE)
                        &&!("".equals(hieroET.getText().toString()))
                ) {
                    Log.d("fragmentLog", "point 2 "+toRepeat);
                    if (hieroglyph.equalsIgnoreCase(hieroET.getText().toString())) {
                        hieroET.setTextColor(Color.GREEN);
                    } else {
                        hieroET.setTextColor(Color.RED);
                        toRepeat=true;
                        Log.d("fragmentLog", "point 5"+toRepeat);
                        Toast toast = Toast.makeText(getContext(),
                                getString(R.string.right_answer) + hieroglyph,
                                Toast.LENGTH_SHORT);
                        //toast.show();
                    }
                } else {
                    if ((hieroET.getVisibility()==View.VISIBLE)
                            &&("".equals(hieroET.getText().toString()))&&isOnToRepeatCatalog){
                        toRepeat=true;
                    }
                }

                //Пиньинь
                if ((pinyinET.getVisibility()==View.VISIBLE)
                        &&!("".equals(pinyinET.getText().toString()))
                ) {
                    Log.d("fragmentLog", "point 3"+toRepeat);
                    if ((pinyin.equalsIgnoreCase(pinyinET.getText().toString())) || (pinyin_dig.equalsIgnoreCase(pinyinET.getText().toString()))) {
                        pinyinET.setTextColor(Color.GREEN);
                    } else {
                        pinyinET.setTextColor(Color.RED);
                        toRepeat=true;
                        Log.d("fragmentLog", "point 4 "+toRepeat);

                        Toast toast = Toast.makeText(getContext(),
                                getString(R.string.right_answer) + pinyin,
                                Toast.LENGTH_SHORT);
                       // toast.show();
                    }
                } else {
                    if ((pinyinET.getVisibility()==View.VISIBLE)
                            &&("".equals(pinyinET.getText().toString()))&&isOnToRepeatCatalog) {
                        toRepeat=true;
                    }
                }

                if (toRepeat){
                    ToRepeatJSONArray.pushItem(id);
                    mCallback.onRepeatCatalogChanged();
                } else {
                    if (isOnToRepeatCatalog) {
                        Log.d("fragmentLog", "to repeat bool="+toRepeat);
                        ToRepeatJSONArray.removeItem(id);
                        mCallback.onRepeatCatalogChanged();
                    }
                }

                return false;
            }
        });

        //если для отображения не выбран ни один элемент
        if (!WhatToShow.getWhatToShow()[0]&&!WhatToShow.getWhatToShow()[1]&&!WhatToShow.getWhatToShow()[2]){
            pinyinTW.setText(R.string.relax);
        }
        else {

            //если выбрано отоюражение иероглифа
            Log.d("fragmentLog", "hiero_try");
            if (WhatToShow.getWhatToShow()[0]) {
                hieroTW.setText(hieroglyph);
                hieroET.setVisibility(View.INVISIBLE);
                Log.d("fragmentLog", "hiero_show");
            } else {
                hieroTW.setVisibility(View.INVISIBLE);
                hieroET.setVisibility(View.VISIBLE);
                //обработчик нажания на кнопку ввод
                //на клавиатуре
                hieroET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            //регуируем размер шрифта иероглифов
                            if (hieroET.getText().toString().length()==1){
                                hieroET.setTextSize(170);
                            } else {
                                if (hieroET.getText().toString().length()==2){
                                    hieroET.setTextSize(130);
                                } else {
                                    hieroET.setTextSize(100);
                                }
                            }
                            if (hieroglyph.equalsIgnoreCase(hieroET.getText().toString()))
                            {
                                hieroET.setTextColor(Color.GREEN);
                            }
                            else {
                                hieroET.setTextColor(Color.RED);
                                ToRepeatJSONArray.pushItem(id);
                                mCallback.onRepeatCatalogChanged();
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                                Toast toast = Toast.makeText(getContext(),
                                        getString(R.string.right_answer)+hieroglyph,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            return true;
                        }
                        return false;
                    }
                });
                Log.d("fragmentLog", "hiero_ hide");
            }


            //если выбрано отображение пиньинь
            if (WhatToShow.getWhatToShow()[1]) {
                pinyinTW.setText(pinyin);
                pinyinET.setVisibility(View.INVISIBLE);
            } else {
                pinyinTW.setVisibility(View.INVISIBLE);
                pinyinET.setVisibility(View.VISIBLE);

                //проверяем значение после нажатия кнопки Ввод
                //на клавиатуре
                pinyinET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if ((pinyin.equalsIgnoreCase(pinyinET.getText().toString()))||(pinyin_dig.equalsIgnoreCase(pinyinET.getText().toString())))
                            {
                                pinyinET.setTextColor(Color.GREEN);
                            }
                            else {
                                pinyinET.setTextColor(Color.RED);
                                ToRepeatJSONArray.pushItem(id);
                                mCallback.onRepeatCatalogChanged();
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                                Toast toast = Toast.makeText(getContext(),
                                        getString(R.string.right_answer)+pinyin,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            return true;
                        }
                        return false;
                    }
                });
            }

            //если выбрано отображение русского перевода
            if (WhatToShow.getWhatToShow()[2]) {
                if (russian.length<2){
                    russianTW.setText(russianString);
                } else {
                    russianTW.setMaxLines(russian.length);
                    String str1="";
                    for (int i=0; i<russian.length-1;i++)
                    {
                        str1+=russian[i]+"\n";
                    }
                    str1+=russian[russian.length-1];
                    russianTW.setText(str1);
                }

                rusET.setVisibility(View.INVISIBLE);
            } else {
                russianTW.setVisibility(View.INVISIBLE);
                rusET.setVisibility(View.VISIBLE);
                rusET.setText("");
                //обработка нажатия Ввода
                //на клавиатуре
                rusET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            Log.d("fragmentLog", "entered russian: "+rusET.getText());
                            Log.d("fragmentLog", "right russian: "+russian);

                            for (int i = 0; i < russian.length; i++) {
                                if (russian[i].equals(rusET.getText().toString())) {
                                    rusET.setTextColor(Color.GREEN);
                                    break;
                                } else {
                                    for (int j = 0; j < russianVar.length; j++) {
                                        if (russianVar[j].equals(rusET.getText().toString())) {
                                            rusET.setTextColor(Color.GREEN);
                                            break;
                                        }
                                        else {
                                            rusET.setTextColor(Color.RED);
                                            ToRepeatJSONArray.pushItem(id);
                                            mCallback.onRepeatCatalogChanged();
                                            Toast toast = Toast.makeText(getContext(),
                                                    getString(R.string.right_answer) + russianString,
                                                    Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    }

                                }
                            }

                            return true;
                        }
                        return false;
                    }
                });
            }
        }

        return rootView;
    }

}
