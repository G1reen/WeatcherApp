//package com.weatcher;
//
//import androidx.appcompat.app.AppCompatActivity;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.weatcher.gson.Forecast;
//import com.weatcher.gson.Weather;
//import com.weatcher.util.HttpUtil;
//import com.weatcher.util.Utility;
//
//import java.io.IOException;
//
//public class WeatherActivity extends AppCompatActivity {
//    private ScrollView weatherLayout;
//    private TextView titleCity;
//    private TextView titleUpdateTime;
//    private TextView degreeText;
//    private TextView weatherInfoText;
//    private LinearLayout forecastLayout;
//    private TextView aqiText;
//    private TextView pm25Text;
//    private TextView comfortText;
//    private TextView carWashText;
//    private TextView sportText;
//    private String mWeatherId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather);
//        weatherLayout = findViewById(R.id.weather_layout);
//        titleCity = findViewById(R.id.title_city);
//        titleUpdateTime = findViewById(R.id.title_update_time);
//        degreeText = findViewById(R.id.degree_text);
//        weatherInfoText = findViewById(R.id.weather_info_text);
//        forecastLayout = findViewById(R.id.forecast_layout);
//        aqiText = findViewById(R.id.aqi_text);
//        pm25Text = findViewById(R.id.pm25_text);
//        comfortText = findViewById(R.id.comfort_text);
//        carWashText = findViewById(R.id.car_wash_text);
//        sportText = findViewById(R.id.sport_text);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String weatherString = prefs.getString("weather", null);
//        if (weatherString != null) {
//            Weather weather = Utility.handleWeatherResponse(weatherString);
//            showWeatherInfo(weather);
//        } else {
////            mWeatherId = getIntent().getStringExtra("CN101190401");
//            mWeatherId = "CN101190401";
//            weatherLayout.setVisibility(View.INVISIBLE);
//            requestWeather(mWeatherId);
//        }
//    }
//
//    private void requestWeather(final String weatherId) {
//        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
//        HttpUtil.SendOkhttpRequest(weatherUrl, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String responseText = response.body().string();
//                final Weather weather = Utility.handleWeatherResponse(responseText);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (weather != null && "ok".equals(weather.status)) {
//                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
//                            editor.putString("weather", responseText);
//                            editor.apply();
////                            mWeatherId = weather.mBasic.weatherId;
//                            Log.d("数值：", mWeatherId);
//                            showWeatherInfo(weather);
//                        } else {
//                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
//
//    private void showWeatherInfo(Weather weather) {
//        String cityName = weather.mBasic.cityName;
//        String updateTime = weather.mBasic.mUpdate.updateTime.split(" ")[1];
//        String degree = weather.mNow.temperature + "℃";
//        String weatherInfo = weather.mNow.mMore.info;
//        titleCity.setText(cityName);
//        titleUpdateTime.setText(updateTime);
//        degreeText.setText(degree);
//        weatherInfoText.setText(weatherInfo);
//        forecastLayout.removeAllViews();
//        for (Forecast forecast : weather.mForecasts) {
//            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
//            TextView dateText = (TextView) view.findViewById(R.id.date_text);
//            TextView infoText = (TextView) view.findViewById(R.id.info_text);
//            TextView maxText = (TextView) view.findViewById(R.id.max_text);
//            TextView minText = (TextView) view.findViewById(R.id.min_text);
//            dateText.setText(forecast.date);
//            infoText.setText(forecast.mMore.info);
//            maxText.setText(forecast.mTemperature.max);
//            minText.setText(forecast.mTemperature.min);
//            forecastLayout.addView(view);
//        }
//        if (weather.mAQI != null) {
//            aqiText.setText(weather.mAQI.mAQICity.aqi);
//            pm25Text.setText(weather.mAQI.mAQICity.pm25);
//        }
//        String comfort = "舒适度：" + weather.mSuggestion.mComfort.info;
//        String carWash = "洗车指数：" + weather.mSuggestion.mCarWash.info;
//        String sport = "运动建议：" + weather.mSuggestion.mSport.info;
//        comfortText.setText(comfort);
//        carWashText.setText(carWash);
//        sportText.setText(sport);
//        weatherLayout.setVisibility(View.VISIBLE);
//    }
//}
package com.weatcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.weatcher.gson.Forecast;
import com.weatcher.gson.Weather;
import com.weatcher.util.HttpUtil;
import com.weatcher.util.Utility;


import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {


    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;

    public String mWeatherId;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    public DrawerLayout mDrawerLayout;

    private Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);
        // 初始化各控件
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navButton = findViewById(R.id.nav_button);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        bingPicImg = findViewById(R.id.bing_pic_img);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });


        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);

        } else {
            loadBingPic();
        }
    }


    /**
     * 根据天气id请求城市天气信息。
     */
    public void requestWeather(String weatherId) {

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId;
        HttpUtil.SendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 处理并展示Weather实体类中的数据。
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.SendOkHttpRequest(requestBingPic, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

}
