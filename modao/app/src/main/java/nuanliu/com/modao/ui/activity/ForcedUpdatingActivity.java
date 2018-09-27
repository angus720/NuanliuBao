package nuanliu.com.modao.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
import nuanliu.com.modao.network.Apis;
import nuanliu.com.modao.network.upanddownload.DownloadProgressHandler;
import nuanliu.com.modao.network.upanddownload.ProgressHelper;
import nuanliu.com.modao.utils.ArithUtils;
import nuanliu.com.modao.utils.URLUtils;
import nuanliu.com.modao.utils.UpdateUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 强更新页面，当本地版本号和服务器端版本号不一致的时候 ，进行强制更新处理
 */

public class ForcedUpdatingActivity extends BaseActivity {

    @BindView(R.id.btn_rightnow_update)
    TextView mBtnRightnowUpdate;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_newversion)
    TextView mTvNewversion;
    @BindView(R.id.tv_updata_slogan)
    TextView mTvUpdataSlogan;
    private PackageInfo mInfo;

    //服务器下载完成的标志
    private String mNewversion;

    private boolean mDownFinish = false;
    private long mBytesRead;
    private long mContentLength;
    private boolean mIsfirst = true;
    private Apis mRetrofit;
    private String download_url;
    private long sum;
    private int total;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forced_updating;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntentData();
        initView();
    }

    private void initIntentData() {
        if (getIntent().hasExtra("new_version")) {
            mNewversion = getIntent().getStringExtra("new_version");
            mTvNewversion.setText("Modao" + mNewversion.toUpperCase());
        }
        if (getIntent().hasExtra("download_url")) {
            download_url = getIntent().getStringExtra("download_url");
        }
    }

    private void initView() {
        getToolBar().setDisplayHomeAsUpEnabled(false).setTitle("版本更新");

        PackageManager manager = getPackageManager();

        try {
            mInfo = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_rightnow_update)
    public void onClick() {
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            //可读可写
            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            //创建一个文件
            File downFile = new File(storageDirectory, "modao_apk_update.apk");

            if (downFile.exists()) {
                downFile.delete();
            }
        }
        showLoading("更新下载中");
        startDown(mNewversion);
    }

    @Override
    public void onBackPressed() {

    }

    private void startDown(String mNewestVersion) {
        //
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                //                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(URLUtils.APK_DOWN_BASW);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        //        OkHttpClient.Builder builder1 = new OkHttpClient().newBuilder();

        mRetrofit = retrofitBuilder
                .client(builder.connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES).build())
                .build().create(Apis.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                if (mIsfirst) {
                    mContentLength = contentLength;
                    mIsfirst = false;
                }
                showLoading("正在下载中" + (int) (ArithUtils.div(bytesRead, contentLength, 2) * 100) + "%");
                mBytesRead = bytesRead;

                if (done) {
                    mDownFinish = true;
                    dismissLoading();
                }
            }
        });

        getDownApk(mRetrofit, "");

    }

    private void getDownApk(final Apis apis, String range) {
        new Thread(new Runnable() {

            public File downFile;

            @Override
            public void run() {
                try {
                    sum = 0;
                    URL url = new URL(download_url);
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
                            UpdateUtil.installAPK(ForcedUpdatingActivity.this, downFile);
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
