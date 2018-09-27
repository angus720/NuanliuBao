package nuanliu.com.modao.ui.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.RegionBean;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.RegionAdapter;
import nuanliu.com.modao.utils.PopupwindowUtils;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.utils.StringUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 添加报修地址
 */

public class AddRepairAddressActivity extends BaseActivity {

    @BindView(R.id.tv_right)
    TextView tvCityName;
    @BindView(R.id.rl_choose_region)
    RelativeLayout rlChooseRegion;
    @BindView(R.id.tv_region)
    TextView tvRegion;
    @BindView(R.id.et_community)
    EditText etCommunity;
    @BindView(R.id.et_building)
    EditText etBuilding;
    @BindView(R.id.et_unit)
    EditText etUnit;
    @BindView(R.id.et_house)
    EditText etHouse;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private PopupWindow mPopupWindow;
    private List<RegionBean.BodyBean> lists = new ArrayList();
    private ListView community_list;
    private RegionAdapter mAdapter;
    private LinearLayout llBlank;

    private String cityName;
    private String provinceName;
    private String cityCode;
    private List<HotCity> hotCities = new ArrayList<>();
    private String region;
    private String areaCode;
    private String community;
    private String building;
    private String unit;
    private String house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initPop();
        setHotCity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_repair_address;
    }

    private void initView() {
        getToolBar().setTitle("添加地址");

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void setHotCity() {
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
    }


    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    Log.e("位置：", aMapLocation.getAddress());
                    cityName = aMapLocation.getCity() + "";
                    cityName = cityName.replace("市", "");
                    provinceName = aMapLocation.getProvince() + "";
                    cityCode = aMapLocation.getCityCode() + "";
                    tvCityName.setText(cityName);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }

    private void getAreaByCity(String cityName) {
        ServiceFactory
                .getApis()
                .getAreaByCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<RegionBean>bindToLifecycle())
                .subscribe(new Subscriber<RegionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RegionBean regionBean) {
                        if (regionBean.getStatus() == 1) {
                            refreshViewByData(regionBean.getBody());
                            if (mPopupWindow.isShowing()) {
                                mPopupWindow.dismiss();
                            } else {
                                PopupwindowUtils.showAsDropDown(rlChooseRegion, mPopupWindow);
                            }
                        }
                    }
                });
    }

    private void refreshViewByData(List<RegionBean.BodyBean> list) {
        lists.clear();
        for (int i = 0; i < list.size(); i++) {
            RegionBean.BodyBean datasBean = list.get(i);
            lists.add(new RegionBean.BodyBean(datasBean.getName(), datasBean.getId()));
        }
    }

    private void findHouseByAddr(String areaCode, String username, String communityName, String buildingNumber, String unitNumber, String houseNumber) {
        ServiceFactory
                .getApis()
                .findHouseByAddr(areaCode, username, communityName, buildingNumber, unitNumber, houseNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatus() == 1) {
                            Toast.makeText(AddRepairAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            if (baseResponse.getStatus_name().contains("房屋不存在")) {
                                Toast.makeText(AddRepairAddressActivity.this, "没有查到符合条件的地址", Toast.LENGTH_SHORT).show();
                            } else if (baseResponse.getStatus_name().contains("用户不存在")) {
                                Toast.makeText(AddRepairAddressActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                            } else if (baseResponse.getStatus_name().contains("房屋已绑定")) {
                                Toast.makeText(AddRepairAddressActivity.this, "房屋已绑定", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.rl_choose_region, R.id.tll_right, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_choose_region:
                getAreaByCity(tvCityName.getText().toString());
                break;
            case R.id.tll_right:
                CityPicker.getInstance()
                        .setFragmentManager(getSupportFragmentManager())    //此方法必须调用
                        .enableAnimation(true)    //启用动画效果
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)    //自定义动画
                        .setLocatedCity(new LocatedCity(cityName, provinceName, cityCode))  //APP自身已定位的城市，默认为null（定位失败）
                        .setHotCities(hotCities)    //指定热门城市
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                if (null != data) {
                                    tvCityName.setText(data.getName());
                                    tvRegion.setText("请选择行政区(必填)");
                                }
                            }

                            @Override
                            public void onLocate() {
                            }
                        })
                        .show();
                break;
            case R.id.btn_submit:
                addAddress();
                break;
        }
    }

    private void addAddress() {
        region = tvRegion.getText().toString();
        community = etCommunity.getText().toString();
        building = etBuilding.getText().toString();
        unit = etUnit.getText().toString();
        house = etHouse.getText().toString();
        if (!StringUtil.isEmpty(areaCode) && !StringUtil.isEmpty(community) && !StringUtil.isEmpty(building) && !StringUtil.isEmpty(unit) && !StringUtil.isEmpty(house)) {
            findHouseByAddr(areaCode, SpUtil.getUser().getUsername(), community, building, unit, house);
        } else {
            Toast.makeText(this, "所填信息不能有空", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.community_list, null);
        mAdapter = new RegionAdapter(this, lists);
        community_list = (ListView) view.findViewById(R.id.community_list);
        llBlank = (LinearLayout) view.findViewById(R.id.ll_blank);
        community_list.setAdapter(mAdapter);
        mPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(view);

        community_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvRegion.setText(lists.get(position).getName() + "");
                areaCode = lists.get(position).getId();
                mPopupWindow.dismiss();
            }
        });
        llBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

}
