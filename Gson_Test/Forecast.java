package com.example.java.weatherapps.Gson_Test;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 59575 on 2017/5/23.
 */

public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Temperature mTemperature;

    @SerializedName("cond")
    public More mMore;

    public class More {
        @SerializedName("txt_d")
        public String info;
    }

    public class Temperature {
        public String max;
        public String min;
    }


}
