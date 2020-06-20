package com.weatcher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.weatcher.db.City;
import com.weatcher.db.County;
import com.weatcher.db.Province;
import com.weatcher.util.HttpUtil;
import com.weatcher.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 3;
    public static final int LEVEL_COUNTY = 1;
    private ProgressDialog mProgressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> dataList = new ArrayList<>();

    /**
     * 省列表
     */
    private List<Province> mProvinceList;

    /**
     * 市列表
     */
    private List<City> mCityList;
    /**
     * 县列表
     */
    private List<County> mCountyList;

    /**
     * 选中的省份
     */
    private Province selectedProvince;

    /**
     * 选中的市
     */
    private City selectedCity;

    /**
     * 选中的县
     */
    private int currentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        mListView = view.findViewById(R.id.list_view);
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (currentLevel == LEVEL_PROVINCE) {
//                    selectedProvince = mProvinceList.get(position);
//                    queryCities();
//                } else if (currentLevel == LEVEL_CITY) {
//                    selectedCity = mCityList.get(position);
//                    queryCounties();
//                } else if (currentLevel == LEVEL_COUNTY) {
//                    String weatherId = mCountyList.get(position).getWeatherId();
//                    if (getActivity() instanceof MainActivity) {
//                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
//                        intent.putExtra("weather_id", weatherId);
//                        startActivity(intent);
//                        getActivity().finish();
//                    }
//                }
//            }
//        });
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (currentLevel == LEVEL_COUNTY) {
//                    queryCities();
//                } else if (currentLevel == LEVEL_CITY) {
//                    queryProvinces();
//                }
//            }
//        });
//
//        queryProvinces();
//    }
//
//    private void queryProvinces() {
//        titleText.setText("中国");
//        backButton.setVisibility(View.GONE);
//        mProvinceList = DataSupport.findAll(Province.class);
//        if (mProvinceList.size() > 0) {
//            dataList.clear();
//            for (Province province : mProvinceList) {
//                dataList.add(province.getProvinceName());
//            }
//            mAdapter.notifyDataSetChanged();
//            mListView.setSelection(0);
//            currentLevel = LEVEL_PROVINCE;
//        } else {
//            String address = "http://guolin.tech/api/china";
//            queryFormServer(address, "province");
//        }
//
//    }
//
//    private void queryCities() {
//        titleText.setText(selectedProvince.getProvinceName());
//        backButton.setVisibility(View.VISIBLE);
//        mCityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
//        if (mCityList.size() > 0) {
//            dataList.clear();
//            for (City city : mCityList) {
//                dataList.add(city.getCityName());
//            }
//            mAdapter.notifyDataSetChanged();
//            mListView.setSelection(0);
//            currentLevel = LEVEL_CITY;
//        } else {
//            int provinceCode = selectedProvince.getProvinceCode();
//            String address = "http://guolin.tech/api/china/" + provinceCode;
//            queryFormServer(address, "city");
//        }
//    }
//
//    private void queryCounties() {
//        titleText.setText(selectedCity.getCityName());
//        backButton.setVisibility(View.VISIBLE);
//        mCountyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
//        if (mCountyList.size() > 0) {
//            dataList.clear();
//            for (County county : mCountyList) {
//                dataList.add(county.getCountyName());
//            }
//            mAdapter.notifyDataSetChanged();
//            mListView.setSelection(0);
//            currentLevel = LEVEL_CITY;
//        } else {
//            int provinceCode = selectedProvince.getProvinceCode();
//            int cityCode = selectedCity.getCityCode();
//            String address = "http://guolin.tech/api/china/" +
//                    provinceCode + "/" + cityCode;
//            queryFormServer(address, "county");
//        }
//    }
//
//    private void queryFormServer(String address, final String type) {
//        ShowProgressDialog();
//        HttpUtil.SendOkhttpRequest(address, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                boolean result = false;
//                if ("province".equals(type)) {
//                    result = Utility.handleProvinceResponse(responseText);
//                } else if ("city".equals(type)) {
//                    result = Utility.handCityResponse(responseText, selectedProvince.getId());
//                } else if ("county".equals(type)) {
//                    result = Utility.handCountyResponse(responseText, selectedCity.getId());
//                }
//                if (result) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            closeProgressDialog();
//                            if ("province".equals(type)) {
//                                queryProvinces();
//                            } else if ("city".equals(type)) {
//                                queryCities();
//                            } else if ("county".equals(type)) {
//                                queryCounties();
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeProgressDialog();
//                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//        });
//    }
//
//    private void closeProgressDialog() {
//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
//    }
//
//    private void ShowProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(getActivity());
//            mProgressDialog.setMessage("正在加载中....");
//            mProgressDialog.setCanceledOnTouchOutside(false);
//        }
//        mProgressDialog.show();
//    }
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = mProvinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = mCityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = mCountyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        mProvinceList = DataSupport.findAll(Province.class);
        if (mProvinceList.size() > 0) {
            dataList.clear();
            for (Province province : mProvinceList) {
                dataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        mCityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (mCityList.size() > 0) {
            dataList.clear();
            for (City city : mCityList) {
                dataList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        mCountyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (mCountyList.size() > 0) {
            dataList.clear();
            for (County county : mCountyList) {
                dataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据。
     */
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.SendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handCountyResponse(responseText, selectedCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // 通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}