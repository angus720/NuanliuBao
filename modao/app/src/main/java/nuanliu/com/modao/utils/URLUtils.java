package nuanliu.com.modao.utils;

/**
 * @创建者 Angus
 * @描述
 * @更新描述
 */

public class URLUtils {

    public static final String APK_DOWN_BASW = "http://222.128.2.68:9095/charge/apk/";

    public static final String REPAIR_BASEURL = "http://222.128.2.68:9095/maintain/app/";
//    public static final String REPAIR_BASEURL = "http://192.168.0.126/maintain/app/";

//    public static final String BASE_BASEURL = "http://192.168.0.210/charge/app/";
    public static final String BASE_BASEURL = "http://222.128.2.68:9095/charge/app/";
//    public static final String BASE_BASEURL = "http://192.168.0.126/charge/app/";

    public static String getWebRoot() {

        String HTTP = BASE_BASEURL;

        return HTTP;
    }

}
