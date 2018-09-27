package nuanliu.com.modao.ui.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.RxEventHeadBean;
import nuanliu.com.modao.rxbus.RxBus;
import nuanliu.com.modao.rxbus.RxHelper;
import nuanliu.com.modao.utils.HeadConstant;
import nuanliu.com.modao.widget.headclip.ClipViewLayout;

import static nuanliu.com.modao.rxbus.RxBusCode.RX_BUS_CODE_HEAD_IMAGE_URI;


/**
 * Created by Horrarndoo on 2017/9/25.
 * <p>
 * 设置头像Activity
 */

public class HeadSettingActivity extends BaseActivity {

    @BindView(R.id.cvl_rect)
    ClipViewLayout cvlRect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head_setting;
    }

    protected void initData() {
        //        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }
    private void initView() {
        initData();
        getToolBar().setTitle("选取头像");
        cvlRect.setImageSrc(getIntent().getData());
    }



    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_ok:
                Observable.create(new ObservableOnSubscribe<Uri>() {
                    @Override
                    public void subscribe(ObservableEmitter<Uri> e) throws
                            Exception {
                        e.onNext(generateUri());
                        e.onComplete();
                    }
                }).compose(RxHelper.<Uri>rxSchedulerHelper())
                        .subscribe(new Consumer<Uri>() {
                            @Override
                            public void accept(Uri uri) throws Exception {
                                RxEventHeadBean rxEventHeadBean = new RxEventHeadBean(uri);
                                RxBus.get().send(RX_BUS_CODE_HEAD_IMAGE_URI, rxEventHeadBean);
                                finish();
                            }
                        });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Logger.e("RxBus.get().unRegister(this)");
        RxBus.get().unRegister(this);
    }

    /**
     * 生成Uri
     */
    private Uri generateUri() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = cvlRect.clip();
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), HeadConstant.HEAD_IMAGE_NAME + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mSaveUri;
    }
}
