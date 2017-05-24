package com.example.java.weatherapps.Gson_Test;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 59575 on 2017/5/23.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }

}

