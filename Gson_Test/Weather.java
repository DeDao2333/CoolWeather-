package com.example.java.weatherapps.Gson_Test;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 59575 on 2017/5/23.
 */

public class Weather {
    public String status;
    public Basic basic;
    public Aqi aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> mForecastList;

}
