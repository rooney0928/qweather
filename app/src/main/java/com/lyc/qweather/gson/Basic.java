package com.lyc.qweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wayne on 2016/12/28.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;


    public class Update{

        @SerializedName("loc")
        public String updateTime;
    }
}
