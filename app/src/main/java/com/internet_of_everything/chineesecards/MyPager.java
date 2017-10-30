package com.internet_of_everything.chineesecards;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by olga1 on 30.10.2017.
 */

public class MyPager extends ViewPager {
    public MyPager(Context context) {
        super(context);
    }

    public MyPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(inNeutralArea(ev.getX(),ev.getY())){
            //--events re-directed to this ViewPager's onTouch() and to its child views from there--
            return false;
        }else {
            //--events intercepted by this ViewPager's default implementation, where it looks for swipe gestures--
            return super.onInterceptTouchEvent(ev);
        }
    }

    private boolean inNeutralArea(float x, float y) {
        //--check if x,y inside non reactive area, return true/false accordingly--
        return false;
    }

}