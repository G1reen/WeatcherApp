package com.weatcher.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.weatcher.db.City;
import com.weatcher.db.County;
import com.weatcher.db.Province;
import com.weatcher.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /**
     * 解析处理服务器返回的数据
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProbince = new JSONArray(response);
                for (int i = 0; i < allProbince.length(); i++) {
                    JSONObject ProvinceObject = allProbince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(ProvinceObject.getString("name"));
                    province.setProvinceCode(ProvinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject ProvinceObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(ProvinceObject.getString("name"));
                    city.setCityCode(ProvinceObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject ProvinceObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(ProvinceObject.getString("name"));
                    county.setWeatherId(ProvinceObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * JSON数据解析成weather实体类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
