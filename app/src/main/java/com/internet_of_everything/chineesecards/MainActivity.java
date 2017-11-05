package com.internet_of_everything.chineesecards;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONException;

//главная активность
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

       /* if(getWindow().getDecorView().getLayoutDirection()==View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }*/

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.left_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_folder_open_white_36dp);
        myToolbar.setNavigationIcon(drawable);
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
        mPagerAdapter = new OneCardAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        ImageButton backButton = (ImageButton)findViewById(R.id.button_back);
        if (mPager.getCurrentItem()==0) {
            backButton.setVisibility(android.view.View.INVISIBLE);
        } else {
            backButton.setVisibility(android.view.View.VISIBLE);
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.hsk1) {
            Toast.makeText(getApplicationContext(), "hsk 1", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.hsk2) {

        } else if (id == R.id.no_hsk) {

        } else if (id == R.id.to_repeat) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
