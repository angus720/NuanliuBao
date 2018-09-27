package nuanliu.com.modao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.LinkedHashMap;

import nuanliu.com.modao.BuildConfig;
import nuanliu.com.modao.bean.CollectBean;
import nuanliu.com.modao.bean.User;
import nuanliu.com.modao.global.App;


/**
 * Created by Deity on 2016/12/12.
 */

public class SpUtil {

    public static final String FILENAME = "Modao";

    private static SharedPreferences preferences;

    public static SharedPreferences getSharedPreferences() {
        if (preferences == null) {
            preferences = App.getContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
            return preferences;
        }
        return preferences;
    }

    public static void saveString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    //获取一个String值
    public static String getString(String key, String defValue) {
        return preferences.getString(key, "");
    }

    public static void saveLoginState(Boolean islogin) {

        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putBoolean("logined", islogin);
        edit.commit();
    }

    public static boolean isLogin() {

        return getSharedPreferences().getBoolean("logined", false);
    }

    public static boolean saveIsUseGuide(int versionCode) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putInt("isUseGuide", versionCode);
        boolean commit = edit.commit();
        return commit;
    }

    public static boolean isUseGuide() {
        return BuildConfig.VERSION_CODE != getSharedPreferences().getInt("isUseGuide", -1);
    }

    //    保存项目id
    public static void saveCompanyId(String companyId) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putString("CompanyId", companyId);
        edit.commit();
    }

    //    获取项目id
    public static String getCompanyId() {
        return getSharedPreferences().getString("CompanyId", "");
    }

    //    保存项目名字
    public static void saveCompanyName(String companyName) {
        companyName = StringUtil.getCompanyNameForDZ(companyName);
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putString("companyName", companyName);
        edit.commit();

    }

    //    获取项目名
    public static String getCompanyName() {

        return getSharedPreferences().getString("companyName", "");
    }

    public static void saveProjectArea(String projectname) {
        projectname = StringUtil.getCompanyNameForDZ(projectname);
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putString("projectarea", projectname);
        edit.commit();

    }

    //    获取项目面积
    public static String getProjectArea() {

        return getSharedPreferences().getString("projectarea", "");
    }

    public static void saveMaintainGroupId(String projectname) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putString("maintaingroupid", projectname);
        edit.commit();

    }

    //    获取项目维修项目分组ID
    public static String getMaintainGroupId() {

        return getSharedPreferences().getString("maintaingroupid", "");
    }

    //保存用户
    public static boolean saveUser(User user) {
        String s = new Gson().toJson(user);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("user", s);
        return editor.commit();
    }

    public static boolean removeUser() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove("user");
        return editor.commit();
    }

    public static boolean containsUser() {
        return getSharedPreferences().contains("user");
    }

    public static User getUser() {
        String s = getSharedPreferences().getString("user", "");
        if (!TextUtils.isEmpty(s)) {
            return new Gson().fromJson(s, User.class);
        } else {
            return new User("", "", "", "", "", 0, "", "", "");
        }
    }

    public static boolean savaCollect(CollectBean collectBean) {
        String s = new Gson().toJson(collectBean);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("Collect", s);
        return editor.commit();
    }

    public static CollectBean getCollect() {
        String s = getSharedPreferences().getString("Collect", "");
        if (!TextUtils.isEmpty(s)) {
            return new Gson().fromJson(s, CollectBean.class);
        } else {
            return new CollectBean(new LinkedHashMap<String, Boolean>());
        }
    }

    public static boolean savaMeterCollect(CollectBean collectBean) {
        String s = new Gson().toJson(collectBean);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("Metercollect", s);
        return editor.commit();
    }

    public static CollectBean getMeterCollect() {
        String s = getSharedPreferences().getString("Collect", "");
        if (!TextUtils.isEmpty(s)) {
            return new Gson().fromJson(s, CollectBean.class);
        } else {
            return new CollectBean(new LinkedHashMap<String, Boolean>());
        }
    }

    public static void setBindDeviceState(Boolean state) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putBoolean("devicestate", state);
        edit.commit();
    }

    public static boolean getBindDeviceState() {

        return getSharedPreferences().getBoolean("devicestate", false);
    }


}
