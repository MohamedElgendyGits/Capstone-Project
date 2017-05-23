package com.instanews.utils;

import com.google.gson.Gson;

/**
 * Created by Mohamed Elgendy on 22/5/2017.
 */

public class JsonUtils {

    public static <T> T convertJsonStringToObject(String jsonString, Class<T> clazz) {

        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> String convertObjectToJsonString(T Object, Class<T> clazz) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(Object, clazz);
        return jsonString;
    }
}
