package com.lyc.qweather.util;

import android.text.TextUtils;

import com.lyc.qweather.db.City;
import com.lyc.qweather.db.County;
import com.lyc.qweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wayne on 2016/12/27.
 */

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     *
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObj = allProvinces.getJSONObject(i);
                    Province p = new Province();
                    p.setProvinceName(provinceObj.getString("name"));
                    p.setProvinceCode(provinceObj.getInt("id"));
                    p.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     *
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObj = allCities.getJSONObject(i);
                    City c = new City();
                    c.setCityName(cityObj.getString("name"));
                    c.setCityCode(cityObj.getInt("id"));
                    c.setProvinceId(provinceId);
                    c.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理吴福气返回的县级数据
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                for(int i =0;i<allCounties.length();i++){
                    JSONObject countyObj = allCounties.getJSONObject(i);
                    County c = new County();
                    c.setCountyName(countyObj.getString("name"));
                    c.setWeatherId(countyObj.getString("weather_id"));
                    c.setCityId(cityId);
                    c.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
