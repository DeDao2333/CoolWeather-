package com.example.java.weatherapps.CoolWeather.db;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.java.weatherapps.CoolWeather.db.fragment.ItemFragment;
import com.example.java.weatherapps.CoolWeather.db.fragment.dummy.DummyContent;
import com.example.java.weatherapps.CoolWeather.db.util.HttpUtil;
import com.example.java.weatherapps.CoolWeather.db.util.Utility;
import com.example.java.weatherapps.Gson_Test.Weather;
import com.example.java.weatherapps.R;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoolWActivity extends AppCompatActivity implements
        ItemFragment.OnListFragmentInteractionListener {

    private static int current_page;
    private static int state_notify;

    private static final int PRE=0;
    private static final int PROVINCE=1;
    private static final int CITY=2;
    private static final int COUNTY=3;

    private String selectedProvince;
    private String selectedCity;
    private String selectedCounty;

    private static final String KEY = "00fedcca12e747d498468fa61a8ba3ec";
    private static final String PreAddress = "http://guolin.tech/api/china";
    private static List<String> LaterAddress = new ArrayList<>();

    @Override
    public void onBackPressed() {
        LaterAddress.remove(LaterAddress.size()-1);
        if (current_page == PROVINCE) {
            super.onBackPressed();
        } else if (current_page == CITY) {
            Log.d("wer", "back 1: "+selectedProvince);
            current_page=PRE;
            queryFromData(null);
        } else if (current_page == COUNTY) {
            Log.d("wer", "back 2");
            current_page = PROVINCE;
            queryFromData(selectedProvince);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cool_w);

        String s = "{\"HeWeather5\":[{\"aqi\":{\"city\":{\"aqi\":\"65\",\"co\":\"0\",\"no2\":\"33\",\"o3\":\"108\",\"pm10\":\"79\",\"pm25\":\"17\",\"qlty\":\"良\",\"so2\":\"3\"}},\"basic\":{\"city\":\"北京\",\"cnty\":\"中国\",\"id\":\"CN101010100\",\"lat\":\"39.90498734\",\"lon\":\"116.40528870\",\"update\":{\"loc\":\"2017-05-24 21:52\",\"utc\":\"2017-05-24 13:52\"}},\"daily_forecast\":[{\"astro\":{\"mr\":\"03:55\",\"ms\":\"17:32\",\"sr\":\"04:53\",\"ss\":\"19:29\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"101\",\"txt_d\":\"晴\",\"txt_n\":\"多云\"},\"date\":\"2017-05-24\",\"hum\":\"31\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1009\",\"tmp\":{\"max\":\"33\",\"min\":\"17\"},\"uv\":\"8\",\"vis\":\"16\",\"wind\":{\"deg\":\"295\",\"dir\":\"西南风\",\"sc\":\"3-4\",\"spd\":\"17\"}},{\"astro\":{\"mr\":\"04:36\",\"ms\":\"18:45\",\"sr\":\"04:53\",\"ss\":\"19:30\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"晴\",\"txt_n\":\"晴\"},\"date\":\"2017-05-25\",\"hum\":\"40\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1010\",\"tmp\":{\"max\":\"30\",\"min\":\"15\"},\"uv\":\"8\",\"vis\":\"20\",\"wind\":{\"deg\":\"211\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"6\"}},{\"astro\":{\"mr\":\"05:23\",\"ms\":\"19:57\",\"sr\":\"04:52\",\"ss\":\"19:31\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"晴\",\"txt_n\":\"晴\"},\"date\":\"2017-05-26\",\"hum\":\"48\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1008\",\"tmp\":{\"max\":\"31\",\"min\":\"18\"},\"uv\":\"8\",\"vis\":\"20\",\"wind\":{\"deg\":\"148\",\"dir\":\"北风\",\"sc\":\"微风\",\"spd\":\"8\"}}],\"hourly_forecast\":[{\"cond\":{\"code\":\"100\",\"txt\":\"晴\"},\"date\":\"2017-05-24 22:00\",\"hum\":\"35\",\"pop\":\"0\",\"pres\":\"1006\",\"tmp\":\"27\",\"wind\":{\"deg\":\"302\",\"dir\":\"西北风\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"now\":{\"cond\":{\"code\":\"101\",\"txt\":\"多云\"},\"fl\":\"27\",\"hum\":\"26\",\"pcpn\":\"0\",\"pres\":\"1005\",\"tmp\":\"26\",\"vis\":\"8\",\"wind\":{\"deg\":\"230\",\"dir\":\"西南风\",\"sc\":\"4-5\",\"spd\":\"26\"}},\"status\":\"ok\",\"suggestion\":{\"air\":{\"brf\":\"很差\",\"txt\":\"气象条件不利于空气污染物稀释、扩散和清除，请尽量避免在室外长时间活动。\"},\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"炎热\",\"txt\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较不宜\",\"txt\":\"天气较好，但风力较强，在户外要选择合适的运动，另外考虑到天气炎热，建议停止高强度运动。\"},\"trav\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，较热，但风稍大，能缓解较热的天气。较适宜旅游，您仍可陶醉于大自然的美丽风光中。\"},\"uv\":{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"}}}]}";
        Weather weather=Utility.handWeatherResponse(s);
        Log.d("wer", weather.status);
        Log.d("wer", weather.basic.cityName + weather.basic.update.updateTime);
        init();

    }

    /**
     * 欠缺显式问题
     */
    public void init() {
        Fragment f = ItemFragment.newInstance(1);
        FragmentManager m = getSupportFragmentManager();
        FragmentTransaction t = m.beginTransaction();
        t.replace(R.id.frameLayout, f);
        t.commit();

        state_notify=0;
        current_page = PRE;
        if (queryFromData(null)) {
        } else {
            queryFromServe(PreAddress,null);
        }
    }

    @Override
    public void onListFragmentInteraction(final DummyContent.DummyItem item) {
        if (current_page == PROVINCE) {
            selectedProvince = item.id;
        } else if (current_page == CITY) {
            selectedCity = item.id;
        } else if (current_page == COUNTY) {
            selectedCounty = item.id;
        }

        LaterAddress.add("/"+item.id);

        if (queryFromData(item.id)) {
            Log.d("wer", "from data");
        } else {
            Log.d("wer", "from serve ");
            queryFromServe(PreAddress+ Utility.changeListIntoString(LaterAddress),item);
        }
    }


    /**
     * 从服务器中查找数据,赋值给数据库
     * @param address   地址
     * @param item  点击的项目
     */
    public void queryFromServe(String address, final DummyContent.DummyItem item) {
        Log.d("wer", "address: " + address);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("wer", "failure in send okhttp");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (current_page == PRE) {
                    Utility.handleProvinceResponse(response.body().string());
                } else if (current_page == PROVINCE) {
                    Utility.handleCityResponse(response.body().string(), item.id);
                } else if (current_page == CITY) {
                    Utility.handleCountyResponse(response.body().string(), item.id);
                } else {
                    //当前为城镇页面，点击可以进行显式天气状况显式的代码

                }
                Log.d("wer", "4");
                queryFromData(item.id);    //到数据库中去读取数据刷新列表
            }
        });
    }

    /**
     * 从数据库中查找数据
     * @return  是否查找成功
     */
    public boolean queryFromData(String itemId) {
        state_notify++;
        if (current_page == PRE) {
            List<Province> p = DataSupport.findAll(Province.class);
            if (p.size() > 0) {
                //对list进行赋值的代码
                DummyContent.initProvinceList(p);
                if (state_notify > 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ItemFragment.adapter.notifyDataSetChanged();
                        }
                    });
                }
                current_page = PROVINCE;
                return true;
            }
        } else if (current_page == PROVINCE) {
            List<City> c = DataSupport.where("provinceId=?", itemId).find(City.class);
            if (c.size() > 0) {
                //对list进行赋值的代码
                Log.d("wer", "" + c.size());
                DummyContent.initCityList(c);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ItemFragment.adapter.notifyDataSetChanged();
                        current_page = CITY;
                    }
                });
                return true;
            }
        } else if (current_page == CITY) {
            List<County> ct = DataSupport.where("cityId=?", itemId).find(County.class);
            if (ct.size() > 0) {
                //对list进行赋值的代码
                DummyContent.initCountyList(ct);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ItemFragment.adapter.notifyDataSetChanged();
                        current_page = COUNTY;
                    }
                });
                return true;
            }
        } else if (current_page == COUNTY) {
            //进行天气查询的代码，从数据库中提取；
        }
        return false;
    }


}
