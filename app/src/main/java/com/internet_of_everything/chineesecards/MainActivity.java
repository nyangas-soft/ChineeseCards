package com.internet_of_everything.chineesecards;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

//главная активность
public class MainActivity extends AppCompatActivity {

     /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    public static MainActivity context=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new OneCardAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //обработка нажатия на кнопку "редактировать" в статус-баре
        if (id == R.id.settings) {
            final boolean[] mCheckedItems = { WhatToShow.getWhatToShow()[0], WhatToShow.getWhatToShow()[1], WhatToShow.getWhatToShow()[2] };
            final String[] checkWhatToShow =
                    { getResources().getString(R.string.show_hiero), getResources().getString(R.string.show_pinyin), getResources().getString(R.string.show_rus) };
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.dialog_title)
                    .setMultiChoiceItems(checkWhatToShow, mCheckedItems,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which, boolean isChecked) {
                                    mCheckedItems[which] = isChecked;
                                }
                            })
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //
                                    WhatToShow.setWhatToShow(mCheckedItems[0],mCheckedItems[1],mCheckedItems[2]);
                                    mPagerAdapter.notifyDataSetChanged();
                                    Log.d("mainActLog",String.valueOf(mCheckedItems[0])+String.valueOf(mCheckedItems[1])+String.valueOf(mCheckedItems[2]));
                                    Log.d("mainActLog",String.valueOf(WhatToShow.getWhatToShow()[0])+
                                            String.valueOf(WhatToShow.getWhatToShow()[1])+String.valueOf(WhatToShow.getWhatToShow()[2]));
                                }
                            });


            //показываем модальное окно с выбором элементов,
            //которые нужно показать на экране
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }


    //обработка пролистывания назад
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            Log.d("mainActLog","first element");
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            Log.d("mainActLog","back");
        }
    }

    //страница карточки
    private class OneCardAdapter extends FragmentStatePagerAdapter {
        public OneCardAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            OneCardFragment f = new OneCardFragment();
            Bundle bdl = new Bundle(5);

            //передаем все необходимые поля:
            //иероглиф, пиньинь и перевод
            try{
                bdl.putString("HIERO_ARG", MainJSONArray.getMyJSONobj(position).getString("hieroglyph"));
                bdl.putString("PINYIN_ARG", MainJSONArray.getMyJSONobj(position).getString("pinyin"));
                bdl.putString("PINYIN_DIG", MainJSONArray.getMyJSONobj(position).getString("pinyin_num"));
                bdl.putString("RUS_ARG", MainJSONArray.getMyJSONobj(position).getString("russian"));
                bdl.putString("RUS_VAR", MainJSONArray.getMyJSONobj(position).getString("russian_var"));
            }
            catch (JSONException e){}
            f.setArguments(bdl);
            return f;
        }

        @Override
        public int getCount() {
             return MainJSONArray.getNumPages();
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }
    }

}
