package com.example.java.weatherapps.Gson_Test;


import com.google.gson.annotations.SerializedName;
/**
 * Created by 59575 on 2017/5/23.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }

}
