package com.weatherapp.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapUtils {

    public static Map<String, Object> map(Pair<String, ?>... pairs) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Pair<String, ?> pair : pairs) {
            map.put(pair.getItem1(), pair.getItem2());
        }
        return map;
    }

    public static String serialize(Pair<String, ?>... pairs) {
        return new Gson().toJson(map(pairs));
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Map<String, ?> map, String... nestedKeys) {
        Map<String, ?> tmp = map;
        for (int i = 0; i < nestedKeys.length; i++) {
            String key = nestedKeys[i];
            if (tmp.containsKey(key)) {
                if (i == nestedKeys.length - 1) {
                    return (T) tmp.get(key);
                }
                if (tmp.get(key) instanceof Map) {
                    tmp = (Map<String, Object>) tmp.get(key);
                } else return null;
            } else return null;
        }
        return null;
    }
}
