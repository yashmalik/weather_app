package com.weatherapp.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pair<T1, T2> {

    private T1 item1;
    private T2 item2;

    public Pair(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }

    public static <T1, T2> Pair<T1, T2> pair(T1 t1, T2 t2) {
        return new Pair<T1, T2>(t1, t2);
    }
}
