package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.DeviceItemBean;
import nuanliu.com.modao.bean.DeviceListBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.MainActivity;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.widget.TempView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 设备监测
 */

public class EquipmentMonitorActivity extends BaseActivity {

    @BindView(R.id.fl_empty)
    FrameLayout flEmpty;
    @BindView(R.id.fl_viewPager)
    View flViewPager;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    LinearLayout llIndicator;

    private View pageItem;
    private List<DeviceItemBean> lists = new ArrayList();
    private TempView tempView;

    private int flag = 2;
    private static final int REQUEST_ADD_EQUIPMENT = 102;
    private static final int REQUEST_EQUIPMENT_ADDRESS = 103;
    private String equipmentAddress;
    private TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_equipment_monitor;
    }

    private void initView() {
        getToolBar().setTitle("设备监测");
    }

    private void initEvent() {
        getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(EquipmentMonitorActivity.this, MainActivity.class);
            }
        });
    }

    private void initData() {
//        lists.add(new DeviceItemBean("25.2568", "梦园小区", "50", "3栋"));
//        lists.add(new DeviceItemBean("26.2687", "海棠湾", "51", "4栋"));
//        lists.add(new DeviceItemBean("27.0000", "优活城", "52", "5栋"));
//        lists.add(new DeviceItemBean("28.1234", "拓基广场", "53", "6栋"));

        getDeviceList();
    }

    private void getDeviceList() {
        showLoading("加载中");
        ServiceFactory
                .getApis()
                .getUserDeviceList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<DeviceListBean>bindToLifecycle())
                .subscribe(new Subscriber<DeviceListBean>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DeviceListBean deviceListBean) {
                        if (deviceListBean.getStatus() == 1) {
                            refreshViewByData(deviceListBean.getBody().getDatas());
                        }
                    }
                });
    }

    private void refreshViewByData(List<DeviceListBean.BodyBean.DatasBean> list) {
        for (int i = 0; i < list.size(); i++) {
            DeviceListBean.BodyBean.DatasBean datasBean = list.get(i);
            lists.add(new DeviceItemBean(datasBean.getEui64(), datasBean.getTemp(), datasBean.getAddress(), datasBean.getHumidity(), datasBean.getCommunity()));
        }
        if (null == lists || lists.size() == 0) {
            flViewPager.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < lists.size(); i++) {
                //set indicator
                View indicator = LayoutInflater.from(this).inflate(R.layout.equipment_pageindicator,
                        null);
                ImageView child1 = (ImageView) indicator.findViewById(R.id.pageindicator);
                if (i == 0) {
                    child1.setBackgroundResource(R.drawable.ic_page_indicator_focused);
                } else {
                    child1.setBackgroundResource(R.drawable.ic_page_indicator);
                }
                llIndicator.addView(indicator);
            }
            viewPager.setAdapter(new ViewPagerAdapter());
            viewPager.setOffscreenPageLimit(lists.size());
            viewPager.addOnPageChangeListener(new EquipmentChangeListener());
        }
    }

    @OnClick({R.id.tll_add_new, R.id.add_equipment, R.id.iv_go_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tll_add_new:
                Intent intent1 = new Intent(this, OnlinePaymentActivity.class);
                intent1.putExtra(Fields.FLAG, flag);
                startActivity(intent1);
                break;
            case R.id.add_equipment:
                Intent intent = new Intent(this, OnlinePaymentActivity.class);
                intent.putExtra(Fields.FLAG, flag);
                startActivity(intent);
                break;
            case R.id.iv_go_list:
                IntentUtils.startActivity(this, EquipmentListActivity.class);
                break;
        }
    }

    class EquipmentChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageSelected(int position) {
            TempView tempView = (TempView) viewPager.getChildAt(position).getTag();
//            tempView.startTranslate(lists.get(position).getTemp());
            tempView.startTranslate(String.format("%.2f", Double.parseDouble(lists.get(position).getTemp())));
            System.out.println("onPageSelected :" + position);

            for (int i = 0; i < llIndicator.getChildCount(); i++) {
                View child = llIndicator.getChildAt(i);
                ImageView circle = (ImageView) child.findViewById(R.id.pageindicator);
                if (i == position) {
                    circle.setBackgroundResource(R.drawable.ic_page_indicator_focused);
                } else {
                    circle.setBackgroundResource(R.drawable.ic_page_indicator);
                }
            }
        }
    }

    //ViewPager页面适配器
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            System.out.println("instantiateItem :" + position);
            pageItem = LayoutInflater.from(getBaseContext()).inflate(
                    R.layout.equipment_item, null);
            tvAddress = (TextView) pageItem.findViewById(R.id.tv_address);
            final TextView temperature = (TextView) pageItem.findViewById(R.id.tv_temperature);
            final TextView humidity = (TextView) pageItem.findViewById(R.id.tv_humidity);
            final TextView eui64 = (TextView) pageItem.findViewById(R.id.tv_eui64);
            tempView = (TempView) pageItem.findViewById(R.id.temp);
            DeviceItemBean bean = lists.get(position);
            String tempera = String.format("%.2f", Double.parseDouble(bean.getTemp()));
            String humi = String.format("%.2f", Double.parseDouble(bean.getHumidity()));
            eui64.setText("设备序列号：" + bean.getEui64());
            temperature.setText(tempera);
            humidity.setText(humi);
            if (pageItem.getTag() == null && position == 0) {
                tempView.startTranslate(tempera);
            }
            pageItem.setTag(tempView);
            tempView.createTextImage("已连接");
            tvAddress.setText(bean.getAddress());
            viewGroup.addView(pageItem);
            return pageItem;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.startActivity(this, MainActivity.class);
    }
}
