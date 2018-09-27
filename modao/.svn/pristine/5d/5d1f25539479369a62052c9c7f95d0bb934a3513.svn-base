package nuanliu.com.modao.ui.activity;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.AppUpdate;
import nuanliu.com.modao.network.Apis;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.network.upanddownload.DownloadProgressHandler;
import nuanliu.com.modao.network.upanddownload.ProgressHelper;
import nuanliu.com.modao.utils.ArithUtils;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.URLUtils;
import nuanliu.com.modao.utils.UpdateUtil;
import nuanliu.com.modao.widget.TouchRelativeLayout;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 关于我们
 */

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.versioncode)
    TextView mVersioncode;
    @BindView(R.id.rednew)
    TextView mRednew;
    @BindView(R.id.trl_update)
    TouchRelativeLayout mTrlUpdate;

    private PackageInfo mInfo;
    private String mDownloadUrl;
    private long sum;
    private int total;

    //服务器下载完成的标志
    private static final int DOWNFINISH = 101;
    //intent标记
    private static final int INSTALLREQUEST = 102;

    private Handler mHandler;
    private String mNewestVersion;
    //是否可以去更新
    private boolean goUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    private void initView() {
        initHandler();
        getToolBar().setTitle("关于我们");
        PackageManager manager = getPackageManager();
        try {
            mInfo = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mVersioncode.setText("Version" + mInfo.versionName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        ServiceFactory.getApis().getAppUpdate(" ", mInfo.versionName + "")
                .subscribeOn(Schedulers.io())
                .compose(this.<BaseResponse<AppUpdate>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<AppUpdate>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<AppUpdate> appUpdate) {
                        if (appUpdate.getStatus() == 1) {
                            refreshView(appUpdate.getBody());
                        }
                    }

                });
    }

    private void initHandler() {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWNFINISH:
                        dismissLoading();

                        Toast.makeText(AboutUsActivity.this, "下载完成", Toast.LENGTH_SHORT).show();

                        File downFile = (File) msg.obj;

                        UpdateUtil.installAPK(AboutUsActivity.this, downFile);
                        break;
                }
            }
        };
    }

    private void refreshView(AppUpdate appUpdate) {
        mDownloadUrl = appUpdate.getDownload_url();
        mNewestVersion = appUpdate.getNewest_version();
        if (TextUtils.equals("v" + mInfo.versionName, appUpdate.getNewest_version())) {
            mRednew.setVisibility(View.GONE);
            goUpdate = false;
        } else {
            mRednew.setVisibility(View.VISIBLE);
            goUpdate = true;
        }
    }

    @OnClick({R.id.trl_update, R.id.tv_user_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_update:
                if (goUpdate) {
                    showUpdateDialog();
                } else {
                    mTrlUpdate.setClickable(false);
                    Toast.makeText(this, "当前版本已是最新", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_user_agreement:
                IntentUtils.startActivity(this, UserAgreementActivity.class);
                break;
        }
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本更新");
        builder.setCancelable(true);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showLoading("更新下载中");
                if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
                    //可读可写
                    File storageDirectory = Environment.getExternalStorageDirectory();
                    //创建一个文件
                    File downFile = new File(storageDirectory, "modao_apk_update.apk");
                    if (downFile.exists()) {
                        downFile.delete();
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(mNewestVersion);
                    }
                }).start();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void download(String mNewestVersion) {
        //
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(URLUtils.APK_DOWN_BASW);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
//        OkHttpClient.Builder builder1 = new OkHttpClient.Builder();

        Apis retrofit = retrofitBuilder
                .client(builder.connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS).build())
                .build().create(Apis.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                showLoading("拼命加载中" + (int) (ArithUtils.div(bytesRead, contentLength, 2) * 100) + "%");

                if (done) {
                    dismissLoading();
                }
            }
        });
        new Thread(new Runnable() {

            public File downFile;

            @Override
            public void run() {
                try {
                    sum = 0;
                    URL url = new URL(mDownloadUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    total = connection.getContentLength();
                    FileOutputStream fileOutputStream = null;
                    InputStream inputStream;
                    if (connection.getResponseCode() == 200) {
                        inputStream = connection.getInputStream();

                        if (inputStream != null) {
                            //可读可写
                            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                            //创建一个文件
                            downFile = new File(storageDirectory, "modao_apk_update.apk");
                            fileOutputStream = new FileOutputStream(downFile);
                            byte[] buffer = new byte[1024];
                            int length = 0;

                            while ((length = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, length);
                                sum += length;
                                final int progress = (int) (sum * 1.0f / total * 100);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showLoading("拼命加载中" + progress + "%");
                                    }
                                });
                            }
                            fileOutputStream.close();
                            fileOutputStream.flush();
                        }
                        inputStream.close();
                    }

                    //Log.e("Mr.Kang", "run: "+"下载完成");
                    // 往handler发送一条消息 更改button的text属性
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UpdateUtil.installAPK(AboutUsActivity.this, downFile);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
