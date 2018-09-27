package nuanliu.com.modao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.constant.Fields;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_CHOOSE_GALLERY = 10;

    @BindView(R.id.zxingview)
    ZXingView mZxingview;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ttv_start)
    TextView mTtvStart;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scan);
        initView();
    }

    private void initView() {
        requestCodeQRCodePermissions();
        mZxingview.setDelegate(this);
        getToolBar().setTitle("扫码");

    }


    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mZxingview.startCamera();
        //mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mZxingview.showScanRect();

        mZxingview.startSpotDelay(50);

    }

    @Override
    protected void onStop() {
        mZxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZxingview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(Fields.PAGE_DATA, result);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @OnClick({R.id.ttv_start, R.id.open_flashlight, R.id.close_flashlight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ttv_start:
                mZxingview.startSpotDelay(200);
                break;
            case R.id.open_flashlight:
                mZxingview.openFlashlight();
                break;
            case R.id.close_flashlight:
                mZxingview.closeFlashlight();
                break;
            //            case R.id.choose_qrcde_from_gallery:
            //                /*
            //                  从相册选取二维码图片，这里为了方便演示，使用的是
            //                https://github.com/bingoogolapple/BGAPhotoPicker-Android
            //                这个库来从图库中选择二维码图片，这个库不是必须的，你也可以通过自己的方式从图库中选择图片
            //                 */
            //                startActivityForResult(BGAPhotoPickerActivity.newIntent(this, null, 1, null, false), REQUEST_CODE_CHOOSE_GALLERY);
            //                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
