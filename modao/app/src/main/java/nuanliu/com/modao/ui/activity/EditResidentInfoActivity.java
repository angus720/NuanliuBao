package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import nuanliu.com.modao.bean.ResidentInfoBean;
import nuanliu.com.modao.bean.ResidentItemBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.dialog.AppDialog;
import nuanliu.com.modao.ui.dialog.CardInfoDialog;
import nuanliu.com.modao.utils.SpUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 编辑缴费账户
 */

public class EditResidentInfoActivity extends BaseActivity {

    @BindView(R.id.et_owner_name)
    EditText etOwnerName;
    @BindView(R.id.et_owner_number)
    EditText etOwnerNumber;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_right)
    TextView tvCityName;

    private AppDialog appDialog;
    private CardInfoDialog cardInfoDialog;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private ResidentItemBean itemBean;
    private String companyName;
    private String companyId;
    private String residentid;
    private String residentid_old;//旧的id
    private String residentName;
    private String residentCardNumber;
    private String cityName;
    private String provinceName;
    private String cityCode;
    private String mOwnerName;
    private String mOwnerNumber;
    private String community;
    private String building;
    private String unit;
    private String house;

    private static final int REQUEST_HEAT_COMPANY = 100;

    private List<HotCity> hotCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setHotCity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_resident_info;
    }

    private void initView() {
        getToolBar().setTitle("编辑缴费账户");
        setLocate();
    }

    private void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            if (intent.hasExtra(Fields.RESIDENT_ITEM)) {
                itemBean = (ResidentItemBean) intent.getSerializableExtra(Fields.RESIDENT_ITEM);
                companyName = itemBean.getCompanyName();
                companyId = itemBean.getCompanyid();
                residentCardNumber = itemBean.getAccount_h();
                residentName = itemBean.getHouseholder();
                residentid_old = itemBean.getResidentid();
            }
        }

        etOwnerName.setText(residentName);
        tvCompanyName.setText(companyName);
        etOwnerNumber.setText(residentCardNumber);
    }

    private void setLocate() {
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

    private void getData() {
        residentName = etOwnerName.getText().toString().trim() + "";
        residentCardNumber = etOwnerNumber.getText().toString().trim() + "";
    }

    @OnClick({R.id.trl_choose_company, R.id.tv_find_number, R.id.btn_confirm, R.id.tll_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_choose_company:
                Intent intent = new Intent(this, ChooseCompanyActivity.class);
                intent.putExtra(Fields.CITY, tvCityName.getText().toString());
                startActivityForResult(intent, REQUEST_HEAT_COMPANY);
                break;
            case R.id.tv_find_number:
                appDialog = new AppDialog(EditResidentInfoActivity.this).builder()
                        .setTitle(getResources().getString(R.string.how_know_card_number))
                        .setMsg(getResources().getString(R.string.card_number_detail))
                        .setPositiveButton("知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                appDialog.show();
                break;
            case R.id.btn_confirm:
                getData();
                findHouse(SpUtil.getUser().getUsername(), SpUtil.getUser().getTelphone(), companyId, residentName, residentCardNumber);
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
                                }
                            }

                            @Override
                            public void onLocate() {
                                //启动定位
                                //开始定位，这里模拟一下定位
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //定位完成之后更新数据
//                                        CityPicker.getInstance()
//                                                .locateComplete(new LocatedCity(cityName, provinceName, cityCode), LocateState.SUCCESS);
//                                    }
//                                }, 2000);
                            }
                        })
                        .show();
                break;
        }
    }

    private void findHouse(String userName, String telPhone, String companyId, String houseHolder, String account_h) {
        ServiceFactory
                .getApis()
                .findHouse(userName, telPhone, companyId, houseHolder, account_h)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse<ResidentInfoBean>>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse<ResidentInfoBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<ResidentInfoBean> baseResponse) {
                        if (baseResponse.getStatus() == 1) {
                            ResidentInfoBean bean = baseResponse.getBody();
                            mOwnerName = bean.getHouseholder();
                            mOwnerNumber = bean.getAccount_h();
                            community = bean.getCommunityName();
                            building = bean.getBuildingNumber();
                            unit = bean.getUnitNumber();
                            house = bean.getHouseNumber();
                            residentid = bean.getResidentid();
                            showDialog(mOwnerName, mOwnerNumber, community, building, unit, house, residentid);
                        } else {
                            if (baseResponse.getStatus_name().contains("房屋不存在")) {
                                Toast.makeText(EditResidentInfoActivity.this, "没有查到符合条件的地址", Toast.LENGTH_SHORT).show();
                            } else if (baseResponse.getStatus_name().contains("用户不存在")) {
                                Toast.makeText(EditResidentInfoActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                            } else if (baseResponse.getStatus_name().contains("房屋已绑定")) {
                                Toast.makeText(EditResidentInfoActivity.this, "房屋已绑定", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void upbdResident(String userName, String residentId, String companyId, String holderName, String account_h) {
        ServiceFactory
                .getApis()
                .upbdResident(userName, residentId, companyId, holderName, account_h)
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
                            Toast.makeText(EditResidentInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_HEAT_COMPANY:
                if (resultCode == RESULT_OK) {
                    if (data.hasExtra(Fields.CHOOSE_COMPANY)) {
                        companyName = data.getStringExtra(Fields.CHOOSE_COMPANY);
                        tvCompanyName.setText(companyName);
                    }
                    if (data.hasExtra(Fields.COMPANY_ID)) {
                        companyId = data.getStringExtra(Fields.COMPANY_ID);
                    }
                }
                break;
        }
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

    private void showDialog(String userName, String cardNumber, String community, String building, String unit, String house, final String residentId) {
        cardInfoDialog = new CardInfoDialog(this).builder();
        cardInfoDialog.setUserName(userName);
        cardInfoDialog.setNewNumber(cardNumber);
        cardInfoDialog.setCommunityName(community);
        cardInfoDialog.setBuildingNumber(building);
        cardInfoDialog.setUnitNumber(unit);
        cardInfoDialog.setHouseNumber(house);
        cardInfoDialog.setResidentId(residentId);
        cardInfoDialog.setNegativeButton("返回修改", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardInfoDialog.setPositiveButton("确认修改", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upbdResident(SpUtil.getUser().getUsername(), residentid_old, companyId, residentName, residentCardNumber);
            }
        });
        cardInfoDialog.show();
    }

}
