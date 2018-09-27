package nuanliu.com.modao.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.RxEventHeadBean;
import nuanliu.com.modao.global.App;
import nuanliu.com.modao.rxbus.RxBus;
import nuanliu.com.modao.rxbus.Subscribe;
import nuanliu.com.modao.ui.MainActivity;
import nuanliu.com.modao.ui.dialog.CleanCacheDialog;
import nuanliu.com.modao.utils.HeadConstant;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.LogUtils;
import nuanliu.com.modao.utils.MD5Utils;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.utils.URLUtils;
import nuanliu.com.modao.widget.PersonalPopupWindow;
import nuanliu.com.modao.widget.photopicker.utils.FileUtils;
import okhttp3.Call;

import static nuanliu.com.modao.rxbus.RxBusCode.RX_BUS_CODE_HEAD_IMAGE_URI;

/**
 * 个人信息
 */

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.iv_user_pic)
    ImageView civHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    //请求相机
    private static final int REQUEST_CAMERA                      = 100;
    //请求相册
    private static final int REQUEST_PHOTO                       = 101;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE  = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    private CleanCacheDialog    mCleanCacheDialog;
    private PersonalPopupWindow popupWindow;
    private File                tempFile;
    private File                fileLOGO;
    private Context             mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        RxBus.get().register(this);
        initPopupView();
        initView();
        initData();
        initEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    private void initView() {
        getToolBar().setTitle("个人信息");
    }

    private void initData() {
        tvUserName.setText(SpUtil.getUser().getName());
    }

    private void initEvent() {
        getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(UserInfoActivity.this, MainActivity.class);
            }
        });
    }

    @OnClick({R.id.trl_user_pic, R.id.trl_user_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_user_pic:
                showPopupView();
                break;
            case R.id.trl_user_name:
                IntentUtils.startActivity(this, ModifyUserNameActivity.class);
                break;
        }
    }


    //显示
    public void showPopupView() {
        View parent = LayoutInflater.from(mContext).inflate(R.layout.fragment_personal, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }

    //隐藏
    public void dismissPopupView() {
        popupWindow.dismiss();
    }

    //判断
    public boolean popupIsShowing() {
        return popupWindow.isShowing();
    }


    //初始化popwindow
    public void initPopupView() {
        popupWindow = new PersonalPopupWindow(mContext);
        popupWindow.setOnItemClickListener(new PersonalPopupWindow.OnItemClickListener() {
            @Override
            public void onCaremaClicked() {
                btnCameraClicked();
            }

            @Override
            public void onPhotoClicked() {
                btnPhotoClicked();
            }

            @Override
            public void onCancelClicked() {
                dismissPopupView();
            }
        });

    }


    //点击拍照
    public void btnCameraClicked() {
        //创建拍照存储的图片文件
        tempFile = new File(FileUtils.checkDirPath(Environment.getExternalStorageDirectory()
                .getPath() + "/image/"), MD5Utils.getMD5(HeadConstant.HEAD_IMAGE_NAME) + System
                .currentTimeMillis() + ".jpg");
        //权限判断
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            //跳转到调用系统相机
            gotoSystemCamera(tempFile, REQUEST_CAMERA);
        }
        if (popupIsShowing())
            dismissPopupView();
    }


    //点击相册
    public void btnPhotoClicked() {
        //权限判断
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                            .permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            //跳转到相册
            gotoSystemPhoto(REQUEST_PHOTO);
        }
        if (popupIsShowing())
            dismissPopupView();
    }



    //跳转设置界面
    public void gotoHeadSettingActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(mContext, HeadSettingActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }

    //跳转到调用系统图库
    public void gotoSystemPhoto(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), requestCode);
    }

    //跳转到调用系统相机
    public void gotoSystemCamera(File tempFile, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml，下面2种方式都可以
            //            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //            Uri contentUri = FileProvider.getUriForFile(mActivity, BuildConfig
            // .APPLICATION_ID + "" +
            //                    ".fileProvider", tempFile);
            //            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile
                    .getAbsolutePath());
            Uri uri = mContext.getContentResolver().insert(MediaStore.Images
                    .Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

                gotoSystemCamera(tempFile, REQUEST_CAMERA);
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

                gotoSystemPhoto(REQUEST_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAMERA: //调用系统相机返回
                if (resultCode == Activity.RESULT_OK) {
                    gotoHeadSettingActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PHOTO:  //调用系统相册返回
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoHeadSettingActivity(uri);
                }
                break;
        }
    }
    /**
     * RxBus接收图片Uri
     *
     * @param bean RxEventHeadBean
     */
    @Subscribe(code = RX_BUS_CODE_HEAD_IMAGE_URI)
    public void rxBusEvent(RxEventHeadBean bean) {
        Uri uri = bean.getUri();
        if (uri == null) {
            return;
        }
        String cropImagePath = FileUtils.getRealFilePathFromUri(this, uri);
        try {
            fileLOGO = new File(new URI(uri.toString()));
            getfileIformation();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        if (bitMap != null)
            civHead.setImageBitmap(bitMap);

    }
    /*
   * 上传文件接口
   * */
    public void getfileIformation() {

        //请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("telephone", App.getUser().getTelphone());

        OkHttpUtils
                .post()
                .url(URLUtils.getWebRoot() + "/user/updateCUHeadProtrait.do")
                .params(params)
                .addFile("headPortrait", fileLOGO.getName(), fileLOGO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        // SDToast.showToast("失败" + e.getMessage());
                        ToastUtils.showShortToast(mContext,"上传失败");
                        LogUtils.e("失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("=========>"+response);
                        ToastUtils.showShortToast(mContext,"上传成功");
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}
