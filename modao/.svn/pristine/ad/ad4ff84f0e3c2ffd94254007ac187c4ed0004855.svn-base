package nuanliu.com.modao.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.List;

import nuanliu.com.modao.bean.User;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.SpUtil;


public class App extends Application {

    private static Context context;
    private static Handler handler;
    private static User mUser;

    private static String mNative_recorder_path = null;


    //是否拒收下线通知
    private static boolean IsRejective = false;

    private static List mpermission;

    private static int mRecorderDuration = 0;

    public static int getmRecorderDuration() {
        return mRecorderDuration;
    }

    public static void setmRecorderDuration(int recorderDuration) {
        mRecorderDuration = recorderDuration;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mUser = SpUtil.getUser();
        //      if (BuildConfig.DEBUG)
        //      LeakCanary.install(this);
        //开启适配
        AutoLayoutConifg.getInstance().useDeviceSize();
        //极光推送开启
//        JPushInterface.setDebugMode(true);  //如果时正式版就改成false
//        JPushInterface.init(getApplicationContext());
//        JPushInterface.resumePush(this);

    }

    public static Class<?> aClass;


    public static boolean IsRejective() {
        return IsRejective;
    }

    public static void setIsRejective(boolean isRejective) {
        App.IsRejective = isRejective;
    }


    public static void setTargetClass(Class<?> cls) {
        aClass = cls;
    }

    public static void gotoTargetActivity(Context context) {
        IntentUtils.startActivity(context, aClass);
        aClass = null;
    }

    public static Context getContext() {
        return context;
    }

    public static String getmNative_recorder_path() {
        return mNative_recorder_path;
    }

    public static void setmNative_recorder_path(String mNative_recorder_path) {
        App.mNative_recorder_path = mNative_recorder_path;
    }

    public static Handler getHandler() {
        return handler;

    }

    public static User getUser() {
        return mUser;
    }

    public static void setUser(User user) {
        App.mUser = user;
        SpUtil.saveUser(user);
    }

}
