package com.internet_of_everything.chineesecards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;import org.json.JSONException;
import java.util.ArrayList;

//главная активность
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OneCardFragment.OnRepeatCatalogChangedListener {

     /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mAllPagerAdapter;
    private PagerAdapter mHsk1PagerAdapter;
    private PagerAdapter mHsk2PagerAdapter;
    private PagerAdapter mNoHskPagerAdapter;
    private PagerAdapter mToRepeatPagerAdapter;
    public static MainActivity context=null;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private Toolbar myToolbar;

    public void onRepeatCatalogChanged(){
        mToRepeatPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause () {
        Log.d("toRepeatLog","onDestroy from activity");
        ToRepeatJSONArray.writeToFile();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.left_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_folder_open_white_36dp);
        myToolbar.setNavigationIcon(drawable);
        myToolbar.setTitle(R.string.title_all);
        setSupportActionBar(myToolbar);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon

        drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                myToolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(drawerToggle);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mAllPagerAdapter = new AllCardsAdapter(getSupportFragmentManager());
        mHsk1PagerAdapter=new Hsk1CardsAdapter(getSupportFragmentManager());
        mHsk2PagerAdapter=new Hsk2CardsAdapter(getSupportFragmentManager());
        mNoHskPagerAdapter=new NoHskCardsAdapter(getSupportFragmentManager());
        mToRepeatPagerAdapter=new ToRepeatCardsAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAllPagerAdapter);



        ImageButton backButton = (ImageButton)findViewById(R.id.button_back);
        if (mPager.getCurrentItem()==0) {
            backButton.setVisibility(android.view.View.INVISIBLE);
        } else {
            backButton.setVisibility(android.view.View.VISIBLE);
        }
        mPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
                ImageButton backButton = (ImageButton)findViewById(R.id.button_back);
                ImageButton nextButton = (ImageButton)findViewById(R.id.button_next);
                PagerAdapter pagerAdapter=mPager.getAdapter();

                if (mPager.getCurrentItem()==0) {
                    backButton.setVisibility(android.view.View.INVISIBLE);
                } else {
                    backButton.setVisibility(android.view.View.VISIBLE);
                }
                if (mPager.getCurrentItem()==mPager.getAdapter().getCount()-1) {
                    nextButton.setVisibility(android.view.View.INVISIBLE);
                } else {
                    nextButton.setVisibility(android.view.View.VISIBLE);
                }
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                ImageButton backButton = (ImageButton)findViewById(R.id.button_back);
                ImageButton nextButton = (ImageButton)findViewById(R.id.button_next);
                PagerAdapter pagerAdapter=mPager.getAdapter();
                if (mPager.getCurrentItem()==0) {
                    backButton.setVisibility(android.view.View.INVISIBLE);
                } else {
                    backButton.setVisibility(android.view.View.VISIBLE);
                }
                if (mPager.getCurrentItem()==mPager.getAdapter().getCount()-1) {
                    nextButton.setVisibility(android.view.View.INVISIBLE);
                } else {
                    nextButton.setVisibility(android.view.View.VISIBLE);
                }
            }

        });

        ImageButton nextButton = (ImageButton)findViewById(R.id.button_next);

        backButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem()-1, true);

            }
        });



        nextButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem()+1, true);

            }
        });
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
                                    mAllPagerAdapter.notifyDataSetChanged();
                                    mHsk1PagerAdapter.notifyDataSetChanged();
                                    mHsk2PagerAdapter.notifyDataSetChanged();
                                    mNoHskPagerAdapter.notifyDataSetChanged();
                                    mToRepeatPagerAdapter.notifyDataSetChanged();
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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
    private class AllCardsAdapter extends FragmentStatePagerAdapter {
        public AllCardsAdapter(FragmentManager fm) {
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
                bdl.putString("ID", MainJSONArray.getMyJSONobj(position).getString("id"));
                bdl.putString("CATALOG", "all");
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

    private class Hsk1CardsAdapter extends FragmentStatePagerAdapter {
        public Hsk1CardsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            OneCardFragment f = new OneCardFragment();
            Bundle bdl = new Bundle(5);

            //передаем все необходимые поля:
            //иероглиф, пиньинь и перевод
            try{
                bdl.putString("HIERO_ARG", HSK1JSONArray.getMyHSK1JSONobj(position).getString("hieroglyph"));
                bdl.putString("PINYIN_ARG", HSK1JSONArray.getMyHSK1JSONobj(position).getString("pinyin"));
                bdl.putString("PINYIN_DIG", HSK1JSONArray.getMyHSK1JSONobj(position).getString("pinyin_num"));
                bdl.putString("RUS_ARG", HSK1JSONArray.getMyHSK1JSONobj(position).getString("russian"));
                bdl.putString("RUS_VAR", HSK1JSONArray.getMyHSK1JSONobj(position).getString("russian_var"));
                bdl.putString("ID", HSK1JSONArray.getMyHSK1JSONobj(position).getString("id"));
                bdl.putString("CATALOG", "hsk1");
            }
            catch (JSONException e){}
            f.setArguments(bdl);
            return f;
        }

        @Override
        public int getCount() {
            return HSK1JSONArray.getNumPages();
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }


    }

    private class Hsk2CardsAdapter extends FragmentStatePagerAdapter {
        public Hsk2CardsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            OneCardFragment f = new OneCardFragment();
            Bundle bdl = new Bundle(5);

            //передаем все необходимые поля:
            //иероглиф, пиньинь и перевод
            try{
                bdl.putString("HIERO_ARG", HSK2JSONArray.getMyHSK2JSONobj(position).getString("hieroglyph"));
                bdl.putString("PINYIN_ARG", HSK2JSONArray.getMyHSK2JSONobj(position).getString("pinyin"));
                bdl.putString("PINYIN_DIG", HSK2JSONArray.getMyHSK2JSONobj(position).getString("pinyin_num"));
                bdl.putString("RUS_ARG", HSK2JSONArray.getMyHSK2JSONobj(position).getString("russian"));
                bdl.putString("RUS_VAR", HSK2JSONArray.getMyHSK2JSONobj(position).getString("russian_var"));
                bdl.putString("ID", HSK2JSONArray.getMyHSK2JSONobj(position).getString("id"));
                bdl.putString("CATALOG", "hsk2");
            }
            catch (JSONException e){}
            f.setArguments(bdl);
            return f;
        }

        @Override
        public int getCount() {
            return HSK2JSONArray.getNumPages();
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }


    }

    private class NoHskCardsAdapter extends FragmentStatePagerAdapter {
        public NoHskCardsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            OneCardFragment f = new OneCardFragment();
            Bundle bdl = new Bundle(5);

            //передаем все необходимые поля:
            //иероглиф, пиньинь и перевод
            try {
                bdl.putString("HIERO_ARG", NoHSKJSONArray.getMyNoHSKJSONobj(position).getString("hieroglyph"));
                bdl.putString("PINYIN_ARG", NoHSKJSONArray.getMyNoHSKJSONobj(position).getString("pinyin"));
                bdl.putString("PINYIN_DIG", NoHSKJSONArray.getMyNoHSKJSONobj(position).getString("pinyin_num"));
                bdl.putString("RUS_ARG", NoHSKJSONArray.getMyNoHSKJSONobj(position).getString("russian"));
                bdl.putString("RUS_VAR", NoHSKJSONArray.getMyNoHSKJSONobj(position).getString("russian_var"));
                bdl.putString("ID", NoHSKJSONArray.getMyNoHSKJSONobj(position).getString("id"));
                bdl.putString("CATALOG", "noHsk");
            } catch (JSONException e) {
            }
            f.setArguments(bdl);
            return f;

    }



        @Override
        public int getCount() {
            return NoHSKJSONArray.getNumPages();
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }


    }

    private class ToRepeatCardsAdapter extends FragmentStatePagerAdapter {
        public ToRepeatCardsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            OneCardFragment f = new OneCardFragment();
            Bundle bdl = new Bundle(5);

            //передаем все необходимые поля:
            //иероглиф, пиньинь и перевод
            try {
                bdl.putString("HIERO_ARG", ToRepeatJSONArray.getMyToRepeatJSONobj(position).getString("hieroglyph"));
                bdl.putString("PINYIN_ARG", ToRepeatJSONArray.getMyToRepeatJSONobj(position).getString("pinyin"));
                bdl.putString("PINYIN_DIG", ToRepeatJSONArray.getMyToRepeatJSONobj(position).getString("pinyin_num"));
                bdl.putString("RUS_ARG", ToRepeatJSONArray.getMyToRepeatJSONobj(position).getString("russian"));
                bdl.putString("RUS_VAR", ToRepeatJSONArray.getMyToRepeatJSONobj(position).getString("russian_var"));
                bdl.putString("ID", ToRepeatJSONArray.getMyToRepeatJSONobj(position).getString("id"));
                bdl.putString("CATALOG", "toRepeat");
            } catch (JSONException e) {
            }
            f.setArguments(bdl);
            return f;

        }



        @Override
        public int getCount() {
            return ToRepeatJSONArray.getNumPages();
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.hsk1) {
            ArrayList<Fragment> fragments=new ArrayList<Fragment>();
            for(int i=0; i<fragments.size(); i++)
                getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commit();
            fragments.clear();
            mPager.setAdapter(mHsk1PagerAdapter);
            myToolbar.setTitle(R.string.title_hsk1);
        } else if (id == R.id.hsk2) {
            ArrayList<Fragment> fragments=new ArrayList<Fragment>();
            for(int i=0; i<fragments.size(); i++)
                getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commit();
            fragments.clear();
            mPager.setAdapter(mHsk2PagerAdapter);
            myToolbar.setTitle(R.string.title_hsk2);
        } else if (id == R.id.no_hsk) {
            ArrayList<Fragment> fragments=new ArrayList<Fragment>();
            for(int i=0; i<fragments.size(); i++)
                getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commit();
            fragments.clear();
            mPager.setAdapter(mNoHskPagerAdapter);
            myToolbar.setTitle(R.string.title_no_hsk);
        } else if (id == R.id.to_repeat) {
            ArrayList<Fragment> fragments=new ArrayList<Fragment>();
            for(int i=0; i<fragments.size(); i++)
                getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commit();
            fragments.clear();
            mPager.setAdapter(mToRepeatPagerAdapter);
            myToolbar.setTitle(R.string.title_to_repeat);
        } else if (id == R.id.all) {
            ArrayList<Fragment> fragments=new ArrayList<Fragment>();
            for(int i=0; i<fragments.size(); i++)
                getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commit();
            fragments.clear();
            mPager.setAdapter(mAllPagerAdapter);
            myToolbar.setTitle(R.string.title_all);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}
