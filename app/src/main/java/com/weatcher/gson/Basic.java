package com.weatcher.gson;

import com.google.gson.annotations.SerializedName;

    //Json不太适合用java字段来命名
    //Json 字段和Java字段之间建立映射关系
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

