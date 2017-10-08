package com.internet_of_everything.chineesecards;

/**
 * Created by olga1 on 28.09.2017.
 * Класс-синглетон
 * хранит информацию о том, какие элементы карточки выводить на экран:
 * иероглиф, пиньинь или перевод
 */

class WhatToShow {

    //массив, который показывает, какие элементы отображать
    //0 - иероглиф, 1-пиньинь, 2 - русский
    private static boolean[] whatToShow=new boolean[3];

    //установить порядок отображения
    public static void setWhatToShow(boolean hiero, boolean pinyin, boolean rus){
        whatToShow[0]=hiero;
        whatToShow[1]=pinyin;
        whatToShow[2]=rus;
    }

    //получить информацию о том,
    //какие элементы необходимо отобразить
    public static boolean[] getWhatToShow(){
        return whatToShow;
    }
    private static final WhatToShow ourInstance = new WhatToShow();

    static WhatToShow getInstance() {
        return ourInstance;
    }

    //конструктор, по умолчанию отображаются
    //все элементы
    private WhatToShow() {
        setWhatToShow(true,true,true);
    }
}
