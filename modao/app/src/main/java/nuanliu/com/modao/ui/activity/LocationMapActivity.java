package nuanliu.com.modao.ui.activity;

import android.os.Bundle;

import com.amap.api.maps2d.MapView;

import butterknife.BindView;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;

/**
 * 维修师傅地图位置
 */

public class LocationMapActivity extends BaseActivity {

    @BindView(R.id.mapview)
    MapView mMapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapview.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_map;
    }

    private void initView() {
        getToolBar().setTitle("位置信息");
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapview.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapview.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapview.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapview.onDestroy();
    }
}
